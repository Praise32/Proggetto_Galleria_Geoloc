package MODEL;

public class Luogo {
    private float latitudine;
    private float longitudine;
    private String nome;
    private String descrizione;

    public Luogo() {
    }

    public Luogo(float latitudine, float longitudine, String nome, String descrizione) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public float getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(float latitudine) {
        this.latitudine = latitudine;
    }

    public float getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(float longitudine) {
        this.longitudine = longitudine;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
