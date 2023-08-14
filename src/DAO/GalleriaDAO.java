package DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

// Interfaccia GalleriaDAO per le operazioni di accesso ai dati dell'entità Galleria
public interface GalleriaDAO {

    // Metodo per ottenere la lista degli utenti dal database
    // Parametri:
    // - usernamelist: lista degli username degli utenti
    // - passwordlist: lista delle password degli utenti
    // - adminlist: lista dei flag admin degli utenti
    void getListUtenteDAO(ArrayList<String> usernamelist, ArrayList<String> passwordlist, ArrayList<Boolean> adminlist);

    // Metodo per ottenere la lista delle collezioni dal database
    // Parametri:
    // - idCollezioneList: lista degli id delle collezioni
    // - usernameList: lista degli username degli utenti associati alle collezioni
    // - titoloList: lista dei titoli delle collezioni
    // - dataCollezioneList: lista delle date di creazione delle collezioni
    void getListCollezioneDAO(ArrayList<Integer> idCollezioneList, ArrayList<String> usernameList, ArrayList<String> titoloList, ArrayList<Timestamp> dataCollezioneList);

    // Metodo per ottenere la lista dei contenuti dal database
    // Parametri:
    // - idCollezione: lista degli id delle collezioni
    // - idFoto: lista degli id delle foto associate ai contenuti
    void getListContenutoDAO(ArrayList<Integer> idCollezione, ArrayList<Integer> idFoto);

    // Metodo per ottenere la lista delle fotografie dal database
    // Parametri:
    // - idFotoList: lista degli id delle fotografie
    // - usernameAutore: lista degli username degli autori delle fotografie
    // - datiFotoList: lista dei dati delle fotografie
    // - dispositivoList: lista dei dispositivi delle fotografie
    // - dataFotoList: lista delle date di creazione delle fotografie
    // - latitudineList: lista delle latitudini delle fotografie
    // - longituineList: lista delle longitudini delle fotografie
    // - condivisaList: lista dei flag di condivisione delle fotografie
    // - titoloList: lista dei titoli delle fotografie
    void getListFotografiaDAO(ArrayList<Integer> idFotoList, ArrayList<String> usernameAutore, ArrayList<byte[]> datiFotoList, ArrayList<String> dispositivoList, ArrayList<Timestamp> dataFotoList, ArrayList<Float> latitudineList, ArrayList<Float> longituineList, ArrayList<Boolean> condivisaList, ArrayList<String> titoloList);

    // ... altri metodi per ottenere le liste di dati ...

    // Metodo per aggiornare il database con i nuovi dati
    void updateDatabaseDAO() throws SQLException;
}
