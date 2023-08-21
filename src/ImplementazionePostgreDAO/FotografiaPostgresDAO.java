package ImplementazionePostgresDAO;

import DAO.FotografiaDAO;
import DBconnection.ConnessioneDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timestamp;

/**
 * The type Fotografia postgres dao.
 */
public class FotografiaPostgresDAO implements FotografiaDAO{
    private Connection connection;


    /**
     * Instantiates a new Fotografia postgres dao and set a connection with Database.
     */
    public FotografiaPostgresDAO(){
        try {
            connection = ConnessioneDB.getInstance().getConnection();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean eliminaFotografiaDAO(int idFotoSelezionata) throws SQLException {

        PreparedStatement deleteFot;
        deleteFot = connection.prepareStatement("DELETE FROM Fotografia WHERE id_foto = ? ");
        deleteFot.setInt(1, idFotoSelezionata);
        int result = deleteFot.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;

    }

    @Override
    public boolean aggiungiFotografiaDAO(int idFoto, String usernameAutore, byte[] datiFoto, String dispositivo, Timestamp dataFoto, float latitudine, float longitudine, boolean condivisa, String titolo) throws SQLException{
        PreparedStatement insertFot;
        insertFot = connection.prepareStatement("INSERT INTO Collezione (idFoto, usernameAutore, datiFoto, dispositivo, dataFoto, latitudine, longitudine, condivisa, titolo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        insertFot.setInt(1, idFoto);
        insertFot.setString(2, usernameAutore);
        insertFot.setByte(3, dataFoto);
        insertFot.setString(4, dispositivo);
        insertFot.setTimestamo(5, dataFoto);
        insertFot.setFloat(6, latitudine);
        insertFot.setFloat(7, longitudine);
        insertFot.setBoolean(8, condivisa);
        insertFot.setString(9, titolo);
        int result = insertFot.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }
//Contenuto
    @override
    public boolean vediContenutoFotografiaDAO(int idFotoselezionata, ArrayList<Integer> idCollezioneAssociato) throws SQLException {
        try {
            PreparedStatement vediContenutoFot;
            vediContenutoFot = connection.prepareStatement("SELECT id_collezione FROM contenuto WHERE id_foto = ?");
            vediContenutoFot = setInt(1, idCollezioneAssociato)
            vediContenutoFot = setInt(2, idFotoselezionata);
            ResultSet rs =  vediContenutoFot.executeQuery();
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
    public boolean aggiungiContenutoFotografiaDAO(int idFotoSelezionata, int idCollezioneSelezionato) throws SQLException {

        try {
            PreparedStatement insertConFott;
            insertConFott = connection.prepareStatement("INSERT INTO CONTENUTO (id_foto, id_collezione) VALUES (?, ?)");
            insertConFott = setInt(1, idFotoSelezionata);
            insertConFott = setInt(2, idCollezioneSelezionato);
            ResultSet rs =  insertConFott().executeQuery();
            if(rs==1){
                return true;
            }
            return false;
        }
    }

    @override
    public boolean eliminaContenutoFotografiaDAO(int idFotoselezionata, int idCollezioneSelezionato) throws SQLException {
        try {
            PreparedStatement insertContFot;
            insertContFot = connection.prepareStatement("DELETE FROM CONTENUTO (id_foto, id_collezione) VALUES (?, ?)");
            insertContFot = setInt(1, idFotoselezionata);
            insertContFot = setInt(2, idCollezioneSelezionato);
            ResultSet rs =  insertContFot().executeQuery();
            if(rs==1){
                return true;
            }
            return false;
        }

    }
//TAG Utente
    @override
    public vediTagUtenteDAO(int idFotoSelezionata, ArrayList<String> utenteAssociato) throws SQLException{
            try {
                PreparedStatement vediTagUte;
                vediTagUte = connection.prepareStatement("SELECT username FROM tag_utente WHERE id_foto = ?");
                vediTagUte = setInt(1, idFotoSelezionata)
                vediTagUte = setString(2, utenteAssociato);
                ResultSet rs =  vediTagUte.executeQuery();
                while (rs.next() ){
                    utenteAssociato.add(rs.getString("username"));
                }
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }

    @override
    public boolean aggiungiTagUtenteDAO(int idFotoSelezionata, String utenteSelezionato) throws SQLException {
        try {
            PreparedStatement insertTagUtente;
            insertTagUtente = connection.prepareStatement("INSERT INTO tag_utente (id_foto, username) VALUES (?, ?)");
            insertTagUtente = setInt(1, idFotoSelezionata);
            insertTagUtente = setString(2, utenteSelezionato);
            ResultSet rs = insertTagUtente().executeQuery();
            if (rs == 1) {
                return true;
            }
            return false;
        }
    }

    @override
    public boolean eliminaTagUtenteDAO(int idFotoSelezionata, String utenteSelezionato) throws SQLException {
        try {
            PreparedStatement insertTagUtente;
            insertTagUtente = connection.prepareStatement("DELETE FROM tag_utente (id_foto, username) VALUES (?, ?)");
            insertTagUtente = setInt(1, idFotoselezionata);
            insertTagUtente = setString(2, utenteSelezionato);
            ResultSet rs = insertTagUtente().executeQuery();
            if (rs == 1) {
                return true;
            }
            return false;
        }

    }
//TAG Soggetto
    @override
    public vediTagSoggettoDAO(int idFotoSelezionata, ArrayList<String> soggettoAssociato) throws SQLException {
        try {
            PreparedStatement vediTagSog;
            vediTagSog = connection.prepareStatement("SELECT nome_soggetto FROM tag_soggetto WHERE id_foto = ?");
            vediTagSog = setInt(1, idFotoSelezionata)
            vediTagSog = setString(2, soggettoAssociato);
            ResultSet rs =  vediTagSog.executeQuery();
            while (rs.next() ){
                soggettoAssociato.add(rs.getString("nome_soggetto"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @override
    public boolean aggiungiTagSoggettoDAO(int idFotoSelezionata, String soggettoSelezionato) throws SQLException {
        try {
            PreparedStatement insertTagSoggetto;
            insertTagSoggetto = connection.prepareStatement("INSERT INTO tag_soggetto (id_foto, nome_soggetto) VALUES (?, ?)");
            insertTagSoggetto = setInt(1, idFotoSelezionata);
            insertTagSoggetto = setString(2, soggettoSelezionato);
            ResultSet rs = insertTagSoggetto().executeQuery();
            if (rs == 1) {
                return true;
            }
            return false;
        }
    }
    @override
    public boolean eliminaTagSoggettoDAO(int idFotoSelezionata, String soggettoSelezionato) throws SQLException{
        try {
            PreparedStatement insertTagSoggetto;
            insertTagSoggetto = connection.prepareStatement("DELETE FROM tag_soggetto (id_foto, nome_soggetto) VALUES (?, ?)");
            insertTagSoggetto = setInt(1, idFotoSelezionata);
            insertTagSoggetto = setString(2, soggettoSelezionato);
            ResultSet rs = insertTagSoggetto().executeQuery();
            if (rs == 1) {
                return true;
            }
            return false;
        }
    }







}
