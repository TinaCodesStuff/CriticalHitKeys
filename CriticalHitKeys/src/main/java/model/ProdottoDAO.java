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
                p.setDisponibile(rs.getBoolean("Disponibile"));
                p.setPiattaforme(doRetrievePiattaformeByProdotto(p.getID_Prodotto()));

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
                p.setPiattaforme(doRetrievePiattaformeByProdotto(p.getID_Prodotto()));
                p.setDisponibile(rs.getBoolean("Disponibile"));
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
                p.setPiattaforme(doRetrievePiattaformeByProdotto(p.getID_Prodotto()));
                p.setDisponibile(rs.getBoolean("Disponibile"));


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

                p.setPiattaforme(doRetrievePiattaformeByProdotto(p.getID_Prodotto()));

                p.setDisponibile(rs.getBoolean("Disponibile"));


                listaByNome.add(p);

            }

            return listaByNome;

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }



    public List<Prodotto> doRetrieveProdottoByGenere (List<String> generi) {

        List<Prodotto> listaProdotti = new ArrayList<>();

        String placeholders = String.join(",", Collections.nCopies(generi.size(), "?"));

        String sql = "SELECT DISTINCT p.* " +   //creiamo una query che ci permetta di filtrare su più generi
                "FROM Prodotto p " +
                "JOIN Genere g ON p.ID_Prodotto = g.ID_Prodotto " +
                "WHERE g.Genere IN (" + placeholders + ")";

        try(Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            for(int i = 0; i < generi.size(); i++) {    //qui inseriamo i generi nella query così vediamo quale prodotto ne fa parte
                ps.setString(i + 1, generi.get(i));
            }

            ResultSet rs = ps.executeQuery();   //eseguo la query

            while(rs.next()) {
                Prodotto p = new Prodotto();
                p.setNome(rs.getString("Nome"));
                p.setID_Prodotto(rs.getInt("ID_Prodotto"));
                p.setDescrizione(rs.getString("Descrizione_Prod"));
                p.setPrezzo_OG(rs.getFloat("Prezzo_OG"));
                p.setPrezzo_scontato(rs.getFloat("Prezzo_Scontato"));
                p.setModalita_Gioco(rs.getString("Modalita_Gioco"));
                p.setSconto(rs.getInt("Sconto"));
                p.seteMailAmm(rs.getString("Email_Amm"));
                p.setPiattaforme(doRetrievePiattaformeByProdotto(p.getID_Prodotto()));
                p.setDisponibile(rs.getBoolean("Disponibile"));

                listaProdotti.add(p);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

        return listaProdotti;

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

                p.setPiattaforme(doRetrievePiattaformeByProdotto(p.getID_Prodotto()));

                p.setDisponibile(rs.getBoolean("Disponibile"));


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

                p.setPiattaforme(doRetrievePiattaformeByProdotto(p.getID_Prodotto()));

                p.setDisponibile(rs.getBoolean("Disponibile"));


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

                p.setPiattaforme(doRetrievePiattaformeByProdotto(p.getID_Prodotto()));

                p.setDisponibile(rs.getBoolean("Disponibile"));


                listaByPrezzo.add(p);

            }

            return listaByPrezzo;

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public List<Prodotto> filtraProdotti(List<String> generi, String casa, Float min, Float max , String mod_gioco) {    //questo metodo riutilizza tutte le funzioni create precedentemente, e funziona per tutti i filtri

        ProdottoDAO dao = new ProdottoDAO();

        List<Prodotto> result = dao.doRetrieveAll();

        if (generi != null && !generi.isEmpty()) {
            result.retainAll(dao.doRetrieveProdottoByGenere(generi));
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

    public List<String> doRetrievePiattaformeByProdotto(int idProdotto) {
        List<String> piattaforme = new ArrayList<>();

        try (Connection conn = ConPool.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT Piattaforma FROM Piattaforma WHERE ID_Prodotto = ?");

            ps.setInt(1, idProdotto);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                piattaforme.add(rs.getString("Piattaforma"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return piattaforme;
    }



}
