package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarrelloDAO {
    public void doSave(Utente u) {
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Carrello (Username_Ut, Email_Ut) VALUES (?, ?)");
            ps.setString(1, u.getUsername_Ut());
            ps.setString(2, u.getEmail_Ut());



            if(ps.executeUpdate() != 1){
                System.out.println("Errore nell'INSERT del Carrello");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Prodotto> doRetrieveAllByUtente(Utente utente) {
        try(Connection conn = ConPool.getConnection()){
            List<Prodotto> lista = new ArrayList<>();
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Carrello Ca JOIN Contiene Co ON (Ca.ID_Carrello = Co.ID_Carrello) JOIN Prodotto Po ON (Co.ID_Prodotto = Po.ID_Prodotto) WHERE Ca.Username_Ut = ? AND Ca.Email_Ut = ?");
            s.setString(1, utente.getUsername_Ut());
            s.setString(2, utente.getEmail_Ut());

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

    public int doRetrieveID_Carrello(Utente utente) {
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement s = conn.prepareStatement("SELECT ID_Carrello FROM Carrello WHERE Username_Ut = ? AND Email_Ut = ?");
            s.setString(1, utente.getUsername_Ut());
            s.setString(2, utente.getEmail_Ut());

            ResultSet rs = s.executeQuery();

            if(rs.next()){
                return rs.getInt("ID_Carrello");
            } else{
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
