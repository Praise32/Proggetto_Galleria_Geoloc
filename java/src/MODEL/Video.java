package MODEL;

import java.util.ArrayList;
import java.util.List;
// Definizione della classe Video nel package model
public class Video {
    // Attributi privati della classe Video
    private int idVideo;
    private Utente autore;
    private String titolo;
    private int numeroFrames;
    private int durata;
    private String descrizione;

    private List<Frame> listaFrameVideoAssociati = new ArrayList<>();

    // Costruttore con parametri per inizializzare gli attributi della classe Video
    public Video(int idVideo, Utente autore, String titolo, int numeroFrames, int durata, String descrizione) {
        this.idVideo = idVideo;        
        this.autore = autore;          
        this.titolo = titolo;           
        this.numeroFrames = numeroFrames;
        this.durata = durata;           
        this.descrizione = descrizione; 
    }
    
    //Metodo per aggiungere idvideo a composizione video
    public void aggiungiFrame(Frame frame) {this.listaFrameVideoAssociati.add(frame); }
    // Metodi getter e setter per l'attributo idVideo
    public int getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(int idVideo) {
        this.idVideo = idVideo;
    }

    // Metodi getter e setter per l'attributo autore di tipo Utente
    public Utente getAutore() {
        return autore;
    }
    public void setAutore(Utente autore) {
        this.autore = autore;
    }

    // Metodi getter e setter per l'attributo titolo
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    // Metodi getter e setter per l'attributo numeroFrames
    public int getNumeroFrames() {
        return numeroFrames;
    }

    public void setNumeroFrames(int numeroFrames) {
        this.numeroFrames = numeroFrames;
    }

    // Metodi getter e setter per l'attributo durata
    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    // Metodi getter e setter per l'attributo descrizione
    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Frame> getListaFrameVideoAssociati() {
        return listaFrameVideoAssociati;
    }

    public void setListaFrameVideoAssociati(List<Frame> listaFrameVideoAssociati) {
        this.listaFrameVideoAssociati = listaFrameVideoAssociati;
    }
}
