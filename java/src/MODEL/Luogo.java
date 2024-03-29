package MODEL;

import java.util.ArrayList;
import java.util.List;

public class  Luogo {
    private float latitudine;
    private float longitudine;
    private String nome;
    private String descrizione;
    private int numeroFotografie;


    private List<Fotografia> listaFotografia = new ArrayList<>();


    public Luogo(float latitudine, float longitudine, String nome, String descrizione) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public void aggiungiFotografia(Fotografia fotografia) {this.listaFotografia.add(fotografia);}

    public List<Fotografia> getListaFotografia() {
        return listaFotografia;
    }

    public void setListaFotografia(List<Fotografia> listaFotografia) {
        this.listaFotografia = listaFotografia;
    }

    public void setNumeroFotografie(int numeroFotografie) {
        this.numeroFotografie = numeroFotografie;
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
