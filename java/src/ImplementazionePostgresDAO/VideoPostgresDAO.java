package ImplementazionePostgresDAO;

import DAO.VideoDAO;
import DBconnection.ConnessioneDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

public class VideoPostgresDAO implements VideoDAO{
    private Connection connection;


    public VideoPostgresDAO(){
        try {
            connection = ConnessioneDB.getInstance().getConnection();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean eliminaVideoDAO(int idVideoSelezionato) throws SQLException{
        PreparedStatement deleteVid;
        deleteVid = connection.prepareStatement("DELETE FROM video WHERE id_video = ? ");
        deleteVid.setInt(1, idVideoSelezionato);
        int result = deleteVid.executeUpdate();
        return result == 1;

    }

    @Override
    public boolean aggiungiVideoDAO(int idVideo, String autore, String titolo,  String descrizione) throws SQLException {

        int numero_frames = 0;
        int durata = 0;

        PreparedStatement insertVid;
        insertVid = connection.prepareStatement("INSERT INTO video (id_video, autore, titolo, numero_frames, durata, descrizione) VALUES (?, ?, ?, ?, ?, ?)");
        insertVid.setInt(1, idVideo);
        insertVid.setString(2, autore);
        insertVid.setString(3, titolo);
        insertVid.setInt(4, numero_frames);
        insertVid.setInt(5, durata);
        insertVid.setString(6, descrizione);
        int result = insertVid.executeUpdate();
        return result == 1;
    }


    @Override
    public boolean modificaVideoDAO(int idVideoSelezionato, String titolo, String descrizione) throws SQLException {
        PreparedStatement modificaVid;
        modificaVid = connection.prepareStatement("UPDATE video SET titolo = ?, descrizione = ? WHERE id_video = ?");
        modificaVid.setString(1, titolo);
        modificaVid.setString(2, descrizione);
        modificaVid.setInt(3, idVideoSelezionato);
        int rs = modificaVid.executeUpdate();
        return rs > 0;
    }

    @Override
    public boolean vediFrameVideoDAO(int idVideoSelezionato, ArrayList<Integer> frameAssociati) throws SQLException{
        try {
            PreparedStatement vediFotoVideo;
            vediFotoVideo = connection.prepareStatement("SELECT id_foto FROM frame WHERE id_video = ?");
            vediFotoVideo.setInt(1, idVideoSelezionato);
            ResultSet rs =  vediFotoVideo.executeQuery();
            while (rs.next() ){
                frameAssociati.add(rs.getInt("id_foto"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }






}

