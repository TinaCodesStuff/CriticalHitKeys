package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {
    public void doSave(Ordine o) {
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Ordine (ID_Ordine, Importo_tot, DataOrdine, Descrizione_Acquisto, ID_Carrello) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, o.getID_Ordine());
            ps.setFloat(2, o.getImportoTot());
            ps.setObject(3, o.getData_Ordine());
            ps.setString(4, o.getDescrizione_Acquisto());
            ps.setInt(5, o.getID_Carrello());


            if(ps.executeUpdate() != 1){
                System.out.println("Errore nell'INSERT del Carrello");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Ordine> doRetrieveAllByID_Carrello(int ID_Carrello) {
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Ordine WHERE ID_Carrello = ?");
            ps.setInt(1, ID_Carrello);

            List<Ordine> ordini = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Ordine o = new Ordine();
                o.setID_Ordine(rs.getInt("ID_Ordine"));
                o.setImportoTot(rs.getFloat("Importo_tot"));
                o.setData_Ordine(rs.getObject("DataOrdine", LocalDateTime.class));
                o.setDescrizione_Acquisto(rs.getString("Descrizione_Acquisto"));
                o.setID_Carrello(rs.getInt("ID_Carrello"));

                ordini.add(o);
            }

            return ordini;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
