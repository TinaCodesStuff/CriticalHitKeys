package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UtenteDAO {
    public void doSave(Utente utente){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO Utente (Username_Ut, Email_Ut, Password_Ut) VALUES(?,?,?)");
            ps.setString(1, utente.getUsername_Ut());
            ps.setString(2, utente.getEmail_Ut());
            ps.setString(3, utente.getPassword_Ut());

            if(ps.executeUpdate() != 1){
                System.out.println("INSERT Error in Utente");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Utente doRetrieveUser(String emailUsername){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT Username_Ut, Email_Ut, Password_Ut FROM Utente WHERE Email_Ut = ? OR Username_Ut = ?LIMIT 1");
            ps.setString(1, emailUsername);
            ps.setString(2, emailUsername);

            ResultSet r = ps.executeQuery();

            if(r.next()){
                Utente u = new Utente();

                u.setEmail_Ut(r.getString("Email_Ut"));
                u.setUsername_Ut(r.getString("Username_Ut"));
                u.setPassword_Ut(r.getString("Password_Ut"));
                return u;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsByUsername(String username) {
        return existsByQuery("SELECT 1 FROM Utente WHERE Username_Ut = ? LIMIT 1", username);
    }

    public boolean existsByEmail(String email) {
        return existsByQuery("SELECT 1 FROM Utente WHERE Email_Ut = ? LIMIT 1", email);
    }

    private boolean existsByQuery(String query, String value) {
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, value);

            ResultSet r = ps.executeQuery();
            return r.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Utente> doRetrieveAll(){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT Username_Ut, Email_Ut FROM Utente");

            ResultSet r = ps.executeQuery();

            List<Utente> listaUtenti = new ArrayList<>();

            while(r.next()){
                Utente u = new Utente();

                u.setEmail_Ut(r.getString("Email_Ut"));
                u.setUsername_Ut(r.getString("Username_Ut"));

                listaUtenti.add(u);
            }

            return listaUtenti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
