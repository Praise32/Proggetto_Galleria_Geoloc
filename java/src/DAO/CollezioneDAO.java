package DAO;  // Questo codice è all'interno del package "DAO".

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

public interface CollezioneDAO {

    // Questa interfaccia definisce metodi per l'accesso ai dati delle collezioni.

    // Metodo per aggiungere una nuova collezione al database.
    public boolean aggiungiCollezioneDAO(int idCollezione, String username, String titolo, Timestamp dataCollezione) throws SQLException;

    // Metodo per eliminare una collezione dal database.
    public boolean eliminaCollezioneDAO(int collezioneSelezionato) throws SQLException;

    // Metodo per aggiungere contenuti (foto) a una collezione esistente.
    public boolean aggiungiContenutoDAO(int idCollezioneSelezionato, int idFotoSelezionata) throws SQLException;

    // Metodo per eliminare contenuti (foto) da una collezione esistente.
    public boolean eliminaContenutoDAO(int idCollezioneSelezionato, int idFotoSelezionata) throws SQLException;

    // Metodo per visualizzare i contenuti (foto associate) di una collezione.
    public boolean vediContenutoDAO(int idCollezioneSelezionato, ArrayList<Integer> idFotoAssociate) throws SQLException;

    // Metodo per modificare i dettagli di una collezione esistente nel database.
    public boolean modificaCollezioneDAO(int idCollezioneSelezionato, String titolo) throws SQLException;
}
