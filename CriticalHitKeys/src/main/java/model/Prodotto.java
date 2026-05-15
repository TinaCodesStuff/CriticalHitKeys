package model;

public class Prodotto {
    private int ID_Prodotto;
    private String nome;
    private String descrizione;
    private float prezzo_OG;
    private float prezzo_scontato;
    private String modalita_Gioco;
    private String casa_sviluppatrice;
    private int Sconto;
    private String eMailAmm;


    public int getID_Prodotto() {
        return ID_Prodotto;
    }

    public void setID_Prodotto(int ID_Prodotto) {
        this.ID_Prodotto = ID_Prodotto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public float getPrezzo_OG() {
        return prezzo_OG;
    }

    public void setPrezzo_OG(float prezzo_OG) {
        this.prezzo_OG = prezzo_OG;
    }

    public float getPrezzo_scontato() {
        return prezzo_scontato;
    }

    public void setPrezzo_scontato(float prezzo_scontato) {
        this.prezzo_scontato = prezzo_scontato;
    }

    public String getModalita_Gioco() {
        return modalita_Gioco;
    }

    public void setModalita_Gioco(String modalita_Gioco) {
        this.modalita_Gioco = modalita_Gioco;
    }

    public String getCasa_sviluppatrice() {
        return casa_sviluppatrice;
    }

    public void setCasa_sviluppatrice(String casa_sviluppatrice) {
        this.casa_sviluppatrice = casa_sviluppatrice;
    }

    public int getSconto() {
        return Sconto;
    }

    public void setSconto(int sconto) {
        Sconto = sconto;
    }

    public String geteMailAmm() {
        return eMailAmm;
    }

    public void seteMailAmm(String eMailAmm) {
        this.eMailAmm = eMailAmm;
    }
}
