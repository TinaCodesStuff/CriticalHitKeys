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

    public Prodotto doRetrieveById (int id) {
        try (Connection conn = ConPool.getConnection()) {
            Prodotto p = new Prodotto();
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Prodotto WHERE ID_Prodotto = ?");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();

            while(rs.next()) {

                p.setNome(rs.getString("Nome"));
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
}
