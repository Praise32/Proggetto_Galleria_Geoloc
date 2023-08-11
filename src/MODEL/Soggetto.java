package MODEL;

// Definizione della classe Soggetto nel package model
public class Soggetto {
    // Attributi privati della classe Soggetto
    private String nome;        
    private String categoria; 

    // Costruttore vuoto di default
    public Soggetto() {
    }

    // Costruttore con parametri per inizializzare gli attributi della classe Soggetto
    public Soggetto(String nome, String categoria) {
        this.nome = nome;           
        this.categoria = categoria; 
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
