package model;

public class TagUtente {
    private Utente username;
    private Fotografia idFoto;

    public TagUtente() {
    }

    public TagUtente(Utente username, Fotografia idFoto) {
        this.username = username;
        this.idFoto = idFoto;
    }

    public Utente getUsername() {
        return username;
    }

    public void setUsername(Utente username) {
        this.username = username;
    }

    public Fotografia getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Fotografia idFoto) {
        this.idFoto = idFoto;
    }
}
