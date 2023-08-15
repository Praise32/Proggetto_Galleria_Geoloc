package ImplementazionePostgrsDAO;
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
    public void getListUtenteDAO(ArrayList<String> usernamelist, ArrayList<String> passwordlist, ArrayList<Boolean> adminlist){
    try{
        preparedStatement selectListaUtente;
        selectListaUtente = connection.prepareStatement("SELECT * FROM utente");
        resultSet rs = selectListaUtente.executeQuery();
        while (rs.next() ) {
            String username = rs.getString("username");
            String password = rs.getString("password");
            boolean admin = rs.getBoolean("admin");
            //riempio i vettori che saranno usati per istanziare oggetti nel controller[...]
            usernamelist.add(username);
            passwordlist.add(password);
            adminlist.add(admin);
        }
    } catch (SQLexception e) {
        System.out.println("Errore nel dump dei dati Utenti...")
    }
    @override
        public void getListFotografiaDAO(ArrayList<int> idFotoList, ArrayList<String> usernameAutoreList, ArrayList<byte[]> datiFotoList, ArrayList<String> dispositivoList, ArrayList<Timestamp> dataFotoList, ArrayList<float> latitudineList, ArrayList<float> longituineList, ArrayList<boolean> condivisaList, ArrayList<String> titoloList){
        try{
            preparedStatement selectListaFotografia;
            selectListaFotografia = connection.prepareStatement("SELECT * FROM fotografia");
            resultSet rs = selectListaFotografia.executeQuery();
            while (rs.next() ) {
                int idFoto = rs.getInt("id_foto");
                String uesrnameAutore = rs.getString("username_autore");
                byte[] datiFoto = rs.getByte("dati_foto");
                String dispositivo = rs.getString("dispositivo");
                Timestamp dataFoto = rs.getTimestamp("data_foto");
                float latitudine = rs.getFloat("latitudine");
                float longitudine = rs.getFloat("longitudine");
                boolean condivisa = rs.getCondivisa("condivisa");
                String titolo = rs.getTitolo("titolo");
                
                
                idFotoList.add(id_foto);
                usernamelist.add(usernameAutore);
                datiFotoList.add(datiFoto);
                dispositivoList.add(dispositivo);
                dataFotoList.add(dataFoto);
                latitudineList.add(latitudine);
                longitudineList.add(longitudine);
                condivisaList.add(condivisa);
                titoloList.add(titolo);
                
            }
        } catch (SQLexception e) {
            System.out.println("Errore nel dump dei dati Fotografia...")
        }

















































    }
