package model;

public class Media {
    private int idMedia;
    private int idProdotto;
    private String tipo;
    private String urlMedia;

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
}
