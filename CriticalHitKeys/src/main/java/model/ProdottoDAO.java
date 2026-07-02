package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.SQLException;

public class ProdottoDAO {
    public List<Prodotto> doRetrieveAll(){
        try(Connection conn = ConPool.getConnection()){
            List<Prodotto> lista = new ArrayList<>();
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Prodotto");
            ResultSet rs = s.executeQuery();

            while(rs.next()){
                Prodotto p = new Prodotto();

                p.setID_Prodotto(rs.getInt("ID_Prodotto"));
                p.setNome(rs.getString("Nome"));
                p.setDescrizione(rs.getString("Descrizione_Prod"));
                p.setPrezzo_OG(rs.getFloat("Prezzo_OG"));
                p.setPrezzo_scontato(rs.getFloat("Prezzo_Scontato"));
                p.setModalita_Gioco(rs.getString("Modalita_Gioco"));
                p.setCasa_sviluppatrice(rs.getString("Casa_Sviluppatrice"));
                p.setSconto(rs.getInt("Sconto"));
                p.seteMailAmm(rs.getString("Email_Amm"));
                lista.add(p);
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Prende un prodotto in base all'Id dato tramite ricerca
    public Prodotto doRetrieveById (int id) {
        try (Connection conn = ConPool.getConnection()) {
            Prodotto p = new Prodotto();
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Prodotto WHERE ID_Prodotto = ?");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();

            while(rs.next()) {

                p.setNome(rs.getString("Nome"));
                p.setID_Prodotto(rs.getInt("ID_Prodotto"));
                p.setDescrizione(rs.getString("Descrizione_Prod"));
                p.setPrezzo_OG(rs.getFloat("Prezzo_OG"));
                p.setPrezzo_scontato(rs.getFloat("Prezzo_Scontato"));
                p.setModalita_Gioco(rs.getString("Modalita_Gioco"));
                p.setCasa_sviluppatrice(rs.getString("Casa_Sviluppatrice"));
                p.setSconto(rs.getInt("Sconto"));
                p.seteMailAmm(rs.getString("Email_Amm"));
                return p;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Matcha l'ID prodotto del media con quello dell'effettivo prodotto a cui è assegnato
    public List<Media> doRetrieveMediaByProdotto (int idProdotto) {
        List<Media> listaMedia = new ArrayList<>();

        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM MediaProdotto WHERE ID_Prodotto = ?");
            s.setInt(1, idProdotto);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                Media media = new Media();

                media.setTipo(rs.getString("Tipo"));
                media.setUrlMedia(rs.getString("URL_Media"));
                listaMedia.add(media);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaMedia;
    }

    public List<Prodotto> doRetrieveSuggestProduct (String[] suggested) {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Prodotto WHERE Nome IN (?, ?, ?, ?, ?, ?)");

            s.setString(1, suggested[0]);
            s.setString(2, suggested[1]);
            s.setString(3, suggested[2]);
            s.setString(4, suggested[3]);
            s.setString(5, suggested[4]);
            s.setString(6, suggested[5]);

            ResultSet rs = s.executeQuery();

            List<Prodotto> listaProdotto = new ArrayList<>();   //qui memorizzo tutti i Prodotti consigliati

            while(rs.next()) {  //qui per ogni prodotto creo un istanza della classe Prodotto, poi la memorizzo nella lista
                Prodotto p = new Prodotto();
                p.setNome(rs.getString("Nome"));
                p.setID_Prodotto(rs.getInt("ID_Prodotto"));
                p.setDescrizione(rs.getString("Descrizione_Prod"));
                p.setPrezzo_OG(rs.getFloat("Prezzo_OG"));
                p.setPrezzo_scontato(rs.getFloat("Prezzo_Scontato"));
                p.setModalita_Gioco(rs.getString("Modalita_Gioco"));
                p.setCasa_sviluppatrice(rs.getString("Casa_Sviluppatrice"));
                p.setSconto(rs.getInt("Sconto"));
                p.seteMailAmm(rs.getString("Email_Amm"));

                listaProdotto.add(p);
            }
            return listaProdotto;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> doRetrieveProdottoByNome (String nome) {

        List<Prodotto> listaByNome = new ArrayList<>();



        try (Connection conn = ConPool.getConnection()) {

            PreparedStatement s = conn.prepareStatement("SELECT * FROM Prodotto p WHERE p.Nome LIKE ?");

            s.setString(1, nome + "%");

            ResultSet rs = s.executeQuery();



            while (rs.next()) {

                Prodotto p = new Prodotto();

                p.setNome(rs.getString("Nome"));

                p.setID_Prodotto(rs.getInt("ID_Prodotto"));

                p.setDescrizione(rs.getString("Descrizione_Prod"));

                p.setPrezzo_OG(rs.getFloat("Prezzo_OG"));

                p.setPrezzo_scontato(rs.getFloat("Prezzo_Scontato"));

                p.setModalita_Gioco(rs.getString("Modalita_Gioco"));

                p.setCasa_sviluppatrice(rs.getString("Casa_Sviluppatrice"));

                p.setSconto(rs.getInt("Sconto"));

                p.seteMailAmm(rs.getString("Email_Amm"));

                listaByNome.add(p);

            }

            return listaByNome;

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }



    public List<Prodotto> doRetrieveProdottoByGenere (String tipo) {

        List<Prodotto> listaProdotti = new ArrayList<>();

        try (Connection conn = ConPool.getConnection()) {

            PreparedStatement s = conn.prepareStatement("SELECT * FROM Prodotto p JOIN Genere g ON p.ID_Prodotto = g.ID_Prodotto WHERE g.Genere = ?");

            s.setString(1, tipo);

            ResultSet rs = s.executeQuery();



            while (rs.next()) {

                Prodotto p = new Prodotto();

                p.setNome(rs.getString("Nome"));

                p.setID_Prodotto(rs.getInt("ID_Prodotto"));

                p.setDescrizione(rs.getString("Descrizione_Prod"));

                p.setPrezzo_OG(rs.getFloat("Prezzo_OG"));

                p.setPrezzo_scontato(rs.getFloat("Prezzo_Scontato"));

                p.setModalita_Gioco(rs.getString("Modalita_Gioco"));

                p.setCasa_sviluppatrice(rs.getString("Casa_Sviluppatrice"));

                p.setSconto(rs.getInt("Sconto"));

                p.seteMailAmm(rs.getString("Email_Amm"));

                listaProdotti.add(p);

            }

            return listaProdotti;

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }



    public List<Prodotto> doRetrieveProdottoByCasaSviluppatrice (String casaSviluppatrice) {

        List<Prodotto> lista = new ArrayList<>();



        try (Connection conn = ConPool.getConnection()) {

            PreparedStatement s = conn.prepareStatement("SELECT * FROM Prodotto WHERE Casa_Sviluppatrice LIKE ?");

            s.setString(1, casaSviluppatrice);

            ResultSet rs = s.executeQuery();



            while (rs.next()) {

                Prodotto p = new Prodotto();

                p.setNome(rs.getString("Nome"));

                p.setID_Prodotto(rs.getInt("ID_Prodotto"));

                p.setDescrizione(rs.getString("Descrizione_Prod"));

                p.setPrezzo_OG(rs.getFloat("Prezzo_OG"));

                p.setPrezzo_scontato(rs.getFloat("Prezzo_Scontato"));

                p.setModalita_Gioco(rs.getString("Modalita_Gioco"));

                p.setCasa_sviluppatrice(rs.getString("Casa_Sviluppatrice"));

                p.setSconto(rs.getInt("Sconto"));

                p.seteMailAmm(rs.getString("Email_Amm"));

                lista.add(p);

            }

            return lista;

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }



    public List<Prodotto> doRetrieveProdottoByModalitaGioco (String modalitaGioco) {

        List<Prodotto> lista = new ArrayList<>();



        try (Connection conn = ConPool.getConnection()) {

            PreparedStatement s = conn.prepareStatement("SELECT * FROM Prodotto p WHERE p.Modalita_Gioco LIKE ? ");

            s.setString(1, modalitaGioco);

            ResultSet rs = s.executeQuery();



            while (rs.next()) {

                Prodotto p = new Prodotto();

                p.setNome(rs.getString("Nome"));

                p.setID_Prodotto(rs.getInt("ID_Prodotto"));

                p.setDescrizione(rs.getString("Descrizione_Prod"));

                p.setPrezzo_OG(rs.getFloat("Prezzo_OG"));

                p.setPrezzo_scontato(rs.getFloat("Prezzo_Scontato"));

                p.setModalita_Gioco(rs.getString("Modalita_Gioco"));

                p.setCasa_sviluppatrice(rs.getString("Casa_Sviluppatrice"));

                p.setSconto(rs.getInt("Sconto"));

                p.seteMailAmm(rs.getString("Email_Amm"));

                lista.add(p);

            }

            return lista;

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public List<Prodotto> doRetrieveProdottoByPrezzo (float prezzoMin, float prezzoMax) {

        List<Prodotto> listaByPrezzo = new ArrayList<>();



        try (Connection conn = ConPool.getConnection()) {

            PreparedStatement s = conn.prepareStatement("SELECT * FROM Prodotto WHERE Prezzo_Scontato BETWEEN ? AND ?");

            s.setFloat(1, prezzoMin);

            s.setFloat(2, prezzoMax);

            ResultSet rs = s.executeQuery();



            while (rs.next()) {

                Prodotto p = new Prodotto();

                p.setNome(rs.getString("Nome"));

                p.setID_Prodotto(rs.getInt("ID_Prodotto"));

                p.setDescrizione(rs.getString("Descrizione_Prod"));

                p.setPrezzo_OG(rs.getFloat("Prezzo_OG"));

                p.setPrezzo_scontato(rs.getFloat("Prezzo_Scontato"));

                p.setModalita_Gioco(rs.getString("Modalita_Gioco"));

                p.setCasa_sviluppatrice(rs.getString("Casa_Sviluppatrice"));

                p.setSconto(rs.getInt("Sconto"));

                p.seteMailAmm(rs.getString("Email_Amm"));

                listaByPrezzo.add(p);

            }

            return listaByPrezzo;

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public List<Prodotto> filtraProdotti(String genere, String casa, Float min, Float max , String mod_gioco) {    //questo metodo riutilizza tutte le funzioni create precedentemente, e funziona per tutti i filtri

        ProdottoDAO dao = new ProdottoDAO();

        List<Prodotto> result = dao.doRetrieveAll();

        if (genere != null && !genere.isEmpty()) {
            result.retainAll(dao.doRetrieveProdottoByGenere(genere));
        }

        if (casa != null && !casa.isEmpty()) {
            result.retainAll(dao.doRetrieveProdottoByCasaSviluppatrice(casa));
        }

        if (mod_gioco != null && !mod_gioco.isEmpty()) {
            result.retainAll(dao.doRetrieveProdottoByModalitaGioco(mod_gioco));
        }

        if (min != null && max != null) {
            result.retainAll(dao.doRetrieveProdottoByPrezzo(min, max));
        }

        return result;
    }

}
