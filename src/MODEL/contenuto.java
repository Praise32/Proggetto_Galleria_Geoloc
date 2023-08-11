package model;

public class Contenuto {
    private int idCollezione;
    private int idFoto;

    public Contenuto() {
    }

    public Contenuto(int idCollezione, int idFoto) {
        this.idCollezione = idCollezione;
        this.idFoto = idFoto;
    }

    public int getIdCollezione() {
        return idCollezione;
    }

    public void setIdCollezione(int idCollezione) {
        this.idCollezione = idCollezione;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }
}
