package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RecensioneDAO {
    public void doSave(Recensione r) {
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Recensione (ID_Recensione, ID_Prodotto, Voto, Descrizione_Rec, Username_Ut, Email_Ut) VALUES (?, ?,?, ?, ?, ?)");
            ps.setInt(1, r.getID_Recensione());
            ps.setInt(2, r.getID_Prodotto());
            ps.setInt(3, r.getVoto());
            ps.setString(4, r.getDescrizione_Rec());
            ps.setString(5, r.getUsername_Ut());
            ps.setString(6, r.getEmail_Ut());

            if(ps.executeUpdate() != 1){
                System.out.println("Errore nell'INSERT della Recensione");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Recensione> doRetrieveAll() {
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Recensione");

            ResultSet rs = ps.executeQuery();

            if (rs == null) {
                return null;
            }

            List<Recensione> listaRecensione = new ArrayList<>();

            while(rs.next()){
                Recensione r = new Recensione();
                r.setID_Recensione(rs.getInt("ID_Recensione"));
                r.setVoto(rs.getInt("Voto"));
                r.setDescrizione_Rec(rs.getString("Descrizione_Rec"));
                r.setUsername_Ut(rs.getString("Username_Ut"));
                r.setEmail_Ut(rs.getString("Email_Ut"));

                listaRecensione.add(r);
            }
            return listaRecensione;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Recensione> doRetrieveByProdotto(int prodotto) {
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Recensione WHERE ID_Prodotto = ?");
            ps.setInt(1, prodotto);

            ResultSet rs = ps.executeQuery();

            if (rs == null) {
                return null;
            }

            List<Recensione> listaRecensione = new ArrayList<>();

            while(rs.next()){
                Recensione r = new Recensione();
                r.setID_Recensione(rs.getInt("ID_Recensione"));
                r.setVoto(rs.getInt("Voto"));
                r.setDescrizione_Rec(rs.getString("Descrizione_Rec"));
                r.setUsername_Ut(rs.getString("Username_Ut"));
                r.setEmail_Ut(rs.getString("Email_Ut"));

                listaRecensione.add(r);
            }
            return listaRecensione;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
