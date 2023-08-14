package ImplementazionePostgresDAO;

import DAO.UtenteDAO;
import DBconnection.ConnessioneDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementazione dell'interfaccia UtenteDAO per Postgres.
 */
public class UtentePostgresDAO implements UtenteDAO {
    private Connection connection;

    /**
     * Costruttore: inizializza la connessione al database.
     */
    public UtentePostgresDAO() {
        try {
            connection = ConnessioneDB.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un utente dal database.
     *
     * @param utenteSelezionato L'username dell'utente da eliminare.
     * @return true se l'utente è stato eliminato con successo, altrimenti false.
     * @throws SQLException in caso di errore nell'operazione SQL.
     */
    @Override
    public boolean eliminaUtenteoDAO(String utenteSelezionato) throws SQLException {
        PreparedStatement deleteUsr;
        deleteUsr = connection.prepareStatement("DELETE FROM UTENTE WHERE USERNAME = ?");
        deleteUsr.setString(1, utenteSelezionato);
        int result = deleteUsr.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }

    /**
     * Aggiunge un nuovo utente al database.
     *
     * @param username L'username dell'utente da aggiungere.
     * @param password La password dell'utente da aggiungere.
     * @param admin    True se l'utente è un amministratore, altrimenti false.
     * @return true se l'utente è stato aggiunto con successo, altrimenti false.
     * @throws SQLException in caso di errore nell'operazione SQL.
     */
    @Override
    public boolean aggiungiUtenteDAO(String username, String password, boolean admin) throws SQLException {
        PreparedStatement insertUsr;
        insertUsr = connection.prepareStatement("INSERT INTO UTENTE (username, password, admin) VALUES (?, ?, ?)");
        insertUsr.setString(1, username);
        insertUsr.setString(2, password);
        insertUsr.setBoolean(3, admin);
        int result = insertUsr.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }

    // ... altri metodi implementati ...

}

