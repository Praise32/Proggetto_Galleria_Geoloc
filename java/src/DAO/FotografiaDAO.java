package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

public interface FotografiaDAO {

    // Questa interfaccia definisce metodi per l'accesso ai dati delle fotografie.

    // Metodo per aggiungere una nuova fotografia al database.
    public boolean aggiungiFotografiaDAO(int idFoto, String usernameAutore, byte[] datiFoto, String dispositivo, Timestamp dataFoto, float luogolat, float luogolon, boolean condivisa, String titolo) throws SQLException;

    // Metodo per eliminare una fotografia dal database.
    public boolean eliminaFotografiaDAO(int idFotoSelezionata) throws SQLException;

    public boolean modificaFotografiaDAO(int idFotoSelezionata,String DispositivoNuovo ,String TitoloNuovo) throws SQLException;

    // Metodo per aggiungere una fotografia a una collezione esistente.
    public boolean aggiungiContenutoFotografiaDAO(int idFotoSelezionata, int idCollezioneSelezionato) throws SQLException;

    // Metodo per visualizzare il contenuto (collezioni associate) di una fotografia.
    public boolean vediContenutoFotografiaDAO(int idFotoSelezionata, ArrayList<Integer> idCollezioneAssociato) throws SQLException;

    // Metodo per eliminare una fotografia da una collezione esistente.
    public boolean eliminaContenutoFotografiaDAO(int idFotoSelezionata, int idCollezioneSelezionato) throws SQLException;

    // Metodo per aggiungere un TagUtente in una foto esistente.
    public boolean aggiungiTagUtenteDAO(int idFotoSelezionata, String utenteSelezionato) throws SQLException;

    //Metodo per visualizzare l'utente taggato in una foto.
    public boolean vediTagUtenteDAO(int idFotoSelezionata, ArrayList<String> utenteAssociato) throws SQLException;

    // Metodo per eliminare un TagUtente in una foto esistente
    public boolean eliminaTagUtenteDAO(int idFotoSelezionata, String utenteSelezionato) throws SQLException;

    // Metodo per aggiungere un TagSoggetto in una foto esistente.
    public boolean aggiungiTagSoggettoDAO(int idFotoSelezionata, String soggettoSelezionato) throws SQLException;

    //Metodo per visualizzare il soggetto taggato in una foto.
    public boolean vediTagSoggettoDAO(int idFotoSelezionata, ArrayList<String> soggettoAssociato) throws SQLException;

    // Metodo per eliminare un TagSoggetto in una foto esistente
    public boolean eliminaTagSoggettoDAO(int idFotoSelezionata, String soggettoSelezionato) throws SQLException;

}
