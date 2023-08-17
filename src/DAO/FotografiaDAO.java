package DAO;  // Questo codice è all'interno del package "DAO".

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

public interface FotografiaDAO {

    // Questa interfaccia definisce metodi per l'accesso ai dati delle fotografie.

    // Metodo per aggiungere una nuova fotografia al database.
    public boolean aggiungiFotografiaDAO(int idFoto, String usernameAutore, byte[] datiFoto, String dispositivo, Timestamp dataFoto, float latitudine, float longitudine, boolean condivisa, String titolo) throws SQLException;

    // Metodo per eliminare una fotografia dal database.
    public boolean eliminaFotografiaDAO(int idFotoSelezionata) throws SQLException;

    // Metodo per aggiungere una fotografia a una collezione esistente.
    public boolean aggiungiContenutoFotografiaDAO(int idFotoSelezionata, int idCollezioneSelezionato) throws SQLException;

    // Metodo per visualizzare il contenuto (collezioni associate) di una fotografia.
    public boolean vediContenutoFotografiaDAO(int idFotoselezionata, ArrayList<Integer> idCollezioneAssociato) throws SQLException;

    // Metodo per eliminare una fotografia da una collezione esistente.
    public boolean eliminaContenutoFotografiaDAO(int idFotoselezionata, int idCollezioneSelezionato) throws SQLException;

    // Metodo per aggiungere un TagUtente in una foto esistente.
    public boolean aggiungiTagUtenteDAO(int idFotoSelezionata, String utenteSelezionato);

    //Metodo per visualizzare l'utente taggato in una foto.
    public vediTagUtenteDAO(int idFotoSelezionata, ArrayList<String> utenteAssociato);

    // Metodo per eliminare un TagUtente in una foto esistente
    public boolean eliminaTagUtenteDAO(int idFotoSelezionata, String utenteSelezionato);

    // Metodo per aggiungere un TagSoggetto in una foto esistente.
    public boolean aggiungiTagSoggettoDAO(int idFotoSelezionata, String soggettoSelezionato);

    //Metodo per visualizzare il soggetto taggato in una foto.
    public vediTagSoggettoDAO(int idFotoSelezionata, ArrayList<String> soggettoAssociato);

    // Metodo per eliminare un TagSoggetto in una foto esistente
    public boolean eliminaTagSoggettoDAO(int idFotoSelezionata, String soggettoSelezionato);

}
