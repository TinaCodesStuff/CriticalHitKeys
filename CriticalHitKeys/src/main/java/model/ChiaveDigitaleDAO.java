package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class ChiaveDigitaleDAO {
    public void doSave (ChiaveDigitale chiave) {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement s = conn.prepareStatement("INSERT INTO ChiaveDigitale(ID_Prodotto, Chiave) VALUES (?, ?)");
            s.setInt(1, chiave.getID_Prodotto() );
            s.setString(2, chiave.getChiave());

            if (s.executeUpdate() != 1) {
                System.out.println("INSERT ERROR in ChiaveDigitale");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ChiaveDigitale doRetrieveChiaveByCodice (String codiceChiave) {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM ChiaveDigitale WHERE Chiave = ?");
            s.setString(1, codiceChiave);
            ResultSet rs = s.executeQuery();
            ChiaveDigitale chiave = new ChiaveDigitale();
            while (rs.next()) {
                chiave.setID_Prodotto(rs.getInt("ID_Prodotto"));
                chiave.setChiave(rs.getString("Chiave"));
            }
            return chiave;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ChiaveDigitale doRetrieveChiaveByID_Prodotto (int ID_Prodotto) throws SQLException {
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement s = conn.prepareStatement("SELECT * FROM ChiaveDigitale WHERE ID_Prodotto = ?");
            s.setInt(1, ID_Prodotto);
            ResultSet rs = s.executeQuery();
            ChiaveDigitale chiave = new ChiaveDigitale();
            while (rs.next()) {
                chiave.setID_Prodotto(rs.getInt("ID_Prodotto"));
                chiave.setChiave(rs.getString("Chiave"));
            }
            return chiave;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String generaCodiceRandomico () {
        String caratteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        //LA CHIAVE AVRA' LA FORMA XXXXX-XXXXX-XXXXX
        for (int blocco = 0; blocco < 3; blocco++) {
            for (int i = 0; i < 5; i++) {
                int index = random.nextInt(caratteri.length());
                sb.append(caratteri.charAt(index));
            }
            if (blocco < 2) {
                sb.append("-");
            }
        }
        System.out.println("Chiave generata: " + sb);
        return sb.toString();
    }
}
