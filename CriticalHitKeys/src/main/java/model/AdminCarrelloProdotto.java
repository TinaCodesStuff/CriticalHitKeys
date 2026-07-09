package model;

public class AdminCarrelloProdotto {
    // rappresenta una riga del carrello con prodotto e quantità
    private Prodotto prodotto;
    private int quantita;

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
