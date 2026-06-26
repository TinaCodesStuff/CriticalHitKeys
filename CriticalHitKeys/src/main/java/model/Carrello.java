package model;

public class Carrello {
    private int ID_Carrello;
    private String usernameUtente;
    private String emailUtente;

    public int getID_Carrello() {
        return ID_Carrello;
    }

    public String getUsernameUtente() {
        return usernameUtente;
    }

    public void setUsernameUtente(String usernameUtente) {
        this.usernameUtente = usernameUtente;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }
}
