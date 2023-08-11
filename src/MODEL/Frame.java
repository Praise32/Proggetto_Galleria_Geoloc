package MODEL;

// Definizione della classe Frame nel package model
public class Frame {
    // Attributi privati della classe Frame
    private Video idVideo; 
    private Fotografia idFoto;      
    private int durata;    
    private int ordine;    

    // Costruttore vuoto di default
    public Frame() {
    }

    // Costruttore con parametri per inizializzare gli attributi della classe Frame
    public Frame(Video idVideo, Fotografia idFoto, int durata, int ordine) {
        this.idVideo = idVideo;   
        this.idFoto = idFoto;       
        this.durata = durata;       
        this.ordine = ordine;    
    }

    // Metodi getter e setter per l'attributo idVideo
    public Video getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(Video idVideo) {
        this.idVideo = idVideo;
    }

    // Metodi getter e setter per l'attributo idFoto
    public Fotografia getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Fotografia idFoto) {
        this.idFoto = idFoto;
    }

    // Metodi getter e setter per l'attributo durata
    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    // Metodi getter e setter per l'attributo ordine
    public int getOrdine() {
        return ordine;
    }

    public void setOrdine(int ordine) {
        this.ordine = ordine;
    }
}
