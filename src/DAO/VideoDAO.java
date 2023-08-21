package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

// Interfaccia per definire operazioni su soggetti associati a foto
public interface SoggettoDAO {

    // Aggiunge un nuovo soggetto con il nome e la categoria specificati
    boolean aggiungiSoggettoDAO(String nome, String categoria) throws SQLException;

    // Elimina un soggetto in base al nome specificato
    boolean eliminaSoggettoDAO(String nomeSelezionato) throws SQLException;

    // Modifica la categoria di un soggetto in base al nome specificato
    boolean modificaSoggettoDAO(String nomeSelezionato, String categoria) throws SQLException;

    // Ottiene l'elenco delle foto associate a un soggetto specifico
    boolean vediFotoAssociate(String nomeSelezionato, ArrayList<Integer> fotografiaAssociato) throws SQLException;
}