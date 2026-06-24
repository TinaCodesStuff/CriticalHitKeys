package model;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utente {
    private String username_Ut;
    private String email_Ut;
    private String passwordHash;

    public String getUsername_Ut() {
        return username_Ut;
    }

    public void setUsername_Ut(String username_Ut) {
        this.username_Ut = username_Ut;
    }

    public String getEmail_Ut() {
        return email_Ut;
    }

    public void setEmail_Ut(String email_Ut) {
        this.email_Ut = email_Ut;
    }

    public String getPassword_Ut() {
        return this.passwordHash;
    }

    public void setPassword_Ut(String password_Ut) {
        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password_Ut.getBytes(StandardCharsets.UTF_8));
            this.passwordHash = String.format("%040x", new
                    BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
