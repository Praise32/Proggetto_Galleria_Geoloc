package ImplementazionePostgresDAO;

import DAO.GalleriaDAO;
import DBconnection.ConnessioneDB;
import MAIN.Main;
import MAIN.User;
import org.jdesktop.swingx.auth.UserNameStore;

import java.sql.*;
import java.util.ArrayList;

public class GalleriaPostgresDAO implements GalleriaDAO {

    private Connection connection;

    /**
     * Instantiates a new Galleria postgres dao and set a connection with Database.
     */
//il costruttore crea la connessione con il db[...]
    public GalleriaPostgresDAO(){
        try {
            connection = ConnessioneDB.getInstance().getConnection();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    };

    @Override
    public void getListUtenteDAO(ArrayList<String> usernamelist, ArrayList<String> passwordlist, ArrayList<Boolean> adminlist) {
        try {
            PreparedStatement selectListaUtente;
            selectListaUtente = connection.prepareStatement("SELECT * FROM utente");
            ResultSet rs = selectListaUtente.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                boolean admin = rs.getBoolean("admin");
                //riempio i vettori che saranno usati per istanziare oggetti nel controller[...]
                usernamelist.add(username);
                passwordlist.add(password);
                adminlist.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void getListFotografiaDAO (ArrayList <Integer> idFotoList, ArrayList <String> utenteUsernameAutoreList, ArrayList <byte[]> datiFotoList, ArrayList <String> dispositivoList, ArrayList <java.sql.Timestamp> dataFotoList, ArrayList <Float>luogolatitudineList, ArrayList <Float>luogolongituineList, ArrayList <Boolean> condivisaList, ArrayList <String> titoloList){
        try {
            PreparedStatement selectListaFotografia;
            //TODO Risolvere l'username che non viene salvato, probabilmente questa funzione viene avviata prima di avere l'istanza
            selectListaFotografia = connection.prepareStatement("SELECT * FROM fotografia WHERE username_autore = ? OR condivisa = TRUE");
            selectListaFotografia.setString(1, User.getInstance().getUsername());
            ResultSet rs = selectListaFotografia.executeQuery();
            while (rs.next()) {
                int idFoto = rs.getInt("id_foto");
                String usernameAutore = rs.getString("username_autore");
                byte[] datiFoto = rs.getBytes("dati_foto");
                String dispositivo = rs.getString("dispositivo");
                Timestamp dataFoto = rs.getTimestamp("data_foto");

                //TODO RISOLVERE IL FLOAT CHE VIENE LETTO COME NULL
                float latitudine = rs.getFloat("latitudine");
                float longitudine = rs.getFloat("longitudine");
                boolean condivisa = rs.getBoolean("condivisa");
                String titolo = rs.getString("titolo");


                idFotoList.add(idFoto);
                utenteUsernameAutoreList.add(usernameAutore);
                datiFotoList.add(datiFoto);
                dispositivoList.add(dispositivo);
                dataFotoList.add(dataFoto);
                luogolatitudineList.add(latitudine);
                luogolongituineList.add(longitudine);
                condivisaList.add(condivisa);
                titoloList.add(titolo);

            }
        } catch (SQLException e) {
            System.out.println("Errore nel dump dei dati Fotografia...");
        }
    }

    @Override
    public void getListCollezioneDAO (ArrayList<Integer> idCollezioneList, ArrayList<String> utenteUsernameList, ArrayList<String> titoloList, ArrayList<java.sql.Timestamp> dataCollezioneList, ArrayList<Integer> numeroElementiList) {

        try {
            PreparedStatement selectListaCollezioni;
            selectListaCollezioni = connection.prepareStatement("SELECT * FROM collezione");
            ResultSet rs = selectListaCollezioni.executeQuery();
            while (rs.next()) {
                int idCollezione = rs.getInt("id_collezione");
                String username = rs.getString("username");
                String titolo = rs.getString("titolo");
                Timestamp dataCollezione = rs.getTimestamp("data_collezione");
                int numeroElementi = rs.getInt("numero_elementi");

                idCollezioneList.add(idCollezione);
                utenteUsernameList.add(username);
                titoloList.add(titolo);
                dataCollezioneList.add(dataCollezione);
                numeroElementiList.add(numeroElementi);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void getListFrameDAO(ArrayList<Integer> videoidVideoList, ArrayList<Integer> fotografiaidFotoList, ArrayList<Integer> durataList, ArrayList<Integer> ordineList) {
        try{
            PreparedStatement selectListaFrame;
            selectListaFrame = connection.prepareStatement ("SELECT * FROM frame");
            ResultSet rs = selectListaFrame.executeQuery();
            while (rs.next() ) {
                int idVideo = rs.getInt("id_video");
                int idFoto = rs.getInt("id_foto");
                int durata = rs.getInt("durata");
                int ordine = rs.getInt("ordine");

                videoidVideoList.add(idVideo);
                fotografiaidFotoList.add(idFoto);
                durataList.add(durata);
                ordineList.add(ordine);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getListLuogoDAO(ArrayList<Float> latitudineList, ArrayList<Float> longitudineList, ArrayList<String> nomeList, ArrayList<String> descrizioneList) {
        try {
            PreparedStatement selectListaLuogo;
            selectListaLuogo = connection.prepareStatement("SELECT * FROM luogo");
            ResultSet rs = selectListaLuogo.executeQuery();
            while (rs.next()) {
                float latitudine = rs.getFloat("latitudine");
                float longitudine = rs.getFloat("longitudine");
                String nome = rs.getString("nome");
                String descrizione = rs.getString("descrizione");

                latitudineList.add(latitudine);
                longitudineList.add(longitudine);
                nomeList.add(nome);
                descrizioneList.add(descrizione);

            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getListSoggettoDAO(ArrayList<String> nomeList, ArrayList<String> categoriaList){
        try {
            PreparedStatement selectListaSoggetto;
            selectListaSoggetto = connection.prepareStatement("SELECT * FROM soggetto");
            ResultSet rs = selectListaSoggetto.executeQuery();
            while (rs.next() ) {
                String nome = rs.getString("nome");
                String categoria = rs.getString("categoria");

                nomeList.add(nome);
                categoriaList.add(categoria);

            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getListVideoDAO(ArrayList<Integer> idVideoList, ArrayList<String> utenteAutoreList, ArrayList<String> titoloList, ArrayList<Integer> numeroFramesList, ArrayList<Integer> durataList, ArrayList<String> descrizioneList){
        try {
            PreparedStatement selectListaVideo;
            selectListaVideo = connection.prepareStatement("SELECT * FROM video");
            ResultSet rs = selectListaVideo.executeQuery();
            while (rs.next() ) {
                int idVideo = rs.getInt("id_video");
                String autore = rs.getString("autore");
                String titolo = rs.getString("titolo");
                int numeroFrames = rs.getInt("numero_frames");
                int durata = rs.getInt("durata");
                String descrizione = rs.getString("descrizione");

                idVideoList.add(idVideo);
                utenteAutoreList.add(autore);
                titoloList.add(titolo);
                numeroFramesList.add(numeroFrames);
                durataList.add(durata);
                descrizioneList.add(descrizione);


            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }


    public void updateDatabaseDAO() throws SQLException{
        try {
            PreparedStatement updData;
            connection.prepareStatement("SELECT update_database();");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
}
