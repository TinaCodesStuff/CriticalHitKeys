package model;

public class Recensione {
    /*     ID_Recensione INT PRIMARY KEY,
    Voto INT CHECK (Voto >= 1 AND Voto <= 5),
    Descrizione_Rec TEXT,
    Username_Ut VARCHAR(20),
    Email_Ut VARCHAR(30),

     */

    private int ID_Recensione;
    private int Voto;
    private String Descrizione_Rec;
    private String Username_Ut;
    private String Email_Ut;

    public int getID_Recensione() {
        return ID_Recensione;
    }

    public void setID_Recensione(int ID_Recensione) {
        this.ID_Recensione = ID_Recensione;
    }

    public int getVoto() {
        return Voto;
    }

    public void setVoto(int voto) {
        Voto = voto;
    }

    public String getDescrizione_Rec() {
        return Descrizione_Rec;
    }

    public void setDescrizione_Rec(String descrizione_Rec) {
        Descrizione_Rec = descrizione_Rec;
    }

    public String getUsername_Ut() {
        return Username_Ut;
    }

    public void setUsername_Ut(String username_Ut) {
        Username_Ut = username_Ut;
    }

    public String getEmail_Ut() {
        return Email_Ut;
    }

    public void setEmail_Ut(String email_Ut) {
        Email_Ut = email_Ut;
    }
}
