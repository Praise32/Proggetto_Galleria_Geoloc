package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

// Interfaccia UtenteDAO per le operazioni di accesso ai dati dell'entità Utente
public interface UtenteDAO {

    // Metodo per aggiungere un nuovo utente al database
    // Parametri:
    // - username: il nome utente da aggiungere
    // - password: la password associata all'utente
    // - admin: indica se l'utente è un amministratore o meno
    // Restituisce: true se l'utente è stato aggiunto con successo, false altrimenti
    boolean aggiungiUtenteDAO(String username, String password, boolean admin) throws SQLException;

    // Metodo per eliminare un utente dal database
    // Parametro:
    // - utenteSelezionato: l'username dell'utente da eliminare
    // Restituisce: true se l'utente è stato eliminato con successo, false altrimenti
    boolean eliminaUtenteDAO(String utenteSelezionato) throws SQLException;

    // ... altri metodi per operazioni CRUD specifiche ...

}

