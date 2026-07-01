package model;

public class Media {
    private int idMedia;
    private int idProdotto;
    private String tipo;
    private String urlMedia;
    private int ordineVisualizzazione;

    public int getIdMedia() {
        return idMedia;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdMedia(int idMedia) {
        this.idMedia = idMedia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrlMedia() {
        return urlMedia;
    }

    public void setUrlMedia(String urlMedia) {
        this.urlMedia = urlMedia;
    }

    public void setIdProdotto(int idProdotto) { this.idProdotto = idProdotto; }
    public int getOrdineVisualizzazione() { return ordineVisualizzazione; }
    public void setOrdineVisualizzazione(int ordineVisualizzazione) { this.ordineVisualizzazione = ordineVisualizzazione; }
}
