package ImplementazionePostgresDAO;

//LogForJ libreria per il logging
import DAO.UtenteDAO;
import DBconnection.ConnessioneDB;
import MAIN.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

/**
 * The type Utente postgres dao.
 */
public class UtentePostgresDAO implements UtenteDAO {
    private Connection connection;


    /**
     * Instantiates a new Utente postgres dao and set a connection with Database.
     */
    public UtentePostgresDAO() {
        try {
            connection = ConnessioneDB.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean eliminaUtenteDAO(String utenteSelezionato) throws SQLException {

        PreparedStatement deleteUsr;
        deleteUsr = connection.prepareStatement("DELETE FROM utente WHERE username = ? ");
        deleteUsr.setString(1, utenteSelezionato);
        int result = deleteUsr.executeUpdate();
        return result == 1;
    }

    @Override
    public boolean aggiungiUtenteDAO(String username, String password, boolean admin) throws SQLException {
        PreparedStatement insertUsr;
        insertUsr = connection.prepareStatement("INSERT INTO UTENTE (username, password, admin) VALUES (?, ?, ?)");
        insertUsr.setString(1, username);
        insertUsr.setString(2, password);
        insertUsr.setBoolean(3, admin);
        int result = insertUsr.executeUpdate();
        return result == 1;
    }

    @Override
    public boolean VediCollezioniPerUtenteDAO(String usernameSelezionato, ArrayList<Integer> idCollezioneAssociato) throws SQLException {
        try {
            PreparedStatement vediCollezioni;
            vediCollezioni = connection.prepareStatement("SELECT id_collezione FROM collezione WHERE username = ?");
            vediCollezioni.setString(1, usernameSelezionato);
            ResultSet rs = vediCollezioni.executeQuery();
            while (rs.next()) {
                idCollezioneAssociato.add(rs.getInt("id_collezione"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean VediFotografiaPerUtenteDAO(String usernameSelezionato, ArrayList<Integer> idFotoAssociato) throws SQLException {
        try {
            PreparedStatement vediFotografia;
            vediFotografia = connection.prepareStatement("SELECT id_foto, dati_foto, username_autore, data_foto FROM fotografia WHERE username_autore = ?");
            vediFotografia.setString(1, usernameSelezionato);
            ResultSet rs = vediFotografia.executeQuery();
            while (rs.next()) {
                idFotoAssociato.add(rs.getInt("id_foto"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean VediFotografiaPerTagUtenteDAO(String usernameSelezionato, ArrayList<Integer> idFotoAssociato) throws SQLException {
        try {
            PreparedStatement vediFotografia;
            vediFotografia = connection.prepareStatement("SELECT id_foto FROM tag_utente WHERE username = ?");
            vediFotografia.setString(1, usernameSelezionato);
            ResultSet rs = vediFotografia.executeQuery();
            while (rs.next()) {
                idFotoAssociato.add(rs.getInt("id_foto"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean VediVideoPerUtenteDAO(String usernameSelezionato, ArrayList<Integer> idVideoAssociato) throws SQLException {
        try {
            PreparedStatement vediVideo;
            vediVideo = connection.prepareStatement("SELECT id_video FROM video WHERE autore = ?");
            vediVideo.setString(1, usernameSelezionato);
            ResultSet rs = vediVideo.executeQuery();
            while (rs.next()) {
                idVideoAssociato.add(rs.getInt("id_video"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modificaUtenteDAO(String usernameSelezionato, String password, boolean admin) throws SQLException {
        PreparedStatement modificaUsr;
        modificaUsr = connection.prepareStatement("UPDATE utente SET password = ?, admin = ? WHERE username = ?");
        modificaUsr.setString(1, password);
        modificaUsr.setBoolean(2, admin);
        modificaUsr.setString(3, usernameSelezionato);
        int rs = modificaUsr.executeUpdate();
        return rs > 0;
    }

    @Override
    public boolean accessoUtenteDAO(String usernameAccesso, String passwordAccesso) throws SQLException {
        PreparedStatement accessoUtente;
        accessoUtente = connection.prepareStatement("SELECT username FROM utente WHERE username = ? AND password = ?");
        accessoUtente.setString(1, usernameAccesso);
        accessoUtente.setString(2, passwordAccesso);


        ResultSet rs = accessoUtente.executeQuery();
        return rs.next(); //Ritorna vero se ha letto qualcosa, dunque le credenziali sono corrette, altrimenti falso
    }

    @Override
    public boolean controlloAdminDAO(String username) throws SQLException {
        PreparedStatement controlloAdmin;
        controlloAdmin = connection.prepareStatement("SELECT admin FROM utente WHERE username = ?");
        controlloAdmin.setString(1, username);

        ResultSet rs = controlloAdmin.executeQuery();
        return rs.getBoolean("admin");
    }
}





