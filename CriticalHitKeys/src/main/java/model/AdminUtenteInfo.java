package model;

import java.util.ArrayList;
import java.util.List;

public class AdminUtenteInfo {
    // raccoglie i dati mostrati nella pagina admin degli utenti
    private Utente utente;
    private int ID_Carrello;
    private List<AdminCarrelloProdotto> prodottiCarrello = new ArrayList<>();
    private List<Ordine> ordini = new ArrayList<>();

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public int getID_Carrello() {
        return ID_Carrello;
    }

    public void setID_Carrello(int ID_Carrello) {
        this.ID_Carrello = ID_Carrello;
    }

    public List<AdminCarrelloProdotto> getProdottiCarrello() {
        return prodottiCarrello;
    }

    public void setProdottiCarrello(List<AdminCarrelloProdotto> prodottiCarrello) {
        this.prodottiCarrello = prodottiCarrello;
    }

    public List<Ordine> getOrdini() {
        return ordini;
    }

    public void setOrdini(List<Ordine> ordini) {
        this.ordini = ordini;
    }
}
