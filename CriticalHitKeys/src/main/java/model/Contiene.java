package model;

public class Contiene {
    private int ID_Prodotto;
    private int ID_Carrello;
    private int quantita;

    public Contiene(int ID_Prodotto, int ID_Carrello, int quantita) {
        this.ID_Prodotto = ID_Prodotto;
        this.ID_Carrello = ID_Carrello;
        this.quantita = quantita;
    }

    public Contiene() {
        this.ID_Prodotto = 0;
        this.ID_Carrello = 0;
        this.quantita = 0;
    }

    public int getID_Prodotto() {
        return ID_Prodotto;
    }

    public void setID_Prodotto(int ID_Prodotto) {
        this.ID_Prodotto = ID_Prodotto;
    }

    public int getID_Carrello() {
        return ID_Carrello;
    }

    public void setID_Carrello(int ID_Carrello) {
        this.ID_Carrello = ID_Carrello;
    }

    public int getQuanita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
