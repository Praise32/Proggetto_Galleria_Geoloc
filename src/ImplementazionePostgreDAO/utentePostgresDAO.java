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
 * The type Utente postgres dao.
 */
public class UtentePostgresDAO implements UtenteDAO{
    private Connection connection;
}

/**
 * Instantiates a new Utente postgres dao and set a connection with Database.
 */
public UtentePostgresDAO(){
    try {
        connection = ConnessioneDB.getInstance().getConnection();
    }
    catch(SQLException e) {
        e.printStackTrace();
    }
}



@Override
public boolean eliminaUtenteoDAO(String utenteSelezionato) throws SQLException{

    PreparedStatement deleteUsr;
    deleteUsr = connection.prepareStatement("DELETE FROM UTENTE WHERE USERNAME = ? ");
    deleteUsr.setString(1, utenteSelezionato);
    int result = deleteUsr.executeUpdate();
    if (result == 1) {
        return true;
    }
    return false;
}

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

@override
public boolean VediCollezioniPerUtenteDAO(String usernameSelezionato, ArrayString<int> idCollezioneAssociato) throws SQLException {
    try {
        PreparedStatement vediCollezioni;
        vediCollezioni = connection.prepareStatement("SELECT id_collezione FROM collezione WHERE username = ?");
        vediCollezioni = setString(1, usernameSelezionato);
        ResultSet rs =  vediCollezioni.executeQuery();
        while (rs.next() ){
            idCollezioneAssociato.add(rs.getString("id_collezione"));
        }
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
@override
public boolean VediFotografiaPerUtenteDAO(String usernameSelezionato, ArrayString<int> idFotoAssociato) throws SQLException {
    try {
        PreparedStatement vediFotografia;
        vediFotografia = connection.prepareStatement("SELECT id_foto FROM fotografia WHERE usernameAutore = ?");
        vediFotografia = setString(1, usernameSelezionato);
        ResultSet rs =  vediFotografia.executeQuery();
        while (rs.next() ) {
            idFotoAssociato.add(rs.getInt("id_foto"));
        }
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

@override
public boolean VediVideoPerUtenteDAO(String usernameSelezionato, ArrayString<int> idVideoAssociato) throws SQLException {
    try {
        PreparedStatement vediVideo;
        vediVideo = connection.prepareStatement("SELECT id_video FROM video WHERE autore = ?");
        vediVideo = setString(1, usernameSelezionato);
        ResultSet rs =  vediVideo.executeQuery();
        while (rs.next() ) {
            idVideoAssociato.add(rs.getInt("id_video"));
        }
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

@override
public boolean modificaUtenteDAO(String usernameSelezionato, String password, boolean admin) throws SQLException {
    PreparedStatement modificaUsr;
    modificaUsr = connection.prepareStatemen("UPDATE utente SET password = ?, boolean = ? WHERE username = ?");
    modificaUsr = setString(1, password);
    modificaUsr = setBoolean(2, admin);
    modificaUsr = setString(3, usernameSelezionato);
    int rs = modificaUsr.executeQuery();
    if (rs>0) {
        return true;
    }
    return false;
}



