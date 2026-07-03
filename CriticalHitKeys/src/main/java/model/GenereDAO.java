package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GenereDAO {

    public List<Genere> doRetrieveAllGeneri() {
        try(Connection conn = ConPool.getConnection()){
            List<Genere> lista = new ArrayList<>();
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Genere");
            ResultSet rs = s.executeQuery();

            while(rs.next()){
                Genere p = new Genere();

                p.setID_Prodotto(rs.getInt("ID_Prodotto"));
                p.setGenere(rs.getString("Genere"));

                lista.add(p);
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
