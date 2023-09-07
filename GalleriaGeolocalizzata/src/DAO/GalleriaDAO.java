package DAO;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

// Interfaccia GalleriaDAO per le operazioni di accesso ai dati dell'entità Galleria
public interface GalleriaDAO {

    // Metodo per ottenere la lista degli utenti
    public void getListUtenteDAO(ArrayList<String> usernamelist, ArrayList<String> passwordlist, ArrayList<Boolean> adminlist);

    // Metodo per ottenere la lista delle collezioni
    public void getListCollezioneDAO (ArrayList<Integer> idCollezioneList, ArrayList<String> utenteUsernameList, ArrayList<String> titoloList, ArrayList<java.sql.Timestamp> dataCollezioneList, ArrayList<Integer> numeroElementiList);

    // Metodo per ottenere la lista delle fotografie
    public void getListFotografiaDAO(ArrayList<Integer> idFotoList, ArrayList<String> utenteUsernameAutoreList, ArrayList<byte[]> datiFotoList, ArrayList<String> dispositivoList, ArrayList<java.sql.Timestamp> dataFotoList, ArrayList<Float>luogolatitudineList, ArrayList<Float>luogolongituineList, ArrayList<Boolean> condivisaList, ArrayList<String> titoloList);

    // Metodo per ottenere la lista dei frames di un video
    public void getListFrameDAO(ArrayList<Integer> videoidVideoList, ArrayList<Integer> fotografiaidFotoList, ArrayList<Integer> durataList, ArrayList<Integer> ordineList);

    // Metodo per ottenere la lista dei luoghi
    public void getListLuogoDAO(ArrayList<Float> latitudineList, ArrayList<Float> longitudineList, ArrayList<String> nomeList, ArrayList<String> descrizioneList);

    // Metodo per ottenere la lista dei soggetti
    public void getListSoggettoDAO(ArrayList<String> nomeList, ArrayList<String> categoriaList);

    // Metodo per ottenere la lista dei video
    public void getListVideoDAO(ArrayList<Integer> idVideoList, ArrayList<String> utenteAutoreList, ArrayList<String> titoloList, ArrayList<Integer> numeroFramesList, ArrayList<Integer> durataList, ArrayList<String> descrizioneList);

    // Metodo per aggiornare il database con i nuovi dati
    void updateDatabaseDAO() throws SQLException;
}
