package model;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Utente {
    private String username_Ut;
    private String email_Ut;
    private String password_Ut;

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
        return this.password_Ut;
    }

    public void setPassword_Ut(String password_Ut) {
        this.password_Ut = password_Ut;
    }
}
