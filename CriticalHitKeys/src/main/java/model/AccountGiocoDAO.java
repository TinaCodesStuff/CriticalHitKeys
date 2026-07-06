package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountGiocoDAO {
    public void doSave (AccountGioco accountGioco) {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement s = conn.prepareStatement("INSERT INTO Account(ID_Prodotto, Credenziali) VALUES (?, ?)");
            s.setInt(1, accountGioco.getID_Prodotto() );
            s.setString(2, accountGioco.getCredenziali());

            if (s.executeUpdate() != 1) {
                System.out.println("INSERT ERROR in ChiaveDigitale");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*public ChiaveDigitale doRetrieveChiaveByCodice (String ) {
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
    }*/

    public AccountGioco doRetrieveCredenzialiByID_Prodotto (int ID_Prodotto) throws SQLException {
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Account WHERE ID_Prodotto = ?");
            s.setInt(1, ID_Prodotto);
            ResultSet rs = s.executeQuery();
            AccountGioco acc = new AccountGioco();
            while (rs.next()) {
                acc.setID_Prodotto(rs.getInt("ID_Prodotto"));
                acc.setCredenziali(rs.getString("Credenziali"));
            }
            return acc;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String generaCredenziali() {
        String[] USERNAMES = {
                "gatto", "leone", "falco", "tigre", "lupo",
                "drago", "aquila", "pantera", "fenice", "cobra",
                "sole", "luna", "stella", "mare", "bosco"
        };

         String CARATTERI =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";
        Random random = new Random();

        // Username: parola + numero
        String username = USERNAMES[random.nextInt(USERNAMES.length)]
                + random.nextInt(1000);

        // Password casuale di 12 caratteri
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(CARATTERI.length());
            password.append(CARATTERI.charAt(index));
        }
        System.out.println("Credenziali generate: " + username + ":" + password);

        return username + ":" + password;
    }

}
