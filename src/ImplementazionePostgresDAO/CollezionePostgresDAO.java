package ImplementazionePostgresDAO;

import DAO.CollezioneDAO;
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
public class CollezionePostgresDAO implements CollezioneDAO{
    private Connection connection;


    /**
     * Instantiates a new Collezione postgres dao and set a connection with Database.
     */
    public CollezionePostgresDAO(){
        try {
            connection = ConnessioneDB.getInstance().getConnection();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean eliminaCollezioneDAO(int collezioneSelezionato) throws SQLException {

        PreparedStatement deleteCol;
        deleteCol = connection.prepareStatement("DELETE FROM Collezione WHERE id_collezione = ? ");
        deleteCol.setInt(1, collezioneSelezionato);
        int result = deleteCol.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;


    }

    @Override
    public boolean aggiungiCollezioneDAO(int idCollezione, String username, String titolo) throws SQLException{
        PreparedStatement insertCol;
        insertCol = connection.prepareStatement("INSERT INTO Collezione (id_collezione, username, titolo) VALUES (?, ?, ?)");
        inserCol.setInt(1, idCollezione);
        insertCol.setString(2, username);
        insertCol.setString(3, titolo);
        int result = insertCol.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }

    @override
    public boolean vediContenutoDAO(int idCollezioneSelezionato, ArrayList<Integer> idFotoAssociate) throws SQLException {
        try {
            PreparedStatement vediContenuto;
            vediContenuto = connection.prepareStatement("SELECT id_foto FROM contenuto WHERE id_collezione = ?");
            vediContenuto = setInt(1, idCollezioneSelezionato);
            ResultSet rs =  vediContenuto.executeQuery();
            while (rs.next() ){
                idFotoAssociate.add(rs.getString("id_foto"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @override
    public boolean aggiungiContenutoDAO(int idCollezioneSelezionato, int idFotoSelezionata) throws SQLException {
        try {
            PreparedStatement insertCont;
            insertCont = connection.prepareStatement("INSERT INTO CONTENUTO (id_collezione, id_foto) VALUES (?, ?)");
            insertCont = setInt(1, idCollezioneSelezionato);
            insertCont = setInt(2, idFotoSelezionata);
            ResultSet rs =  insertCont().executeQuery();
            if(rs==1){
                return true;
            }
            return false;
    }

        @override
        public boolean eliminaContenutoDAO(int idCollezioneSelezionato, int idFotoSelezionata) throws SQLException {
            try {
                PreparedStatement insertCont;
                insertCont = connection.prepareStatement("DELETE FROM CONTENUTO (id_collezione, id_foto) VALUES (?, ?)");
                insertCont = setInt(1, idCollezioneSelezionato);
                insertCont = setInt(2, idFotoSelezionata);
                ResultSet rs =  insertCont().executeQuery();
                if(rs==1){
                    return true;
                }
                return false;
            }

            @override
            public boolean modificaCollezioneDAO(int idCollezioneSelezionato, String username, String titolo) throws SQLException {
                PreparedStatement modificaCol;
                modificaCol = connection.prepareStatemen("UPDATE collezione SET username = ?, titolo = ?, WHERE id_collezione = ?");
                modificaCol = setString(1, username);
                modificaCol = setString(2, titolo);
                modificaCol = setInt(3, idCollezioneSelezionato);
        int rs = modificaCol.executeQuery();
        if (rs>0) {
            return true;
        }
        return false;
    }


}
