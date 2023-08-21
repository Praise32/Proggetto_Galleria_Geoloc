package ImplementazionePostgresDAO;

import DAO.VideoDAO;
import DBconnection.ConnessioneDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timestamp;

public class VideoPostgresDAO implements VideoDAO{
    private Connection connection;
}

    public VideoPostgresDAO(){
        try {
            connection = ConnessioneDB.getInstance().getConnection();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    boolean eliminaVideoDAO(String idVideoSelezionato) throws SQLException{
        PreparedStatement deleteVid;
        deleteVid = connection.prepareStatement("DELETE FROM video WHERE id_video = ? ");
        deleteVid.setString(1, idVideoSelezionato);
        int result = deleteVid.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;

    }

    @override
    boolean aggiungiVideoDAO(int idVideo, String autore, String titolo, String descrizione) throws SQLException {
        PreparedStatement insertVid;
        insertVid = connection.prepareStatement("INSERT INTO video (idVideo, autore, titolo, descrizione) VALUES (?, ?, ?, ?)");
        insertVid.setInt(1, idVideo);
        insertVid.setString(2, autore);
        insertVid.setString(3, titolo);
        insertVid.setString(4, descrizione);
        int result = insertVid.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }


    @override
    boolean modificaVideoDAO(String idVideoSelezionato, String titolo, String descrizione) throws SQLException {
        PreparedStatement modificaSog;
        modificaSog = connection.prepareStatemen("UPDATE video SET titolo = ?, descrizione = ? WHERE id_video = ?");
        modificaSog = setString(1, titolo);
        modificaSog = setString(2, descrizione);
        modificaSog = setString(3, idVideoSelezionato);
        int rs = modificaSog.executeQuery();
        if (rs>0) {
            return true;
        }
        return false;
    }

    @override
    boolean vediFotoAssociate(String nomeSelezionato, ArrayList<Integer> fotografiaAssociato) throws SQLException{
        try {
            PreparedStatement vediFotoSoggetto;
            vediFotoSoggetto = connection.prepareStatement("SELECT id_foto FROM tag_soggetto WHERE nome_soggetto = ?");
            vediFotoSoggetto = setInt(1, fotografiaAssociato)
            vediFotoSoggetto = setString(2, nomeSelezionato);
            ResultSet rs =  vediTagUte.executeQuery();
            while (rs.next() ){
                fotografiaAssociato.add(rs.getString("id_foto"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }






}

