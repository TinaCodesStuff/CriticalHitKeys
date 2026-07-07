package model;

import java.time.LocalDateTime;

public class Ordine {
    private int ID_Ordine;
    private int ID_Carrello;
    private float importoTot;
    private LocalDateTime data_Ordine;
    private String descrizione_Acquisto;

    public int getID_Ordine() {
        return ID_Ordine;
    }

    public void setID_Ordine(int ID_Ordine) {
        this.ID_Ordine = ID_Ordine;
    }

    public int getID_Carrello() {
        return ID_Carrello;
    }

    public void setID_Carrello(int ID_Carrello) {
        this.ID_Carrello = ID_Carrello;
    }

    public float getImportoTot() {
        return importoTot;
    }

    public void setImportoTot(float importoTot) {
        this.importoTot = importoTot;
    }

    public LocalDateTime getData_Ordine() {
        return data_Ordine;
    }

    public void setData_Ordine(LocalDateTime data_Ordine) {
        this.data_Ordine = data_Ordine;
    }

    public String getDescrizione_Acquisto() {
        return descrizione_Acquisto;
    }

    public void setDescrizione_Acquisto(String descrizione_Acquisto) {
        this.descrizione_Acquisto = descrizione_Acquisto;
    }
}
