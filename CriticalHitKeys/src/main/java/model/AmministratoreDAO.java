package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AmministratoreDAO {
    public Amministratore doRetrieveByEmailOrUsername(String value) {
        String sql = "SELECT Email_Amm, Username_Amm, Password_Amm FROM Amministratore " +
                "WHERE Email_Amm = ?";
        try (Connection connection = ConPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, value);

            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) return null;
                Amministratore amministratore = new Amministratore();
                amministratore.setEmail(result.getString("Email_Amm"));
                amministratore.setUsername(result.getString("Username_Amm"));
                amministratore.setPassword(result.getString("Password_Amm"));
                return amministratore;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}