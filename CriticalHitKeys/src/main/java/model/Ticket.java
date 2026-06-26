package model;

public class Ticket {
    private int ID_Ticket;
    private String campo;
    private String descrizioneTicket;
    private String usernameUtente;
    private String emailUtente;

    public int getID_Ticket() {
        return ID_Ticket;
    }

    public void setID_Ticket(int ID_Ticket) {
        this.ID_Ticket = ID_Ticket;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getDescrizioneTicket() {
        return descrizioneTicket;
    }

    public void setDescrizioneTicket(String descrizioneTicket) {
        this.descrizioneTicket = descrizioneTicket;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public String getUsernameUtente() {
        return usernameUtente;
    }

    public void setUsernameUtente(String usernameUtente) {
        this.usernameUtente = usernameUtente;
    }
}
