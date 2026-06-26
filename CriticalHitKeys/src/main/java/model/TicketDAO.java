package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    public void doSave (Ticket t) {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement s = conn.prepareStatement("INSERT INTO Ticket (ID_Ticket, Campo, Descrizione_Ticket, Username_Ut, Email_Ut) VALUES (?, ?, ?, ?, ?)");
            s.setInt(1, t.getID_Ticket());
            s.setString(2, t.getCampo());
            s.setString(3, t.getDescrizioneTicket());
            s.setString(4, t.getUsernameUtente());
            s.setString(5, t.getEmailUtente());

            if (s.executeUpdate() != 1) {
                System.out.println("ERRORE nell'INSERT del Ticket");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ticket> doRetrieveAll () {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Ticket");
            ResultSet rs = s.executeQuery();

            List<Ticket> listaTicket = new ArrayList<>();

            while (rs.next()) {
                Ticket t = new Ticket();
                t.setID_Ticket(rs.getInt("ID_Ticket"));
                t.setCampo(rs.getString("Campo"));
                t.setDescrizioneTicket(rs.getString("Descrizione_Ticket"));
                t.setUsernameUtente(rs.getString("Username_Ut"));
                t.setEmailUtente(rs.getString("Email_Ut"));

                listaTicket.add(t);
            }
            return listaTicket;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ticket> doRetrieveByUsernameUtente (String nomeUtente) {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Ticket WHERE Username_Ut = ?");
            s.setString(1,  nomeUtente);
            ResultSet rs = s.executeQuery();

            List<Ticket> listaTicket = new ArrayList<>();


            while (rs.next()) {
                Ticket t = new Ticket();
                t.setID_Ticket(rs.getInt("ID_Ticket"));
                t.setCampo(rs.getString("Campo"));
                t.setDescrizioneTicket(rs.getString("Descrizione_Ticket"));
                t.setEmailUtente(rs.getString("Email_Ut"));

                listaTicket.add(t);
            }
            return listaTicket;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
