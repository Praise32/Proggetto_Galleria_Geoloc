package ImplementazionePostgresDAO;
import DAO.GalleriaDAO;
import DBConnection.ConnesioneDB;

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

    @override
    public void getListUtenteDAO(ArrayList<String> usernamelist, ArrayList<String> passwordlist, ArrayList<Boolean> adminlist) {
        try {
            preparedStatement selectListaUtente;
            selectListaUtente = connection.prepareStatement("SELECT * FROM utente");
            resultSet rs = selectListaUtente.executeQuery();
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
    @override
    public void getListFotografiaDAO (ArrayList <Integer> idFotoList, ArrayList <String> utenteUsernameAutoreList, ArrayList <byte[]> datiFotoList, ArrayList <String> dispositivoList, ArrayList <java.sql.Timestamp> dataFotoList, ArrayList <float>luogolatitudineList, ArrayList <float>luogolongituineList, ArrayList <boolean> condivisaList, ArrayList <String> titoloList){
        try {
            preparedStatement selectListaFotografia;
            selectListaFotografia = connection.prepareStatement("SELECT * FROM fotografia");
            resultSet rs = selectListaFotografia.executeQuery();
            while (rs.next()) {
                int idFoto = rs.getInt("id_foto");
                String usernameAutore = rs.getString("username_autore");
                byte[] datiFoto = rs.getByte("dati_foto");
                String dispositivo = rs.getString("dispositivo");
                Timestamp dataFoto = rs.getTimestamp("data_foto");
                float latitudine = rs.getFloat("latitudine");
                float longitudine = rs.getFloat("longitudine");
                boolean condivisa = rs.getCondivisa("condivisa");
                String titolo = rs.getTitolo("titolo");


                idFotoList.add(idfoto);
                utenteUsernameAutoreList.add(usernameAutore);
                datiFotoList.add(datiFoto);
                dispositivoList.add(dispositivo);
                dataFotoList.add(dataFoto);
                luogolatitudineList.add(luogolat);
                luogolongituineList.add(luogolon);
                condivisaList.add(condivisa);
                titoloList.add(titolo);

            }
        } catch (SQLexception e) {
            System.out.println("Errore nel dump dei dati Fotografia...")
        }
    }

    @override
    public void getListCollezioneDAO (ArrayList <Integer> idCollezioneList, ArrayList <String> utenteUsernameList, ArrayList <String> titoloList, ArrayList <java.sql.Timestamp> dataCollezioneList, ArrayList<Integer> numeroElementiList) {

        try {
            preparedStatement selectListaCollezioni;
            selectListaCollezioni = connection.prepareStatement("SELECT * FROM collezione");
            resultSet rs = selectListaCollezioni.executeQuery();
            while (rs.next()) {
                int idCollezione = rs.getInt("id_collezione");
                String username = rs.getString("username");
                String titolo = rs.getString("titolo");
                Timestamp dataCollezione = rs.getTimestamp("data_collezione"):
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
    @override
    public void getListFrameDAO(ArrayList<Integer> videoidVideoList, ArrayList<Integer> fotografiaidFotoList, ArrayList<Integer> durataList, ArrayList<Integer> ordineList) {
        try{
            preparedStatement selectListaFrame;
            selectListaFrame = connection.prepareStatement ("SELECT * FROM frame");
            resultSet rs = selectListaFrame.executeQuery();
            while (rs.next() ) {
                int idVideo = rs.getInt("id_video");
                int idFoto = rs.getInt("id_foto");
                int durata = rs.getInt("durata");
                int ordine = rs.getInt("ordine");

                videoidVideoList.add(idCollezione);
                fotografiaidFotoList.add(idFoto);
                durataList.add(durata);
                ordineList.add(ordine);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @override
    public void getListLuogoDAO(ArrayList<float> latitudineList, ArrayList<float> longitudineList, ArrayList<String> nomeList, ArrayList<String> descrizioneList) {
        try {
            preparedStatement selectListaLuogo;
            selectListaLuogo = connection.prepareStatement("SELECT * FROM luogo");
            resultSet rs = selectListaLuogo.executeQuery();
            while (rs.next()) {
                float latitudine = rs.getFloat("latitudine");
                float longitudine = rs.getFloat("longitudine");
                String nome = rs.getString("nome");
                String descrizione = rs.getString("descrizione");

                latitudineList.add(latitudine);
                longitudineList.add(longitudine);
                nomeList.add(nome);
                descrizioneList.add(descrizione);

            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    @override
    public void getListSoggettoDAO(ArrayList<String> nomeList, ArrayList<String> categoriaList){
        try {
            preparedStatement selectListaSoggetto;
            selectListaSoggetto = connection.prepareStatement("SELECT * FROM soggetto");
            resultSet rs = selectListaSoggetto.executeQuery();
            while (rs.next() ) {
                String nome = rs.getString("nome");
                String categoria = rs.getString("categoria");

                nomeList.add(nome);
                categoriaList.add(categoria);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @override
    public void getListVideoDAO(ArrayList<integer> idVideoList, ArrayList<String> utenteAutoreList, ArrayList<String> titoloList, ArrayList<Integer> numeroFramesList, ArrayList<Integer> durataList, ArrayList<String> descrizioneList){
        try {
            preparedStatement selectListaVideo;
            selectListaVideo = connection.prepareStatement("SELECT * FROM video");
            resultSet rs = selectListaVideo.executeQuery();
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


            } catch (SQLException e) {
                e.printStackTrace();
            }
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
