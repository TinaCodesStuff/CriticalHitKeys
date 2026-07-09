package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminProdottoDAO {

    public int doSave(Prodotto prodotto, List<Genere> listaGeneri,  List<AdminMedia> listaMedia) {
        // prepara l'inserimento del prodotto
        String sql = "INSERT INTO Prodotto (Nome, Descrizione_Prod, Prezzo_OG, Prezzo_Scontato, " +
                "Modalita_Gioco, Casa_Sviluppatrice, Sconto, Email_Amm, Disponibile) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE)";

        try (Connection connection = ConPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                setCommonParameters(statement, prodotto);
                statement.setString(8, prodotto.geteMailAmm());
                statement.executeUpdate();

                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new SQLException("ID del prodotto non generato");
                    }
                    prodotto.setID_Prodotto(keys.getInt(1));
                }

                // salva le piattaforme nella stessa transazione
                insertPiattaforme(connection, prodotto.getID_Prodotto(), prodotto.getPiattaforme());
                insertMedia(connection, prodotto.getID_Prodotto(), listaMedia);
                insertGeneri(connection, prodotto.getID_Prodotto(), listaGeneri);
                connection.commit();
                return prodotto.getID_Prodotto();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doUpdate(Prodotto prodotto) {
        // prepara l'aggiornamento dei dati principali
        String sql = "UPDATE Prodotto SET Nome=?, Descrizione_Prod=?, Prezzo_OG=?, Prezzo_Scontato=?, " +
                "Modalita_Gioco=?, Casa_Sviluppatrice=?, Sconto=? WHERE ID_Prodotto=?";

        try (Connection connection = ConPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setCommonParameters(statement, prodotto);
                statement.setInt(8, prodotto.getID_Prodotto());
                if (statement.executeUpdate() != 1) {
                    connection.rollback();
                    return false;
                }

                // sostituisce le piattaforme solo quando il form le invia
                if (prodotto.getPiattaforme() != null) {
                    try (PreparedStatement delete = connection.prepareStatement(
                            "DELETE FROM Piattaforma WHERE ID_Prodotto=?")) {
                        delete.setInt(1, prodotto.getID_Prodotto());
                        delete.executeUpdate();
                    }
                    insertPiattaforme(connection, prodotto.getID_Prodotto(), prodotto.getPiattaforme());
                }
                connection.commit();
                return true;
            } catch (Exception e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String doRemoveOrDisable(int idProdotto) {
        try (Connection connection = ConPool.getConnection()) {
            connection.setAutoCommit(false);
            try {
                // blocca il prodotto durante il controllo dei riferimenti
                if (!existsForUpdate(connection, idProdotto)) {
                    connection.rollback();
                    return "not-found";
                }

                // mantiene il prodotto quando è usato da carrelli o recensioni
                if (isReferenced(connection, idProdotto)) {
                    try (PreparedStatement update = connection.prepareStatement(
                            "UPDATE Prodotto SET Disponibile=FALSE WHERE ID_Prodotto=?")) {
                        update.setInt(1, idProdotto);
                        update.executeUpdate();
                    }
                    connection.commit();
                    return "disabled";
                }

                // elimina definitivamente il prodotto senza riferimenti
                try (PreparedStatement delete = connection.prepareStatement(
                        "DELETE FROM Prodotto WHERE ID_Prodotto=?")) {
                    delete.setInt(1, idProdotto);
                    delete.executeUpdate();
                }
                connection.commit();
                return "deleted";
            } catch (Exception e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> doRetrievePiattaforme() {
        List<String> piattaforme = new ArrayList<>();
        String sql = "SELECT DISTINCT Piattaforma FROM Piattaforma ORDER BY Piattaforma";
        try (Connection connection = ConPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                piattaforme.add(result.getString("Piattaforma"));
            }
            return piattaforme;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCommonParameters(PreparedStatement statement, Prodotto prodotto) throws SQLException {
        statement.setString(1, prodotto.getNome());
        statement.setString(2, prodotto.getDescrizione());
        statement.setFloat(3, prodotto.getPrezzo_OG());
        statement.setFloat(4, prodotto.getPrezzo_scontato());
        statement.setString(5, prodotto.getModalita_Gioco());
        statement.setString(6, prodotto.getCasa_sviluppatrice());
        statement.setInt(7, prodotto.getSconto());
    }

    private void insertPiattaforme(Connection connection, int idProdotto, List<String> piattaforme)
            throws SQLException {
        if (piattaforme == null || piattaforme.isEmpty()) {
            return;
        }
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Piattaforma (Piattaforma, ID_Prodotto) VALUES (?, ?)")) {
            for (String piattaforma : piattaforme) {
                statement.setString(1, piattaforma);
                statement.setInt(2, idProdotto);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void insertMedia(Connection connection, int idProdotto, List<AdminMedia> listaMedia)
            throws SQLException {
        if (listaMedia == null || listaMedia.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO MediaProdotto (ID_Media, ID_Prodotto, Tipo, URL_Media) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (AdminMedia media : listaMedia) {
                statement.setString(1, media.getIdMedia());
                statement.setInt(2, idProdotto);
                statement.setString(3, media.getTipo());
                statement.setString(4, media.getUrlMedia());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    public void insertGeneri(Connection connection, int idProdotto, List<Genere> listaGeneri){
        if (listaGeneri == null || listaGeneri.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO Genere (Genere, ID_Prodotto) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Genere genere : listaGeneri) {
                statement.setString(1, genere.getGenere());
                statement.setInt(2, idProdotto);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean existsForUpdate(Connection connection, int idProdotto) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT ID_Prodotto FROM Prodotto WHERE ID_Prodotto=? FOR UPDATE")) {
            statement.setInt(1, idProdotto);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        }
    }

    private boolean isReferenced(Connection connection, int idProdotto) throws SQLException {
        String sql = "SELECT EXISTS(SELECT 1 FROM Contiene WHERE ID_Prodotto=?) OR " +
                "EXISTS(SELECT 1 FROM Recensione WHERE ID_Prodotto=?) AS Referenziato";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idProdotto);
            statement.setInt(2, idProdotto);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() && result.getBoolean("Referenziato");
            }
        }
    }
}
