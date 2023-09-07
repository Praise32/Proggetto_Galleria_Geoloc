package ImplementazionePostgresDAO;

import DAO.SoggettoDAO;
import DBconnection.ConnessioneDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

public class SoggettoPostgresDAO implements SoggettoDAO{
    private Connection connection;


    public SoggettoPostgresDAO(){
        try {
            connection = ConnessioneDB.getInstance().getConnection();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean eliminaSoggettoDAO(String nomeSelezionato) throws SQLException {
        PreparedStatement deleteSog;
        deleteSog = connection.prepareStatement("DELETE FROM soggetto WHERE nome = ? ");
        deleteSog.setString(1, nomeSelezionato);
        int result = deleteSog.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;

    }

    @Override
    public boolean aggiungiSoggettoDAO(String nome, String categoria) throws SQLException {
        PreparedStatement insertSog;
        insertSog = connection.prepareStatement("INSERT INTO soggetto (nome, categoria) VALUES (?, ?)");
        insertSog.setString(1, nome);
        insertSog.setString(2, categoria);
        int result = insertSog.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }


    @Override
    public boolean modificaSoggettoDAO(String nomeSelezionato, String categoria, String nuovoNome) throws SQLException {
        PreparedStatement modificaSog;
        modificaSog = connection.prepareStatement("UPDATE soggetto SET nome = ?, categoria = ? WHERE nome = ?");
        modificaSog.setString(1, nuovoNome);
        modificaSog.setString(2, categoria);
        modificaSog.setString(3, nomeSelezionato);
        int rs = modificaSog.executeUpdate();
        if (rs>0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean vediFotoAssociateDAO(String nomeSelezionato, ArrayList<Integer> fotografiaAssociato) throws SQLException{
        try {
            PreparedStatement vediFotoSoggetto;
            vediFotoSoggetto = connection.prepareStatement("SELECT id_foto FROM tag_soggetto WHERE nome_soggetto = ?");
            vediFotoSoggetto.setString(1, nomeSelezionato);
            ResultSet rs =  vediFotoSoggetto.executeQuery();
            while (rs.next() ){
                fotografiaAssociato.add(rs.getInt("id_foto"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }






    }

