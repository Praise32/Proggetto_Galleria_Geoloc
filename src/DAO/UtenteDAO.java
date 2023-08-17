package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

// Questa è l'interfaccia per il DAO (Data Access Object) relativo agli utenti.
public interface UtenteDAO {

    // Metodo per aggiungere un nuovo utente al sistema.
    public boolean aggiungiUtenteDAO(String username, String password, boolean admin) throws SQLException;

    // Metodo per eliminare un utente dal sistema.
    public boolean eliminaUtenteDAO(String utenteSelezionato) throws SQLException;

    // Metodo per ottenere le collezioni associate a un utente.
    public boolean VediCollezioniPerUtenteDAO(String usernameSelezionato, ArrayList<int> idCollezioneAssociato) throws SQLException;

    // Metodo per ottenere le fotografie associate a un utente.
    public boolean VediFotografiaPerUtenteDAO(String usernameSelezionato, ArrayList<int> idFotoAssociato) throws SQLException;

    // Metodo per ottenere i video associati a un utente.
    public boolean VediVideoPerUtenteDAO(String usernameSelezionato, ArrayList<int> idVideoAssociato) throws SQLException;

    // Metodo per modificare le informazioni di un utente esistente.
    public boolean modificaUtenteDAO(String usernameSelezionato, String password, boolean admin) throws SQLException;
}
