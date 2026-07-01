package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProdottoDAO {
    public record RemovalResult(String outcome, List<String> mediaUrls) {}

    public List<Prodotto> doRetrieveAll() {
        return retrieveProducts("SELECT * FROM Prodotto WHERE Disponibile = TRUE ORDER BY Nome", List.of());
    }

    public List<Prodotto> doRetrieveAllForAdmin() {
        return retrieveProducts("SELECT * FROM Prodotto ORDER BY Disponibile DESC, Nome", List.of());
    }

    public Prodotto doRetrieveById(int id) {
        List<Prodotto> products = retrieveProducts("SELECT * FROM Prodotto WHERE ID_Prodotto = ?", List.of(id));
        return products.isEmpty() ? null : products.getFirst();
    }

    public Prodotto doRetrieveAvailableById(int id) {
        List<Prodotto> products = retrieveProducts(
                "SELECT * FROM Prodotto WHERE ID_Prodotto = ? AND Disponibile = TRUE", List.of(id));
        return products.isEmpty() ? null : products.getFirst();
    }

    public List<Prodotto> doRetrieveSuggestProduct(String[] suggested) {
        if (suggested == null || suggested.length == 0) return List.of();
        String placeholders = String.join(",", Collections.nCopies(suggested.length, "?"));
        List<Object> parameters = new ArrayList<>(List.of(suggested));
        return retrieveProducts("SELECT * FROM Prodotto WHERE Disponibile = TRUE AND Nome IN (" +
                placeholders + ") ORDER BY Nome", parameters);
    }

    public List<Prodotto> search(String text, Float min, Float max, String developer,
                                 List<String> genres, List<String> platforms, List<String> modes,
                                 boolean includeUnavailable) {
        StringBuilder sql = new StringBuilder("SELECT DISTINCT p.* FROM Prodotto p WHERE 1=1");
        List<Object> parameters = new ArrayList<>();
        if (!includeUnavailable) sql.append(" AND p.Disponibile = TRUE");
        if (text != null && !text.isBlank()) {
            sql.append(" AND p.Nome LIKE ?");
            parameters.add("%" + text.trim() + "%");
        }
        if (min != null) { sql.append(" AND p.Prezzo_Scontato >= ?"); parameters.add(min); }
        if (max != null) { sql.append(" AND p.Prezzo_Scontato <= ?"); parameters.add(max); }
        if (developer != null && !developer.isBlank()) {
            sql.append(" AND p.Casa_Sviluppatrice LIKE ?");
            parameters.add("%" + developer.trim() + "%");
        }
        appendExists(sql, parameters, "Genere", "Genere", genres);
        appendExists(sql, parameters, "Piattaforma", "Piattaforma", platforms);
        appendModes(sql, parameters, modes);
        sql.append(" ORDER BY p.Disponibile DESC, p.Nome");
        return retrieveProducts(sql.toString(), parameters);
    }

    private void appendExists(StringBuilder sql, List<Object> parameters, String table,
                              String column, List<String> values) {
        if (values == null || values.isEmpty()) return;
        sql.append(" AND EXISTS (SELECT 1 FROM ").append(table)
                .append(" x WHERE x.ID_Prodotto = p.ID_Prodotto AND x.").append(column).append(" IN (")
                .append(String.join(",", Collections.nCopies(values.size(), "?"))).append("))");
        parameters.addAll(values);
    }

    private void appendModes(StringBuilder sql, List<Object> parameters, List<String> modes) {
        if (modes == null || modes.isEmpty()) return;
        sql.append(" AND (");
        for (int i = 0; i < modes.size(); i++) {
            if (i > 0) sql.append(" OR ");
            sql.append("p.Modalita_Gioco LIKE ?");
            parameters.add("%" + modes.get(i) + "%");
        }
        sql.append(")");
    }

    public List<Media> doRetrieveMediaByProdotto(int productId) {
        String sql = "SELECT * FROM MediaProdotto WHERE ID_Prodotto = ? ORDER BY Ordine_Visualizzazione, ID_Media";
        List<Media> media = new ArrayList<>();
        try (Connection connection = ConPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Media item = new Media();
                    item.setIdProdotto(productId);
                    item.setTipo(result.getString("Tipo"));
                    item.setUrlMedia(result.getString("URL_Media"));
                    item.setOrdineVisualizzazione(result.getInt("Ordine_Visualizzazione"));
                    media.add(item);
                }
            }
            return media;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int create(Prodotto product, List<Media> media) {
        String sql = "INSERT INTO Prodotto (Nome, Descrizione_Prod, Prezzo_OG, Prezzo_Scontato, " +
                "Modalita_Gioco, Casa_Sviluppatrice, Sconto, Email_Amm, Disponibile) VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE)";
        try (Connection connection = ConPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                bindProduct(statement, product, false);
                statement.executeUpdate();
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (!keys.next()) throw new SQLException("ID prodotto non generato");
                    product.setID_Prodotto(keys.getInt(1));
                }
                replaceClassifications(connection, product);
                insertMedia(connection, product.getID_Prodotto(), media);
                connection.commit();
                return product.getID_Prodotto();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Prodotto product) {
        String sql = "UPDATE Prodotto SET Nome=?, Descrizione_Prod=?, Prezzo_OG=?, Prezzo_Scontato=?, " +
                "Modalita_Gioco=?, Casa_Sviluppatrice=?, Sconto=? WHERE ID_Prodotto=?";
        try (Connection connection = ConPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                bindProduct(statement, product, true);
                boolean updated = statement.executeUpdate() == 1;
                if (!updated) throw new SQLException("Prodotto inesistente");
                replaceClassifications(connection, product);
                connection.commit();
                return true;
            } catch (Exception e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RemovalResult removeOrDisable(int productId) {
        try (Connection connection = ConPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                try (PreparedStatement lock = connection.prepareStatement(
                        "SELECT ID_Prodotto FROM Prodotto WHERE ID_Prodotto=? FOR UPDATE")) {
                    lock.setInt(1, productId);
                    try (ResultSet result = lock.executeQuery()) {
                        if (!result.next()) {
                            connection.rollback();
                            return new RemovalResult("not_found", List.of());
                        }
                    }
                }
                boolean referenced = exists(connection,
                        "SELECT EXISTS(SELECT 1 FROM Contiene WHERE ID_Prodotto=?) OR " +
                                "EXISTS(SELECT 1 FROM Recensione WHERE ID_Prodotto=?)", productId);
                if (referenced) {
                    try (PreparedStatement update = connection.prepareStatement(
                            "UPDATE Prodotto SET Disponibile=FALSE WHERE ID_Prodotto=?")) {
                        update.setInt(1, productId);
                        update.executeUpdate();
                    }
                    connection.commit();
                    return new RemovalResult("unavailable", List.of());
                }
                List<String> urls = mediaUrls(connection, productId);
                try (PreparedStatement delete = connection.prepareStatement(
                        "DELETE FROM Prodotto WHERE ID_Prodotto=?")) {
                    delete.setInt(1, productId);
                    delete.executeUpdate();
                }
                connection.commit();
                return new RemovalResult("deleted", urls);
            } catch (Exception e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> retrieveGenres() {
        return List.of("Action RPG", "Azione", "Adventure", "Horror", "RPG", "RPG-Sci-fi", "Sandbox", "Sport", "Strategia");
    }
    public List<String> retrievePlatforms() {
        return List.of("Windows", "macOS", "Linux", "PlayStation", "Xbox", "Nintendo Switch");
    }
    public List<String> retrieveModes() { return List.of("Single Player", "Multiplayer", "Cooperativa"); }

    private List<Prodotto> retrieveProducts(String sql, List<Object> parameters) {
        List<Prodotto> products = new ArrayList<>();
        try (Connection connection = ConPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bindParameters(statement, parameters);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) products.add(mapProduct(result));
            }
            for (Prodotto product : products) loadClassifications(connection, product);
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Prodotto mapProduct(ResultSet result) throws SQLException {
        Prodotto product = new Prodotto();
        product.setID_Prodotto(result.getInt("ID_Prodotto"));
        product.setNome(result.getString("Nome"));
        product.setDescrizione(result.getString("Descrizione_Prod"));
        product.setPrezzo_OG(result.getFloat("Prezzo_OG"));
        product.setPrezzo_scontato(result.getFloat("Prezzo_Scontato"));
        product.setModalita_Gioco(result.getString("Modalita_Gioco"));
        product.setCasa_sviluppatrice(result.getString("Casa_Sviluppatrice"));
        product.setSconto(result.getInt("Sconto"));
        product.seteMailAmm(result.getString("Email_Amm"));
        product.setDisponibile(result.getBoolean("Disponibile"));
        return product;
    }

    private void loadClassifications(Connection connection, Prodotto product) throws SQLException {
        product.setGeneri(retrieveRelation(connection, "Genere", "Genere", product.getID_Prodotto()));
        product.setPiattaforme(retrieveRelation(connection, "Piattaforma", "Piattaforma", product.getID_Prodotto()));
        product.setModalita(parseModes(product.getModalita_Gioco()));
    }

    private List<String> parseModes(String value) {
        if (value == null || value.isBlank()) return new ArrayList<>();
        List<String> modes = new ArrayList<>();
        if (value.contains("Single")) modes.add("Single Player");
        if (value.contains("Multi")) modes.add("Multiplayer");
        if (value.contains("Coop")) modes.add("Cooperativa");
        return modes;
    }

    private List<String> retrieveRelation(Connection connection, String table, String column, int id) throws SQLException {
        List<String> values = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT " + column + " FROM " + table + " WHERE ID_Prodotto=? ORDER BY " + column)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) values.add(result.getString(1));
            }
        }
        return values;
    }

    private void replaceClassifications(Connection connection, Prodotto product) throws SQLException {
        deleteRelation(connection, "Genere", product.getID_Prodotto());
        deleteRelation(connection, "Piattaforma", product.getID_Prodotto());
        insertRelation(connection, "Genere", "Genere", product.getID_Prodotto(), product.getGeneri());
        insertRelation(connection, "Piattaforma", "Piattaforma", product.getID_Prodotto(), product.getPiattaforme());
    }

    private void deleteRelation(Connection connection, String table, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM " + table + " WHERE ID_Prodotto=?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private void insertRelation(Connection connection, String table, String column, int id, List<String> values) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " + table + " (ID_Prodotto, " + column + ") VALUES (?, ?)")) {
            for (String value : values) {
                statement.setInt(1, id);
                statement.setString(2, value);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void insertMedia(Connection connection, int productId, List<Media> media) throws SQLException {
        String sql = "INSERT INTO MediaProdotto (ID_Media, ID_Prodotto, Tipo, URL_Media, Ordine_Visualizzazione) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Media item : media) {
                statement.setString(1, java.util.UUID.randomUUID().toString());
                statement.setInt(2, productId);
                statement.setString(3, item.getTipo());
                statement.setString(4, item.getUrlMedia());
                statement.setInt(5, item.getOrdineVisualizzazione());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void bindProduct(PreparedStatement statement, Prodotto product, boolean includeId) throws SQLException {
        statement.setString(1, product.getNome());
        statement.setString(2, product.getDescrizione());
        statement.setFloat(3, product.getPrezzo_OG());
        statement.setFloat(4, product.getPrezzo_scontato());
        statement.setString(5, String.join("/", product.getModalita()));
        statement.setString(6, product.getCasa_sviluppatrice());
        statement.setInt(7, product.getSconto());
        if (includeId) statement.setInt(8, product.getID_Prodotto());
        else statement.setString(8, product.geteMailAmm());
    }

    private boolean exists(Connection connection, String sql, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, id);
            try (ResultSet result = statement.executeQuery()) { return result.next() && result.getBoolean(1); }
        }
    }

    private List<String> mediaUrls(Connection connection, int id) throws SQLException {
        List<String> urls = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT URL_Media FROM MediaProdotto WHERE ID_Prodotto=?")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) urls.add(result.getString(1));
            }
        }
        return urls;
    }

    private List<String> retrieveNames(String sql) {
        List<String> names = new ArrayList<>();
        try (Connection connection = ConPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) names.add(result.getString(1));
            return names;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private void bindParameters(PreparedStatement statement, List<Object> parameters) throws SQLException {
        for (int i = 0; i < parameters.size(); i++) statement.setObject(i + 1, parameters.get(i));
    }
}
