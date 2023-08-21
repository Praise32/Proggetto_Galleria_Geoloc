package ImplementazionePostgresDAO;

import DAO.VideoDAO;
import DBconnection.ConnessioneDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timestamp;

public class FramePostgresDAO implements FrameDAO{
    private Connection connection;
}

    public FramePostgresDAO(){
        try {
            connection = ConnessioneDB.getInstance().getConnection();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean eliminaFrameDAO(int idVideoSelezionato, int idFotoSelezionata) throws SQLException{
        PreparedStatement deleteFrame;
        deleteFrame = connection.prepareStatement("DELETE FROM frame WHERE (id_video, id_foto) VALUES (?, ?)");
        deleteFrame.setInt(1, idVideoSelezionato);
        deleteFrame.setInt(2, idFotoSelezionata);
        int result = deleteFrame.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;

    }

    @override
    public boolean aggiungiFrameDAO(int idVideo, int idFoto, int durata, int ordine) throws SQLException {
        PreparedStatement insertFrame;
        insertFrame = connection.prepareStatement("INSERT INTO video (id_video, id_foto, durata, ordine) VALUES (?, ?, ?, ?)");
        insertFrame.setInt(1, idVideo);
        insertFrame.setInt(2, idFoto);
        insertFrame.setInt(3, durata);
        insertFrame.setInt(4, ordine);
        int result = insertFrame.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }


    @override
    public boolean modificaFrameDAO(int idVideoSelezionato, int idFotoSelezionata, int durata, int ordine) throws SQLException {
        PreparedStatement modificaFrame;
        modificaFrame = connection.prepareStatemen("UPDATE frame SET durata = ?, ordine = ? WHERE id_video = ? AND id_foto = ?");
        modificaFrame = setInt(1, durata);
        modificaFrame = setInt(2, ordine);
        modificaFrame = setInt(3, idVideoSelezionato);
        modificaFrame = setInt(4, idFotoSelezionata)
        int rs = modificaFrame.executeQuery();
        if (rs>0) {
            return true;
        }
        return false;
    }

    @override
    public boolean vediVideoAssociato(int idFotoSelezionata, ArrayList<Integer> idVideoAssociato) throws SQLException{
        try {
            PreparedStatement vediVideoFoto;
            vediVideoFoto = connection.prepareStatement("SELECT id_video FROM frame WHERE id_foto = ?");
            vediVideoFoto = setInt(1, idFotoSelezionata);
            ResultSet rs =  vediVideoFoto.executeQuery();
            while (rs.next() ){
                idVideoAssociato.add(rs.getString("id_foto"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }






}

