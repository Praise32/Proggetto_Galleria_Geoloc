package MODEL;

public class Contenuto {
    private Collezione idCollezione;
    private Fotografia idFoto;

    public Contenuto() {
    }

    public Contenuto(Collezione idCollezione, Fotografia idFoto) {
        this.idCollezione = idCollezione;
        this.idFoto = idFoto;
    }

    public Collezione getIdCollezione() {
        return idCollezione;
    }

    public void setIdCollezione(Collezione idCollezione) {
        this.idCollezione = idCollezione;
    }

    public Fotografia getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Fotografia idFoto) {
        this.idFoto = idFoto;
    }
}
