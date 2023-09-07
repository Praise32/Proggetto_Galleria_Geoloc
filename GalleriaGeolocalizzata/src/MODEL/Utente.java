package MODEL;

import java.util.ArrayList;
import java.util.List;

public class Utente {
    private String username;
    private String password;
    private boolean admin;

    //il seguente attributo considera una lista di collezioni possedute dall'utente
    private List<Collezione> listaCollezione = new ArrayList<>();

    //il seguente attributo considera una lista di fotografie scattate dall'utente
    private List<Fotografia> listaFotografia = new ArrayList<>();

    //il seguente attributo considera una lista di fotografie in cui l'utente è taggato
    private List<Fotografia> listaTagUtente = new ArrayList<>();

    //il seguente attributo considera una lista di video registrati dall'utente
    private List<Video> listaVideo = new ArrayList<>();
    public Utente() {
    }

    public Utente(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        

    }
    // Metodi per aggiungere proprietari alle collezioni, fotografie e video
    public void aggiungiCollezione(Collezione collezione) {
        this.listaCollezione.add(collezione);
    }

    public void aggiungiFotografia(Fotografia fotografia) {
        this.listaFotografia.add(fotografia);
    }

    public void aggiungiVideo(Video video) {
        this.listaVideo.add(video);
    }

    public void aggiungiTagUtente(Fotografia fotografia) {
        this.listaTagUtente.add(fotografia);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    // Restituisci la lista di collezioni, fotografie e video associate all'utente
    public List<Collezione> getListaCollezione() {
        return listaCollezione;
    }

    public List<Fotografia> getListaFotografia() {
        return listaFotografia;
    }

    public List<Fotografia> getListaTagUtente() {
        return listaTagUtente;
    }

    public List<Video> getListaVideo() {
        return listaVideo;
    }

    public void setListaTagUtente(List<Fotografia> listaTagUtente) {
        this.listaTagUtente = listaTagUtente;
    }

    public void setListaCollezione(List<Collezione> listaCollezione) {
        this.listaCollezione = listaCollezione;
    }

    public void setListaFotografia(List<Fotografia> listaFotografia) {
        this.listaFotografia = listaFotografia;
    }

    public void setListaVideo(List<Video> listaVideo) {
        this.listaVideo = listaVideo;
    }
}
