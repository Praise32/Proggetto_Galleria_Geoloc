package CONTROLLER;
import DAO.*;
import ImplementazionePostgresDAO;
import MODEL.*;

import java.sql.Timestamp;
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
    private List<Frame> listaFrame = new ArrayList<>();
    private List<Soggetto> listaSoggetto = new ArrayList<>();
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
        dumpDatiFrame();
        dumpDatiSoggetto();
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
        ArrayList<Integer> idFotoList = new ArrayList<>();
        ArrayList<String> utenteUsernameAutoreList = new ArrayList<>();
        ArrayList<byte[]> datiFotoList = new ArrayList<>();
        ArrayList<String> dispositivoList = new ArrayList<>();
        ArrayList<java.sql.Timestamp> dataFotoList = new ArrayList<>();
        ArrayList<Float> luogolatitudineList = new ArrayList<>();
        ArrayList<Float> luogolongituineList = new ArrayList<>();
        ArrayList<Boolean> condivisaList = new ArrayList<>();
        ArrayList<String> titoloList = new ArrayList<>();

        galleriaDAO.getListFotografiaDAO(idFotoList, utenteUsernameAutoreList, datiFotoList, dispositivoList, dataFotoList, luogolatitudineList, luogolongituineList, condivisaList, titoloList);

        //inizializzo la lista delle fotografie
      for (int i = 0; i < idFotoList.size(); i++) {
          Utente utente = null;
          Luogo luogolat = null;
          Luogo luogolon = null;
          //ciclo che associa ogni utente le sue fotografia e ogni fotografia l'username dell'autore
          for (Utente usr : listaUtente) {
              if(usr.getUsername().equals(usernameAutoreList.get(i))) {
                  utente = usr;
                  break;
              }
          }

          //ciclo che associa ogni luogo le fotografia scattate e ogni fotografia ad un luogo
          for (Luogo lat : listaLuogo) {
              if(lat.getLatitudine().equals(luogolatitudineList.get(i))) {
                  luogolat = lat;
                  break;
              }
          }
          for (Luogo lon : listaLuogo) {
              if(lon.getLatitudine().equals(luogolongituineList.get(i))) {
                  luogolon = lon;
                  break;
              }
          }

              Fotografia f = new Fotografia(idFotoList.get(i), usernameAutore, datiFotoList.get(i), dispositivoList.get(i), dataFotoList.get(i), luogolat, luogolon, condivisaList.get(i), titoloList.get(i));
              listaFotografia.add(f);

      }

    }

    public void dumpDatiLuogo() {
        ArrayList<Float> latitudineList = new ArrayList<>();
        ArrayList<Float> longitudineList = new ArrayList<>();
        ArrayList<String> nomeList = new ArrayList<>();
        ArrayList<String> descrizioneList = new ArrayList<>();

        galleriaDAO.getListLuogoDAO();
        //inizializzo la lista dei luoghi, uso solo una delle due PK dato che per definizione im SQL un luogo è definito da due coordinate solo
        for (int i = 0; i < latitudineList.size(); i++) {
            listaLuogo.add(new Luogo(latitudineList.get(i), longitudineList.get(i), nomeList.get(i), descrizioneList.get(i)));
        }
    }

    public void dumpDatiCollezione() {
        ArrayList<Integer> idCollezioneList = new ArrayList<>();
        ArrayList<String> utenteUsernameList = new ArrayList<>();
        ArrayList<String> titoloList = new ArrayList<>();
        ArrayList<java.sql.Timestamp> dataCollezioneList = new ArrayList<>();
        ArrayList<Integer> numeroElementiList = new ArrayList<>();

        galleriaDAO.getListCollezioneDAO(idCollezioneList, utenteUsernameList, titoloList, dataCollezioneList, numeroElementiList);

        for (int i = 0; i < idCollezioneList.size(); i++) {
            Utente utente = null;
            //trovo prima l'utente proprietario della collezione e poi istanzio la collezione...
            for (Utente usr : listaUtente) {
                if(usr.getUsername().equals(utenteUsernameList.get(i))) {
                    utente = usr;
                    break;
                }
            }

            listaCollezione.add(new Collezione(idCollezioneList.get(i), username, titoloList.get(i), dataCollezioneList.get(i), numeroElementiList.get(i)));
        }
    }

    public void dumpDatiFrame {
        ArrayList<Integer> videoidVideoList = new ArrayList<>();
        ArrayList<Integer> fotografiaidFotoList = new ArrayList<>();
        ArrayList<Integer> durataList = new ArrayList<>();
        ArrayList<Integer> ordineList) = new ArrayList<>();

        galleriaDAO.getListFrameDAO(videoidVideoList, fotografiaidFotoList, durataList, ordineList);

        for (int i = videoidVideoList.size(); i++) {
            Fotografia fotografia = null;
            for(Fotografia fot : listaFotografia) {
                if(fot.getIdFoto().equals(fotografiaidFotoList.get(i))) {
                    fotografia = fot;
                    break;
                }
            }
            Frame fr = new Frame(videoidVideoList.get(i), fotografia, durataList.get(i), ordineList.get(i));
            listaFrame.add(fr);
        }
    }

    public void dumpDatiSoggetto {
        ArrayList<String> nomeList = new ArrayList<>();
        ArrayList<String> categoriaList = new ArrayList<>();

        galleriaDAO.getListSoggettoDAO(nomeList, categoriaList);

        for (int i = 0; i < nomeList.size(); i++) {
            listaSoggetto.add(new Soggetto(nomeList.get(i), categoriaList.get(i)));
        }
    }

    public void dumpDatiVideo {
        ArrayList<Integer> idVideoList = new ArrayList<>();
        ArrayList<String> utenteAutoreList = new ArrayList<>();
        ArrayList<String> titoloList = new ArrayList<>();
        ArrayList<Integer> numeroFramesList = new ArrayList<>();
        ArrayList<Integer> durataList = new ArrayList<>();
        ArrayList<String> descrizioneList = new ArrayList<>();

        galleriaDAO.getListVideoDAO(idVideoList, utenteAutoreList, titoloList, numeroFramesList, durataList, descrizioneList);

        for (int i = 0; i < idVideoList.size(); i++) {
            Utente utente = null;
            for (Utente usr : listaUtente) {
                if( usr.getUtente().equals(utenteAutoreList.get(i))) {
                    utente = usr;
                    break;
                }
            }
            Video v = new Video(idVideoList.get(i), autore, titoloList.get(i), numeroFramesList.get(i), durataList.get(i), descrizioneList.get(i));
            listaVideo.add(v);
        }
    }

//____________________________________________________________________________________________________________________//
//____________________________________________________________________________________________________________________//




//_______________________________________FUNZIONI PER UTENTE//
    /**
     * Aggiunge un nuovo utente al sistema.
     *
     * @param username   Il nome utente dell'utente.
     * @param password   La password dell'utente.
     * @param admin      True se l'utente è un amministratore, False altrimenti.
     * @throws SQLException   Eccezione sollevata in caso di problemi con il database.
     */
    public void aggiungiUtente(String username, String password, boolean admin) throws SQLException {
        Utente usr = new UtentePostgresDAO();

        boolean control = usr.aggiungiUtenteDAO(username, password, admin);

        if (control) {
            System.out.println("Utente aggiunto con successo!");
            Utente u = new Utente(username, passworda, admin);
            listaUtente.add(a);
        }
    }

    /**
     * Elimina un utente dal sistema in base all'utente selezionato.
     *
     * @param utenteSelezionato   Il nome utente dell'utente da eliminare.
     * @throws SQLException      Eccezione sollevata in caso di problemi con il database.
     */
    public void eliminaUtente(String utenteSelezionato) throws SQLException {
        UtenteDAO u = new UtentePostgresDAO();

        boolean control = u.eliminaUtenteDAO(utenteSelezionato);

        if (control) {
            System.out.println("Eliminazione avvenuta con successo ...!");

            //SE L'ELIMINAZIONE HA AVUTO SUCCESSO ALLORA ELIMINO ANCHE DAL MODEL L'UTENTE...
            for (Utente usr : listaUtente) {
                if (utenteSelezionato.equals(usr.getUsername())) {
                    listaUtente.remove(usr);
                    break;
                }
            }
        } else {
            System.out.println("Impossibile rimuovere l'utente...");
        }
    }

    /**
     * Modifica le informazioni di un utente esistente nel sistema.
     *
     * @param usernameSelezionato   Il nome utente dell'utente da modificare.
     * @param password              La nuova password dell'utente.
     * @param admin                 True se l'utente deve essere impostato come amministratore, False altrimenti.
     * @throws SQLException         Eccezione sollevata in caso di problemi con il database.
     */
    public void modificaUtente(String usernameSelezionato, String password, boolean admin) throws SQLException {

        UtenteDAO u = new modificaUtenteDAO();

        boolean control = u.modificaUtenteDAO(usernameSelezionato, password, admin);


        //se modifico l'utente nel database allora lo modifico anche nel MODEL
        if (control) {
            for (Utente usr : listaUtente)
                if (usr.getUsername().equals(usernameSelezionato)) {
                    usr.setPassword(password);
                    usr.setAdmin(admin);
                    break;
                }
        }

    }

    /**
     * Ottiene una lista di identificatori di collezioni associate a un utente per visualizzazione.
     *
     * @param usernameSelezionato   Il nome utente dell'utente per cui si vogliono ottenere gli identificatori delle collezioni.
     * @return                      Un ArrayList contenente gli identificatori delle collezioni associate all'utente.
     */
    public ArrayList<Integer> VediCollezioniPerUtente(String usernameSelezionato) {
        UtenteDAO u = new UtentePostgresDAO();

        //trovo l'utente a cui si riferisce la vista
        Utente displayUsr = null;
        for (Utente usr : listaUtente)
            if (usr.getUsername().equals(usernameSelezionato)) {
                displayUsr = usr;
                break;
            }


        //trovo le collezioni associate
        ArrayList<Integer> idCollezioneAssociato = new ArrayList<>();
        boolean control = u.VediCollezioniPerUtenteDAO(usernameSelezionato, idCollezioneAssociato);

        //inzializzo la lista di collezioni a cui afferisce l'utente
        if (control) {
            for (Collezione col : listaCollezione)
                for (int c : idCollezioneAssociato)
                    if (col.getIdCollezione().equals(c)) {
                        assert displayUsr != null;
                        displayUsr.aggiungiCollezione(col);
                    }
        }

        return idCollezioneAssociato;

    }

    /**
     * Ottiene una lista di identificatori di fotografie associate a un utente per visualizzazione.
     *
     * @param usernameSelezionato   Il nome utente dell'utente per cui si vogliono ottenere gli identificatori delle fotografie.
     * @return                      Un ArrayList contenente gli identificatori delle fotografie associate all'utente.
     */
    public ArrayList<Integer> VediFotografiaPerUtente(String usernameSelezionato) {
        UtenteDAO u = new UtentePostgresDAO();

        //trovo l'utente a cui si riferisce la vista
        Utente displayUsr = null;
        for (Utente usr : listaUtente)
            if (usr.getUsername().equals(usernameSelezionato)) {
                displayUsr = usr;
                break;
            }


        //trovo le fotografie associate
        ArrayList<Integer> idFotoAssociato = new ArrayList<>();
        boolean control = u.VediFotografiaPerUtenteDAO(usernameSelezionato, idFotoAssociato);

        //inzializzo la lista di fotografie a cui afferisce l'utente
        if (control) {
            for (Fotografia fot : listaFotografia)
                for (int f : idFotoAssociato)
                    if (fot.getIdFoto().equals(f)) {
                        assert displayUsr != null;
                        displayUsr.aggiungiFotografia(fot);
                    }
        }

        return idFotoAssociato;

    }
    
    /**
     * Ottiene una lista di identificatori di fotografie associate a un utente per visualizzazione.
     *
     * @param usernameSelezionato   Il nome utente dell'utente per cui si vogliono ottenere gli identificatori delle fotografie.
     * @return                      Un ArrayList contenente gli identificatori delle fotografie associate all'utente.
     */
    public ArrayList<Integer> VediVideoPerUtente(String usernameSelezionato) {
        UtenteDAO u = new UtentePostgresDAO();

        //trovo l'utente a cui si riferisce la vista
        Utente displayUsr = null;
        for (Utente usr : listaUtente)
            if (usr.getUsername().equals(usernameSelezionato)) {
                displayUsr = usr;
                break;
            }


        //trovo i video associati
        ArrayList<Integer> idVideoAssociato = new ArrayList<>();
        boolean control = u.VediVideoPerUtenteDAO(usernameSelezionato, idVideoAssociato);

        //inzializzo la lista di video a cui afferisce l'utente
        if (control) {
            for (Video vid : listaVideo)
                for (int v : idVideoAssociato)
                    if (vid.getIdVideo().equals(v)) {
                        assert displayUsr != null;
                        displayUsr.aggiungiVideo(vid);
                    }
        }

        return idVideoAssociato;

    }




}
