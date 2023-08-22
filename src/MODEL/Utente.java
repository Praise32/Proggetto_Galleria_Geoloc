package MODEL;

import java.util.ArrayList;
import java.util.List;

public class Utente {
    private String username;
    private String password;
    private boolean admin;

    //il seguente attributo considera una lista di collezioni possedute dall'utente
    private List<Collezione> listaCollezioni = new ArrayList<>();

    //il seguente attributo considera una lista di fotografie scattate dall'utente
    private List<Fotografia> listaFotografia = new ArrayList<>();

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
    public void aggiungiProprietarioCollezione(Collezione collezione) {
        this.listaCollezioni.add(collezione);
    }

    public void aggiungiAutoreFotografia(Fotografia fotografia) {
        this.listaFotografie.add(fotografia);
    }

    public void aggiungiAutoreVideo(Video video) {
        this.listaVideo.add(video);
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
    public List<Collezione> getListaCollezioni() {
        return listaCollezioni;
    }

    public List<Fotografia> getListaFotografie() {
        return listaFotografie;
    }

    public List<Video> getListaVideo() {
        return listaVideo;
    }
}
