package model;

// Definizione della classe TagSoggetto nel package model
public class TagSoggetto {
    // Attributi privati della classe TagSoggetto
    private Soggetto nomeSoggetto;  // Nome del soggetto associato al tag
    private Fotografia idFoto;           // ID della fotografia associata al tag

    // Costruttore vuoto di default
    public TagSoggetto() {
    }

    // Costruttore con parametri per inizializzare gli attributi della classe TagSoggetto
    public TagSoggetto(Soggetto nomeSoggetto, Fotografia idFoto) {
        this.nomeSoggetto = nomeSoggetto; 
        this.idFoto = idFoto;            
    }

    // Metodi getter e setter per l'attributo nomeSoggetto
    public Soggetto getNomeSoggetto() {
        return nomeSoggetto;
    }

    public void setNomeSoggetto(Soggetto nomeSoggetto) {
        this.nomeSoggetto = nomeSoggetto;
    }

    // Metodi getter e setter per l'attributo idFoto
    public Fotografia getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Fotografia idFoto) {
        this.idFoto = idFoto;
    }
}
