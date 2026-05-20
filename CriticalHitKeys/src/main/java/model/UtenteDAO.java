package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.SQLException;

public class UtenteDAO {
    public void doSave(String username, String email, String password){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO Utente VALUES(?,?,?)");
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);

            if(ps.executeUpdate() != 1){
                System.out.println("INSERT Error in Utente");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Utente doRetrieveUser(String emailUsername){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Utente WHERE Email_Ut = ? OR Username_Ut = ?");
            ps.setString(1, emailUsername);
            ps.setString(2, emailUsername);

            ResultSet r = ps.executeQuery();

            Utente u = new Utente();
            while(r.next()){

                u.setEmail_Ut(r.getString("Email_Ut"));
                u.setUsername_Ut(r.getString("Username_Ut"));
                u.setPassword_Ut(r.getString("Password_Ut"));
            }
            return u;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Utente> doRetrieveAll(){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELCT (Username_Ut, Email_Ut) FROM Utente");

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
