package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ContieneDAO {

    public void doSave(Contiene contiene) {
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Contiene (ID_Carrello, ID_Prodotto, Quantita) VALUES (?, ?, ?)");
            ps.setInt(1, contiene.getID_Carrello());
            ps.setInt(2, contiene.getID_Prodotto());
            ps.setInt(3, contiene.getQuanita());

            if(ps.executeUpdate() != 1){
                System.out.println("Errore nell'INSERT del Contiene");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdate(Contiene contiene) {
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("UPDATE Contiene SET Quantita = ? WHERE ID_Carrello = ? AND ID_Prodotto = ?");
            ps.setInt(1, contiene.getQuanita());
            ps.setInt(2, contiene.getID_Carrello());
            ps.setInt(3, contiene.getID_Prodotto());


            if(ps.executeUpdate() != 1){
                System.out.println("Errore nell'UPDATE del Contiene");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Contiene findByID_Carrello(int id_carrello) {
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Contiene WHERE ID_Carrello = ?");
            ps.setInt(1, id_carrello);

            ResultSet rs = ps.executeQuery();

            Contiene contiene = new Contiene();


            while(rs.next()){
                contiene.setID_Carrello(rs.getInt("ID_Carrello"));
                contiene.setID_Prodotto(rs.getInt("ID_Prodotto"));
                contiene.setQuantita(rs.getInt("Quantita"));
            }

            return contiene;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, Integer> doRetrieveQuantitaById_Carrello(int ID_Carrello) {
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT ID_Prodotto, Quantita FROM Contiene WHERE ID_Carrello = ?");
            ps.setInt(1, ID_Carrello);

            ResultSet rs = ps.executeQuery();

            Map<Integer, Integer> quantita= new HashMap<Integer, Integer>();


            while(rs.next()){
                quantita.put(rs.getInt("ID_Prodotto"), rs.getInt("Quantita"));
            }

            return quantita;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
