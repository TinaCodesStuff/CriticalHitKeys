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

    public List<Prodotto> doRetrieveSuggestProduct () {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Prodotto LIMIT 7");

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

}
