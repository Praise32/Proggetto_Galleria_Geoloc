package ImplementazionePostgresDAO;

import DAO.FotografiaDAO;
import DBconnection.ConnessioneDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;

public class LuogoPostgresDAO implements DAO.LuogoDAO {

    private Connection connection;


    public LuogoPostgresDAO(){
        try {
            connection = ConnessioneDB.getInstance().getConnection();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean eliminaLuogoDAO(String LuogoSelezionato) throws SQLException {

        PreparedStatement deleteLug;
        deleteLug = connection.prepareStatement("DELETE FROM Luogo WHERE nome = ? ");
        deleteLug.setString(1, LuogoSelezionato);
        int result = deleteLug.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;

    }




    @Override
    public boolean aggiungiLuogoDAO(float latitudine, float longitudine, String nome, String descrizione) throws SQLException{
        PreparedStatement insertLug;
        insertLug = connection.prepareStatement("INSERT INTO Luogo (latitudine, longitudine, nome, descrizione) VALUES (?, ?, ?, ?)");
        insertLug.setFloat(1, latitudine);
        insertLug.setFloat(2, longitudine);
        insertLug.setString(3, nome);
        insertLug.setString(4, descrizione);
        int result = insertLug.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }


    @Override
    public List<MODEL.Luogo> classificaLuoghiDAO() throws SQLException {
        List<MODEL.Luogo> listaLuoghi = new ArrayList<>();

        try {
            PreparedStatement vediClass;
            vediClass = connection.prepareStatement("SELECT latitudine, longitudine, nome, descrizione\n" +
                    "FROM luogo NATURAL LEFT JOIN fotografia\n" +
                    "GROUP BY latitudine, longitudine, nome, descrizione\n" +
                    "ORDER BY COUNT(id_foto) DESC, nome ASC\n" +
                    "LIMIT 3;");
            ResultSet rs =  vediClass.executeQuery();
            while (rs.next()) {
                float latitudine = rs.getFloat("latitudine");
                float longitudine = rs.getFloat("longitudine");
                String nome = rs.getString("nome");
                String descrizione = rs.getString("descrizione");

                MODEL.Luogo luogo = new MODEL.Luogo(latitudine, longitudine, nome, descrizione);

                listaLuoghi.add(luogo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaLuoghi;
    }


    @Override
    public boolean modificaLuogoDAO(String LuogoSelezionato, String descrizione, float latitudine, float longitudine) throws SQLException {
        PreparedStatement modificaluog;
        modificaluog = connection.prepareStatement("UPDATE Luogo SET descrizione = ? AND latitudine = ? AND longitudine = ? WHERE nome = ?");
        modificaluog.setString(1, descrizione);
        modificaluog.setFloat(2, latitudine);
        modificaluog.setFloat(3, longitudine);
        modificaluog.setString(4, LuogoSelezionato);

        int rs = modificaluog.executeUpdate();
        if (rs>0) {
            return true;
        }
        return false;
    }


    public boolean vediFotoAssociateLuogoDAO(String nomeSelezionato, ArrayList<Integer> fotografiaAssociato) throws SQLException{

        try {
            PreparedStatement vediFotoSoggetto;
            vediFotoSoggetto = connection.prepareStatement("SELECT id_foto FROM Luogo NATURAL LEFT JOIN Fotografia WHERE nome = ?");
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
