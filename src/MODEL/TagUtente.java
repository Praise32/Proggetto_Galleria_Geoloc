package MODEL;

// Definizione della classe TagUtente nel package model
public class TagUtente {
    // Attributi privati della classe TagUtente
    private Utente username;     // Rappresenta l'utente associato al tag
    private Fotografia idFoto;   // Rappresenta la fotografia associata al tag

    // Costruttore vuoto di default
    public TagUtente() {
    }

    // Costruttore con parametri per inizializzare gli attributi della classe TagUtente
    public TagUtente(Utente username, Fotografia idFoto) {
        this.username = username;  
        this.idFoto = idFoto;       
    }

    // Metodi getter e setter per l'attributo username di tipo Utente
    public Utente getUsername() {
        return username;
    }

    public void setUsername(Utente username) {
        this.username = username;
    }

    // Metodi getter e setter per l'attributo idFoto di tipo Fotografia
    public Fotografia getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Fotografia idFoto) {
        this.idFoto = idFoto;
    }
}
