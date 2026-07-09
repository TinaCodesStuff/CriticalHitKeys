package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminUtenteDAO {

    public List<AdminUtenteInfo> doRetrieveAll() {
        try (Connection connection = ConPool.getConnection()) {
            // recupera tutti gli utenti registrati
            List<AdminUtenteInfo> lista = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Utente ORDER BY Username_Ut");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Utente utente = new Utente();
                utente.setUsername_Ut(result.getString("Username_Ut"));
                utente.setEmail_Ut(result.getString("Email_Ut"));

                AdminUtenteInfo info = new AdminUtenteInfo();
                info.setUtente(utente);
                info.setID_Carrello(doRetrieveID_Carrello(connection, utente));
                info.setProdottiCarrello(doRetrieveProdottiCarrello(connection, info.getID_Carrello()));
                info.setOrdini(doRetrieveOrdini(connection, info.getID_Carrello()));

                lista.add(info);
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int doRetrieveID_Carrello(Connection connection, Utente utente) throws SQLException {
        // recupera il carrello associato all'utente
        PreparedStatement statement = connection.prepareStatement(
                "SELECT ID_Carrello FROM Carrello WHERE Username_Ut=? AND Email_Ut=?");
        statement.setString(1, utente.getUsername_Ut());
        statement.setString(2, utente.getEmail_Ut());

        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return result.getInt("ID_Carrello");
        }

        return 0;
    }

    private List<AdminCarrelloProdotto> doRetrieveProdottiCarrello(Connection connection, int ID_Carrello)
            throws SQLException {
        // recupera i prodotti presenti nel carrello corrente
        List<AdminCarrelloProdotto> lista = new ArrayList<>();
        if (ID_Carrello == 0) {
            return lista;
        }

        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Contiene c JOIN Prodotto p ON c.ID_Prodotto=p.ID_Prodotto " +
                        "WHERE c.ID_Carrello=?");
        statement.setInt(1, ID_Carrello);

        ResultSet result = statement.executeQuery();
        while (result.next()) {
            Prodotto prodotto = new Prodotto();
            prodotto.setID_Prodotto(result.getInt("ID_Prodotto"));
            prodotto.setNome(result.getString("Nome"));
            prodotto.setPrezzo_OG(result.getFloat("Prezzo_OG"));
            prodotto.setPrezzo_scontato(result.getFloat("Prezzo_Scontato"));
            prodotto.setSconto(result.getInt("Sconto"));

            AdminCarrelloProdotto carrelloProdotto = new AdminCarrelloProdotto();
            carrelloProdotto.setProdotto(prodotto);
            carrelloProdotto.setQuantita(result.getInt("Quantita"));

            lista.add(carrelloProdotto);
        }

        return lista;
    }

    private List<Ordine> doRetrieveOrdini(Connection connection, int ID_Carrello) throws SQLException {
        // recupera gli ordini passati collegati al carrello
        List<Ordine> lista = new ArrayList<>();
        if (ID_Carrello == 0) {
            return lista;
        }

        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Ordine WHERE ID_Carrello=? ORDER BY DataOrdine DESC");
        statement.setInt(1, ID_Carrello);

        ResultSet result = statement.executeQuery();
        while (result.next()) {
            Ordine ordine = new Ordine();
            ordine.setID_Ordine(result.getInt("ID_Ordine"));
            ordine.setImportoTot(result.getFloat("Importo_tot"));
            ordine.setData_Ordine(result.getObject("DataOrdine", LocalDateTime.class));
            ordine.setDescrizione_Acquisto(result.getString("Descrizione_Acquisto"));
            ordine.setID_Carrello(result.getInt("ID_Carrello"));

            lista.add(ordine);
        }

        return lista;
    }
}
