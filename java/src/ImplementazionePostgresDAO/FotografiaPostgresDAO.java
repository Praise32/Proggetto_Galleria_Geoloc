package ImplementazionePostgresDAO;

import DAO.FotografiaDAO;
import DBconnection.ConnessioneDB;
import MAIN.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

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
    public boolean aggiungiFotografiaDAO(int idFoto, String usernameAutore, byte[] datiFoto, String dispositivo, Timestamp dataFoto, float luogolat, float luogolon, boolean condivisa, String titolo) throws SQLException{
        PreparedStatement insertFot;
        insertFot = connection.prepareStatement("INSERT INTO Fotografia (id_foto, username_autore, dati_foto, dispositivo, data_foto, latitudine, longitudine, condivisa, titolo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        insertFot.setInt(1, idFoto);
        insertFot.setString(2, usernameAutore);
        insertFot.setBytes(3, datiFoto);
        insertFot.setString(4, dispositivo);
        insertFot.setTimestamp(5, dataFoto);
        insertFot.setFloat(6, luogolat);
        insertFot.setFloat(7, luogolon);
        insertFot.setBoolean(8, condivisa);
        insertFot.setString(9, titolo);
        int result = insertFot.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }
    public boolean modificaFotografiaDAO(int idFotoSelezionata,String DispositivoNuovo ,String TitoloNuovo) throws SQLException{
        PreparedStatement modificaFot;
        modificaFot = connection.prepareStatement("UPDATE fotografia SET dispositivo = ?, titolo = ? WHERE id_foto = ?");
        modificaFot.setString(1, DispositivoNuovo);
        modificaFot.setString(2, TitoloNuovo);
        modificaFot.setInt(3, idFotoSelezionata);
        int rs = modificaFot.executeUpdate();
        return rs > 0;


    }


    //Contenuto
    @Override
    public boolean vediContenutoFotografiaDAO(int idFotoSelezionata, ArrayList<Integer> idCollezioneAssociato) throws SQLException {
        try {
            PreparedStatement vediContenutoFot;
            vediContenutoFot = connection.prepareStatement("SELECT id_collezione FROM contenuto WHERE id_foto = ?");
            vediContenutoFot.setInt(1, idFotoSelezionata);
            ResultSet rs =  vediContenutoFot.executeQuery();
            while (rs.next() ){
                idCollezioneAssociato.add(rs.getInt("id_collezione"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean aggiungiContenutoFotografiaDAO(int idFotoSelezionata, int idCollezioneSelezionato) throws SQLException {

        PreparedStatement insertConFott;
        insertConFott = connection.prepareStatement("INSERT INTO CONTENUTO (id_foto, id_collezione) VALUES (?, ?)");
        insertConFott.setInt(1, idFotoSelezionata);
        insertConFott.setInt(2, idCollezioneSelezionato);
        int rs =  insertConFott.executeUpdate();
        if(rs==1){
            return true;
        }
        return false;
    }


    @Override
    public boolean eliminaContenutoFotografiaDAO(int idFotoSelezionata, int idCollezioneSelezionato) throws SQLException {

        PreparedStatement insertContFot;
        insertContFot = connection.prepareStatement("DELETE FROM CONTENUTO (id_foto, id_collezione) VALUES (?, ?)");
        insertContFot.setInt(1, idFotoSelezionata);
        insertContFot.setInt(2, idCollezioneSelezionato);
        int rs = insertContFot.executeUpdate();
        if(rs==1){
            return true;
        }
        return false;
    }


    //TAG Utente
    @Override
    public boolean vediTagUtenteDAO(int idFotoSelezionata, ArrayList<String> utenteAssociato) throws SQLException{
        try {
            PreparedStatement vediTagUte;
            vediTagUte = connection.prepareStatement("SELECT username FROM tag_utente WHERE id_foto = ?");
            vediTagUte.setInt(1, idFotoSelezionata);
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

    @Override
    public boolean aggiungiTagUtenteDAO(int idFotoSelezionata, String utenteSelezionato) throws SQLException {

        PreparedStatement insertTagUtente;
        insertTagUtente = connection.prepareStatement("INSERT INTO tag_utente (id_foto, username) VALUES (?, ?)");
        insertTagUtente.setInt(1, idFotoSelezionata);
        insertTagUtente. setString(2, utenteSelezionato);
        int rs = insertTagUtente.executeUpdate();
        if (rs == 1) {
            return true;
        }
        return false;

    }

    @Override
    public boolean eliminaTagUtenteDAO(int idFotoSelezionata, String utenteSelezionato) throws SQLException {

        PreparedStatement insertTagUtente;
        insertTagUtente = connection.prepareStatement("DELETE FROM tag_utente WHERE id_foto = ? AND username = ?");
        insertTagUtente.setInt(1, idFotoSelezionata);
        insertTagUtente.setString(2, utenteSelezionato);
        int rs = insertTagUtente.executeUpdate();
        if (rs == 1) {
            return true;
        }
        return false;
    }


    //TAG Soggetto
    @Override
    public boolean vediTagSoggettoDAO(int idFotoSelezionata, ArrayList<String> soggettoAssociato) throws SQLException {
        try {
            PreparedStatement vediTagSog;
            vediTagSog = connection.prepareStatement("SELECT nome_soggetto FROM tag_soggetto WHERE id_foto = ?");
            vediTagSog.setInt(1, idFotoSelezionata);
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

    @Override
    public boolean aggiungiTagSoggettoDAO(int idFotoSelezionata, String soggettoSelezionato) throws SQLException {

        PreparedStatement insertTagSoggetto;
        insertTagSoggetto = connection.prepareStatement("INSERT INTO tag_soggetto (id_foto, nome_soggetto) VALUES (?, ?)");
        insertTagSoggetto.setInt(1, idFotoSelezionata);
        insertTagSoggetto.setString(2, soggettoSelezionato);
        int rs = insertTagSoggetto.executeUpdate();
        if (rs == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminaTagSoggettoDAO(int idFotoSelezionata, String soggettoSelezionato) throws SQLException{

        PreparedStatement insertTagSoggetto;
        insertTagSoggetto = connection.prepareStatement("DELETE FROM tag_soggetto WHERE id_foto = ? AND nome_soggetto = ?");
        insertTagSoggetto.setInt(1, idFotoSelezionata);
        insertTagSoggetto.setString(2, soggettoSelezionato);
        int rs = insertTagSoggetto.executeUpdate();
        if (rs == 1) {
            return true;
        }
        return false;
    }


    /**
     * Funzione di controllo proprietario di una fotografia
     * @param idFotoSelezionata
     * @return True se il proprietario è l'utente che ha fatto l'accesso, altrimenti false
     * @throws SQLException
     */
    @Override
    public boolean controlloProprietarioDAO(int idFotoSelezionata) throws SQLException {
        PreparedStatement controlloProprietario;
        controlloProprietario = connection.prepareStatement("SELECT username_autore FROM fotografia WHERE id_foto = ?");
        controlloProprietario.setInt(1, idFotoSelezionata);

        ResultSet rs = controlloProprietario.executeQuery();
        rs.next();

        String readUsr = rs.getString("username_autore");

        return readUsr.equals(User.getInstance().getUsername()); //Ritorna vero se i nomi equivalgono, altrimenti ritorna falso
    }

}
