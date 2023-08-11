package MODEL;

import java.sql.Timestamp;

// Definizione della classe Collezione nel package model
public class Collezione {
    // Attributi privati della classe Collezione
    private int idCollezione;         
    private Utente username;           
    private String titolo;            
    private Timestamp dataCollezione; 
    private int numeroElementi;      

    // Costruttore vuoto di default
    public Collezione() {
    }

    // Costruttore con parametri per inizializzare gli attributi della classe Collezione
    public Collezione(int idCollezione, Utente username, String titolo, Timestamp dataCollezione, int numeroElementi) {
        this.idCollezione = idCollezione;          
        this.username = username;                   
        this.titolo = titolo;                      
        this.dataCollezione = dataCollezione;      
        this.numeroElementi = numeroElementi;     
    }

    // Metodi getter e setter per l'attributo idCollezione
    public int getIdCollezione() {
        return idCollezione;
    }

    public void setIdCollezione(int idCollezione) {
        this.idCollezione = idCollezione;
    }

    // Metodi getter e setter per l'attributo username di tipo Utente
    public Utente getUsername() {
        return username;
    }

    public void setUsername(Utente username) {
        this.username = username;
    }

    // Metodi getter e setter per l'attributo titolo
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    // Metodi getter e setter per l'attributo dataCollezione di tipo Timestamp
    public Timestamp getDataCollezione() {
        return dataCollezione;
    }

    public void setDataCollezione(Timestamp dataCollezione) {
        this.dataCollezione = dataCollezione;
    }

    // Metodi getter e setter per l'attributo numeroElementi
    public int getNumeroElementi() {
        return numeroElementi;
    }

    public void setNumeroElementi(int numeroElementi) {
        this.numeroElementi = numeroElementi;
    }
}
