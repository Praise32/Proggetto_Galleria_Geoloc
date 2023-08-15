package CONTROLLER;
import DAO.*;
import ImplementazionePostgresDAO;
import MODEL.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Controller.
 */
public class Controller {

    private List<Utente> listaUtente = new ArrayList<>();
    private List<Fotografia> listaFotografia = new ArrayList<>();
    private List<Luogo> listaLuogo = new ArrayList<>();
    private List<Collezione> listaCollezione = new ArrayList<>();
    private List<Contenuto> listaContenuto = new ArrayList<>();
    private List<Frame> listaFrame = new ArrayList<>();
    private List<Soggetto> listaSoggetto = new ArrayList<>();
    private List<TagSoggetto> listaTagSoggetto = new ArrayList<>();
    private List<TagUtente> listaTagUtente = new ArrayList<>();
    private List<Video> listaVideo = new ArrayList<>();

    /**
     * Instantiates a new Controller.
     */
    public  Controller() {
        dumpdati();
    }

    //____________________________________________FUNZIONI PER IL LOAD DEI DATI_______________________________________//


    //il dumpDati non inizializza le relazioni di afferenza e la gestione...
    private void dumpdati() {
        updateDatabase();
        dumpDatiUtente();
        dumpDatiFotografia();
        dumpDatiLuogo();
        dumpDatiCollezione();
        dumpDatiContenuto();
        dumpDatiFrame();
        dumpDatiSoggetto();
        dumpDatiTagSoggetto();
        dumpDatiTagUtente();
        dumpDatiVideo();

    }

    public void updateDatabase(){
        GalleriaDAO gallery = new GalleriaPostgresDAO();
        try {
            gallery.updateDatabaseDAO();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void dumpDatiUtente() {
        GalleriaDAO galleriaDAO = new GalleriaPostgresDAO();

        ArrayList<String> usernamelist = new ArrayList<>();
        ArrayList<String> passwordlist = new ArrayList<>();
        ArrayList<Boolean> adminlist = new ArrayList<>();

        galleriaDAO.getListUtenteDAO(usernamelist, passwordlist, adminlist);
        //inizializzo la lista di Utenti
        for (int i = 0; i < usernamelist.size(); i++) {
            listaUtente.add(new Utente(usernamelist.get(i), passwordlist.get(i), adminlist.get(i)));
        }
    }

    public void dumpDatiFotografia() {
        ArrayList<int> idFotoList = new ArrayList<>();
        ArrayList<String> usernameAutoreList = new ArrayList<>();
        ArrayList<byte[]> datiFotoList = new ArrayList<>();
        ArrayList<String> dispositivoList = new ArrayList<>();
        ArrayList<Timestamp> dataFotoList = new ArrayList<>();
        ArrayList<float> latitudineList = new ArrayList<>();
        ArrayList<float> longituineList = new ArrayList<>();
        ArrayList<boolean> condivisaList = new ArrayList<>();
        ArrayList<String> titoloList = new ArrayList<>();

        galleriaDAO.getListFotografiaDAO(idFotoList, usernameAutoreList, datiFotoList, dispositivoList, dataFotoList, latitudineList, longituineList, condivisaList, titoloList);

        //inizializzo la lista delle fotografie
        for (int i = 0; i < idFotoList.size(); i++){
            listaFotografia.add(new Fotografia(idFotoList.get(i), usernameAutoreList.get(i), datiFotoList.get(i), dispositivoList.get(i), dataFotoList.get(i), latitudineList.get(i), longituineList.get(i), condivisaList.get(i), titoloList.get(i)));
        }
    }



}
