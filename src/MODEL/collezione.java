package model;

import java.sql.Timestamp;

public class Collezione {
    private int idCollezione;
    private Utente username;
    private String titolo;
    private Timestamp dataCollezione;
    private int numeroElementi;

    public Collezione() {
    }

    public Collezione(int idCollezione, Utente username, String titolo, Timestamp dataCollezione, int numeroElementi) {
        this.idCollezione = idCollezione;
        this.username = username;
        this.titolo = titolo;
        this.dataCollezione = dataCollezione;
        this.numeroElementi = numeroElementi;
    }

    public int getIdCollezione() {
        return idCollezione;
    }

    public void setIdCollezione(int idCollezione) {
        this.idCollezione = idCollezione;
    }

    public Utente getUsername() {
        return username;
    }

    public void setUsername(Utente username) {
        this.username = username;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Timestamp getDataCollezione() {
        return dataCollezione;
    }

    public void setDataCollezione(Timestamp dataCollezione) {
        this.dataCollezione = dataCollezione;
    }

    public int getNumeroElementi() {
        return numeroElementi;
    }

    public void setNumeroElementi(int numeroElementi) {
        this.numeroElementi = numeroElementi;
    }
}
