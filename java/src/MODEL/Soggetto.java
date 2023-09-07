package MODEL;

import java.util.ArrayList;
import java.util.List;

// Definizione della classe Soggetto nel package model
public class Soggetto {
    // Attributi privati della classe Soggetto
    private String nome;        
    private String categoria; 

   public List<Fotografia> listaFotoSoggetto = new ArrayList<>();

    // Costruttore con parametri per inizializzare gli attributi della classe Soggetto
    public Soggetto(String nome, String categoria) {
        this.nome = nome;           
        this.categoria = categoria; 
    }

    public void aggiungiFotoSoggetto (Fotografia fotografia) {this.listaFotoSoggetto.add(fotografia); }

    public List<Fotografia> getListaFotoSoggetto() {
        return listaFotoSoggetto;
    }

    public void setListaFotoSoggetto(List<Fotografia> listaFotoSoggetto) {
        this.listaFotoSoggetto = listaFotoSoggetto;
    }

    // Metodi getter e setter per l'attributo nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Metodi getter e setter per l'attributo categoria
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
