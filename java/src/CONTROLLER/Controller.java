package CONTROLLER;

import DAO.*;
import ImplementazionePostgresDAO.*;
import ImplementazionePostgresDAO.FramePostgresDAO;
import ImplementazionePostgresDAO.VideoPostgresDAO;
import MODEL.*;

import java.text.DecimalFormat;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public Controller() {
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

    public void updateDatabase() {
        GalleriaDAO gallery = new GalleriaPostgresDAO();
        try {
            gallery.updateDatabaseDAO();
        } catch (SQLException e) {
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
        GalleriaDAO galleriaDAO = new GalleriaPostgresDAO();


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
            Luogo latitudine = null;
            Luogo longitudine = null;
            //ciclo che associa ogni utente le sue fotografia e ogni fotografia l'username dell'autore
            for (Utente usr : listaUtente) {
                if (usr.getUsername().equals(utenteUsernameAutoreList.get(i))) {
                    utente = usr;
                    break;
                }
            }
            //ciclo che associa ogni luogo le fotografia scattate e ogni fotografia ad un luogo
            for (Luogo lat : listaLuogo) {
                if (Float.compare(lat.getLatitudine(), luogolatitudineList.get(i)) == 0) {
                    latitudine = lat;
                    break;
                }
            }

            for (Luogo lon : listaLuogo) {
                if (Float.compare(lon.getLongitudine(), luogolongituineList.get(i)) == 0) {
                    longitudine = lon;
                    break;
                }
            }


            Fotografia f = new Fotografia(idFotoList.get(i), utente, datiFotoList.get(i), dispositivoList.get(i), dataFotoList.get(i), latitudine, longitudine, condivisaList.get(i), titoloList.get(i));
            listaFotografia.add(f);

        }

    }

    public void dumpDatiLuogo() {
        GalleriaDAO galleriaDAO = new GalleriaPostgresDAO();

        ArrayList<Float> latitudineList = new ArrayList<>();
        ArrayList<Float> longitudineList = new ArrayList<>();
        ArrayList<String> nomeList = new ArrayList<>();
        ArrayList<String> descrizioneList = new ArrayList<>();

        galleriaDAO.getListLuogoDAO(latitudineList, longitudineList, nomeList, descrizioneList);
        //inizializzo la lista dei luoghi, uso solo una delle due PK dato che per definizione im SQL un luogo è definito da due coordinate solo
        for (int i = 0; i < latitudineList.size(); i++) {
            listaLuogo.add(new Luogo(latitudineList.get(i), longitudineList.get(i), nomeList.get(i), descrizioneList.get(i)));
        }
    }

    public void dumpDatiCollezione() {
        GalleriaDAO galleriaDAO = new GalleriaPostgresDAO();

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
                if (usr.getUsername().equals(utenteUsernameList.get(i))) {
                    utente = usr;
                    break;
                }
            }

            listaCollezione.add(new Collezione(idCollezioneList.get(i), utente, titoloList.get(i), dataCollezioneList.get(i), numeroElementiList.get(i)));
        }
    }

    public void dumpDatiFrame() {
        GalleriaDAO galleriaDAO = new GalleriaPostgresDAO();

        ArrayList<Integer> videoidVideoList = new ArrayList<>();
        ArrayList<Integer> fotografiaidFotoList = new ArrayList<>();
        ArrayList<Integer> durataList = new ArrayList<>();
        ArrayList<Integer> ordineList = new ArrayList<>();

        galleriaDAO.getListFrameDAO(videoidVideoList, fotografiaidFotoList, durataList, ordineList);

        for (int i = 0; i < videoidVideoList.size(); i++) {
            Fotografia fotografia = null;
            Video video = null;
            for (Fotografia fot : listaFotografia) {
                if (fot.getIdFoto() == fotografiaidFotoList.get(i)) {
                    fotografia = fot;
                    break;
                }
            }

            for (Video vid : listaVideo) {
                if (vid.getIdVideo() == videoidVideoList.get(i)) {
                    video = vid;
                    break;
                }
            }
            Frame fr = new Frame(video, fotografia, durataList.get(i), ordineList.get(i));
            listaFrame.add(fr);
        }
    }

    public void dumpDatiSoggetto() {
        GalleriaDAO galleriaDAO = new GalleriaPostgresDAO();

        ArrayList<String> nomeList = new ArrayList<>();
        ArrayList<String> categoriaList = new ArrayList<>();

        galleriaDAO.getListSoggettoDAO(nomeList, categoriaList);

        for (int i = 0; i < nomeList.size(); i++) {
            listaSoggetto.add(new Soggetto(nomeList.get(i), categoriaList.get(i)));
        }
    }

    public void dumpDatiVideo() {
        GalleriaDAO galleriaDAO = new GalleriaPostgresDAO();

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
                if (usr.getUsername().equals(utenteAutoreList.get(i))) {
                    utente = usr;
                    break;
                }
            }
            Video v = new Video(idVideoList.get(i), utente, titoloList.get(i), numeroFramesList.get(i), durataList.get(i), descrizioneList.get(i));
            listaVideo.add(v);
        }
    }


//____________________________________________________________________________________________________________________//
//____________________________________________________________________________________________________________________//


//_______________________________________FUNZIONI PER UTENTE//


    /**
     * Funzione di accesso al database
     * @param username Nome utente, usato anche come PK nel DB
     * @param password Password dell'utente
     * @return Ritorna vero se le credenziali sono corrette, altrimenti ritorna falso
     * @throws SQLException
     */
    public boolean accessoUtente(String username, String password) throws SQLException{
        UtenteDAO u = new UtentePostgresDAO();

        //Modificato da boolean control = -> return u.accessoUtenteDAO
        return u.accessoUtenteDAO(username, password);

    }


    /**
     * Aggiunge un nuovo utente al sistema.
     *
     * @param username Il nome utente dell'utente.
     * @param password La password dell'utente.
     * @param admin    True se l'utente è un amministratore, False altrimenti.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void aggiungiUtente(String username, String password, boolean admin) throws SQLException {
        UtenteDAO u = new UtentePostgresDAO();

        boolean control = u.aggiungiUtenteDAO(username, password, admin);

        if (control) {
            System.out.println("Utente aggiunto con successo!");
            Utente a = new Utente(username, password, admin);
            listaUtente.add(a);
        }
    }

    /**
     * Elimina un utente dal sistema in base all'utente selezionato.
     *
     * @param utenteSelezionato Il nome utente dell'utente da eliminare.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
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
     * @param usernameSelezionato Il nome utente dell'utente da modificare.
     * @param password            La nuova password dell'utente.
     * @param admin               True se l'utente deve essere impostato come amministratore, False altrimenti.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void modificaUtente(String usernameSelezionato, String password, boolean admin) throws SQLException {

        UtenteDAO u = new UtentePostgresDAO();

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
     * @param usernameSelezionato Il nome utente dell'utente per cui si vogliono ottenere gli identificatori delle collezioni.
     * @return Un ArrayList contenente gli identificatori delle collezioni associate all'utente.
     */
    public ArrayList<Integer> VediCollezioniPerUtente(String usernameSelezionato) throws SQLException {
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
            for (Collezione col : listaCollezione) {
                for (int c : idCollezioneAssociato) {
                    if (col.getIdCollezione() == c) {
                        assert displayUsr != null;
                        displayUsr.aggiungiCollezione(col);
                    }
                }
            }
        }
        return idCollezioneAssociato;
    }


    /**
     * Ottiene una lista di identificatori di fotografie associate a un utente per visualizzazione.
     *
     * @param usernameSelezionato Il nome utente dell'utente per cui si vogliono ottenere gli identificatori delle fotografie.
     * @return Un ArrayList contenente gli identificatori delle fotografie associate all'utente.
     */
    public ArrayList<Integer> VediFotografiaPerUtente(String usernameSelezionato) throws SQLException {
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
                    if (fot.getIdFoto() == f) {
                        assert displayUsr != null;
                        displayUsr.aggiungiFotografia(fot);
                    }
        }

        return idFotoAssociato;

    }


    public ArrayList<Integer> VediFotografiaPerTagUtente(String usernameSelezionato) throws SQLException {
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
        boolean control = u.VediFotografiaPerTagUtenteDAO(usernameSelezionato, idFotoAssociato);

        //inzializzo la lista di fotografie a cui afferisce l'utente
        if (control) {
            for (Fotografia fot : listaFotografia)
                for (int f : idFotoAssociato)
                    if (fot.getIdFoto() == f) {
                        assert displayUsr != null;
                        displayUsr.aggiungiFotografia(fot);
                    }
        }

        return idFotoAssociato;

    }




    /**
     * Ottiene una lista di identificatori di fotografie associate a un utente per visualizzazione.
     *
     * @param usernameSelezionato Il nome utente dell'utente per cui si vogliono ottenere gli identificatori delle fotografie.
     * @return Un ArrayList contenente gli identificatori delle fotografie associate all'utente.
     */
    public ArrayList<Integer> VediVideoPerUtente(String usernameSelezionato) throws SQLException {
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
                    if (vid.getIdVideo() == v) {
                        assert displayUsr != null;
                        displayUsr.aggiungiVideo(vid);
                    }
        }

        return idVideoAssociato;

    }
//____________________________________________________________________________________________________________________//
//____________________________________________________________________________________________________________________//


//_______________________________________FUNZIONI PER COLLEZIONE//

    /**
     * Aggiunge una collezione al sistema con i dettagli forniti.
     *
     * @param idCollezione ID univoco della collezione da aggiungere.
     * @param username     Nome utente associato alla collezione.
     * @param titolo       Titolo della collezione da aggiungere.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void aggiungiCollezione(int idCollezione, String username, String titolo, Timestamp dataCollezione) throws SQLException {
        CollezioneDAO c = new CollezionePostgresDAO();
        int numeroElementi = 0;
        boolean control = c.aggiungiCollezioneDAO(idCollezione, username, titolo, dataCollezione);
        if (control) {

            //cerco il proprietario fra gli utenti:
            Utente proprietario = null;
            for (Utente u : listaUtente) {
                if (u.getUsername().equals(username)) {
                    proprietario = u;
                }

                Collezione collezione = new Collezione(idCollezione, proprietario, titolo, dataCollezione, numeroElementi);
                listaCollezione.add(collezione);
            }
        }
    }

    /**
     * Elimina un utente dal sistema in base all'utente selezionato.
     *
     * @param collezioneSelezionato idCollezione della collezione da eliminare.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void eliminaCollezione(int collezioneSelezionato) throws SQLException {
        CollezioneDAO c = new CollezionePostgresDAO();

        boolean control = c.eliminaCollezioneDAO(collezioneSelezionato);

        if (control) {

            //allora elimino anche dal model...
            for (Collezione col : listaCollezione) {
                if (col.getIdCollezione() == collezioneSelezionato) {
                    listaCollezione.remove(col);
                    break;
                }
            }
        }
    }

    /**
     * Aggiunge una collezione al sistema con i dettagli forniti.
     *
     * @param idCollezioneSelezionato ID univoco della collezione da aggiungere.
     * @param titolo       Titolo della collezione da aggiungere.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void modificaCollezione(int idCollezioneSelezionato, String titolo) throws SQLException {
        CollezioneDAO col = new CollezionePostgresDAO();

        boolean control = col.modificaCollezioneDAO(idCollezioneSelezionato, titolo);

        if (control) {

            //trovo il mio oggetto Collezione[...]
            Collezione CollezioneSelezionato = null;
            for (Collezione c : listaCollezione)
                if (c.getIdCollezione() == idCollezioneSelezionato)
                    CollezioneSelezionato = c;

            //a questo punto aggiorno il mio model con i nuovi dati avuti dalla gui
            CollezioneSelezionato.setTitolo(titolo);
        }
    }

    /**
     * Ottiene l'elenco di ID dei contenuti all'interno di una collezione.
     *
     * @param idCollezioneSelezionato ID univoco della collezione di cui visualizzare il contenuto.
     * @return Lista di ID dei contenuti all'interno della collezione.
     */
    public ArrayList<Integer> vediContenuto(int idCollezioneSelezionato) throws SQLException {
        CollezioneDAO c = new CollezionePostgresDAO();

        //step 1°: trovo collezione a cui si riferisce la vista
        Collezione colDaVisualizzare = null;
        for (Collezione col : listaCollezione) {
            if (col.getIdCollezione() == idCollezioneSelezionato) {
                colDaVisualizzare = col;
                break;
            }
        }

        //2 step : trovo fotografie contenute
        ArrayList<Integer> idFotoAssociate = new ArrayList<>();
        boolean control = c.vediContenutoDAO(idCollezioneSelezionato, idFotoAssociate);

        //2° step: inizializzo la lista di fotografie contenute nella collezione in questione.
        if (control) {
            for (Fotografia fot : listaFotografia) {
                for (int f : idFotoAssociate) {
                    if (fot.getIdFoto() == f) {
                        assert colDaVisualizzare != null;
                        Fotografia fotografiaDaAggiungere = fot; // Crea un riferimento all'oggetto Fotografia
                        colDaVisualizzare.aggiungiContenutoFotografia(fotografiaDaAggiungere); // Passa l'oggetto Fotografia al metodo
                    }
                }
            }
        }
        return idFotoAssociate;
    }

    /**
     * Aggiunge un contenuto a una collezione esistente.
     *
     * @param idCollezioneSelezionato ID univoco della collezione a cui aggiungere il contenuto.
     * @param idFotoSelezionata       ID univoco del contenuto (una foto) da aggiungere.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void aggiungiContenuto(int idCollezioneSelezionato, int idFotoSelezionata) throws SQLException {
        CollezioneDAO collezioneDAO = new CollezionePostgresDAO();

        boolean control = collezioneDAO.aggiungiContenutoDAO(idCollezioneSelezionato, idFotoSelezionata);

        if (control) {
            //trovo la collezione in questione nel model
            Collezione colDaVisualizzare = null;
            for (Collezione col : listaCollezione)
                if (col.getIdCollezione() == idCollezioneSelezionato) {
                    colDaVisualizzare = col;
                    break;
                }
            //trovo la fotografia in questione nel model
            Fotografia daContenere = null;
            for (Fotografia fot : listaFotografia) {
                if (fot.getIdFoto() == idFotoSelezionata) {
                    daContenere = fot;
                    break;
                }
            }

            //come ultimo step aggiungo in contenuto la fotografia
            colDaVisualizzare.aggiungiContenutoFotografia(daContenere);
            daContenere.aggiungiCollezione(colDaVisualizzare);
        }
    }

    /**
     * Rimuove un contenuto da una collezione esistente.
     *
     * @param idCollezioneSelezionato ID univoco della collezione da cui rimuovere il contenuto.
     * @param idFotoSelezionata       ID univoco del contenuto (ad esempio, una foto) da rimuovere.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void eliminaContenuto(int idCollezioneSelezionato, int idFotoSelezionata) throws SQLException {

        //come prima cosa trovo gli oggetti della collezioneSelezionata e della fotografia...
        Collezione collezioneSelezionata = null;
        for (Collezione col : listaCollezione)
            if (col.getIdCollezione() == idCollezioneSelezionato) {
                collezioneSelezionata = col;
                break;
            }

        //come seconda cosa trovo l'oggetto fotografia
        Fotografia FotografiaSelezionata = null;
        for (Fotografia fot : listaFotografia)
            if (fot.getIdFoto() == idFotoSelezionata) {
                FotografiaSelezionata = fot;
                break;
            }

        CollezioneDAO col = new CollezionePostgresDAO();

        boolean control = col.eliminaContenutoDAO(idCollezioneSelezionato, idFotoSelezionata);

        if (control) {
            //se l'eliminazione ha avuto successo allora elimino anche dal model
            assert collezioneSelezionata != null;
            collezioneSelezionata.removeContenutoCollezione(FotografiaSelezionata);

        }
    }

//____________________________________________________________________________________________________________________//
//____________________________________________________________________________________________________________________//


//_______________________________________FUNZIONI PER FOTOGRAFIA//

    /**
     * Aggiunge una nuova fotografia al sistema con i dettagli forniti.
     *
     * @param idFoto         ID univoco della fotografia da aggiungere.
     * @param usernameAutore Nome utente dell'autore della fotografia.
     * @param datiFoto       Dati binari della fotografia (ad esempio, l'immagine stessa).
     * @param dispositivo    Dispositivo con cui è stata scattata la fotografia.
     * @param luogolat       Latitudine del luogo in cui è stata scattata la fotografia.
     * @param luogolon       Longitudine del luogo in cui è stata scattata la fotografia.
     * @param condivisa      Indica se la fotografia è condivisa.
     * @param titolo         Titolo della fotografia.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void aggiungiFotografia(int idFoto, String usernameAutore, byte[] datiFoto, String dispositivo, Timestamp dataFoto, float luogolat, float luogolon, boolean condivisa, String titolo) throws SQLException {
        FotografiaDAO f = new FotografiaPostgresDAO();

        boolean control = f.aggiungiFotografiaDAO(idFoto, usernameAutore, datiFoto, dispositivo, dataFoto, luogolat, luogolon, condivisa, titolo);
        if (control) {

            //cerco il proprietario fra gli utenti:
            Utente proprietario = null;
            for (Utente u : listaUtente) {
                if (u.getUsername().equals(usernameAutore)) {
                    proprietario = u;
                    break;
                }
            }


            //cerco la latitudine fra i luoghi:
            Luogo latitudine1 = null;
            for (Luogo l1 : listaLuogo) {
                if (Float.compare(l1.getLatitudine(), luogolat) == 0) {
                    latitudine1 = l1;
                    break;
                }
            }


            //cerco la longitudine fra i luoghi:
            Luogo longitudine1 = null;
            for (Luogo l2 : listaLuogo) {
                if (Float.compare(l2.getLongitudine(), luogolon) == 0) {
                    longitudine1 = l2;
                    break;
                }
            }

            Fotografia fotografia = new Fotografia(idFoto, proprietario, datiFoto, dispositivo, dataFoto, latitudine1, longitudine1, condivisa, titolo);
            listaFotografia.add(fotografia);
        }

    }

    /**
     * Elimina una fotografia dal sistema in base all'ID fornito.
     *
     * @param idFotoSelezionata ID univoco della fotografia da eliminare.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void eliminaFotografia(int idFotoSelezionata) throws SQLException {
        FotografiaDAO f = new FotografiaPostgresDAO();

        boolean control = f.eliminaFotografiaDAO(idFotoSelezionata);

        if (control) {

            //allora elimino anche dal model...
            for (Fotografia fot : listaFotografia) {
                if (fot.getIdFoto() == idFotoSelezionata) {
                    listaFotografia.remove(fot);
                    break;
                }
            }
        }
    }

//-------------------------------------------       CONTENUTO      --------------------------------------------------//

    /**
     * Ottiene l'elenco di ID delle collezioni in cui è presente la fotografia selezionata come contenuto.
     *
     * @param idFotoSelezionata ID univoco della fotografia di cui visualizzare i contenuti.
     * @return Lista di ID delle collezioni che contengono la fotografia come contenuto.
     */
    public ArrayList<Integer> vediContenutoFotografia(int idFotoSelezionata) throws SQLException  {
        FotografiaDAO c = new FotografiaPostgresDAO();

        //step 1°: trovo foto a cui si riferisce la vista
        Fotografia fotDaVisualizzare = null;
        for (Fotografia fot : listaFotografia)
            if (fot.getIdFoto() == idFotoSelezionata) {
                fotDaVisualizzare = fot;
                break;
            }

        //2 step : trovo collezione che la contiene
        ArrayList<Integer> idCollezioneAssociato = new ArrayList<>();
        boolean control = c.vediContenutoFotografiaDAO(idFotoSelezionata, idCollezioneAssociato);

        //2° step: inizializzo la lista di collezioni con contenuto la fotografia in questione.
        if (control) {

            for (Collezione col : listaCollezione) {
                for (int c1 : idCollezioneAssociato)
                    if (col.getIdCollezione() == c1) {

                        assert fotDaVisualizzare != null;
                        fotDaVisualizzare.aggiungiCollezione(col);
                    }
            }


        }

        return idCollezioneAssociato;
    }

    /**
     * Aggiunge una fotografia come contenuto a una collezione esistente.
     *
     * @param idFotoSelezionata       ID univoco della fotografia da aggiungere come contenuto.
     * @param idCollezioneSelezionato ID univoco della collezione a cui aggiungere il contenuto.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void aggiungiContenutoFotografia(int idFotoSelezionata, int idCollezioneSelezionato) throws SQLException {
        FotografiaDAO fotografiaDAO = new FotografiaPostgresDAO();

        boolean control = fotografiaDAO.aggiungiContenutoFotografiaDAO(idFotoSelezionata, idCollezioneSelezionato);

        if (control) {
            //trovo la foto in questione nel model
            Fotografia fotDaVisualizzare = null;
            for (Fotografia fot : listaFotografia)
                if (fot.getIdFoto() == idFotoSelezionata) {
                    fotDaVisualizzare = fot;
                    break;
                }
            //trovo la collezione in questione nel model
            Collezione contenuta = null;
            for (Collezione col : listaCollezione) {
                if (col.getIdCollezione() == idCollezioneSelezionato) {
                    contenuta = col;
                    break;
                }
            }

            //come ultimo step aggiungo in contenuto la collezione
            fotDaVisualizzare.aggiungiCollezione(contenuta);
            contenuta.aggiungiContenutoFotografia(fotDaVisualizzare);
        }
    }

    /**
     * Rimuove una fotografia dal contenuto di una collezione esistente.
     *
     * @param idFotoselezionata       ID univoco della fotografia da rimuovere dal contenuto.
     * @param idCollezioneSelezionato ID univoco della collezione da cui rimuovere il contenuto.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void eliminaContenutoFotografia(int idFotoselezionata, int idCollezioneSelezionato) throws SQLException {

        //come prima cosa trovo gli oggetti della fotografiaSelezionata e della collezione...
        Fotografia fotografiaSelezionata = null;
        for (Fotografia fot : listaFotografia) {
            if (fot.getIdFoto() == idFotoselezionata) {
                fotografiaSelezionata = fot;
                break;
            }
        }

        //come seconda cosa trovo l'oggetto collezione
        Collezione CollezioneSelezionata = null;
        for (Collezione col : listaCollezione) {
            if (col.getIdCollezione() == idCollezioneSelezionato) {
                CollezioneSelezionata = col;
                break;
            }
        }

        FotografiaDAO fot = new FotografiaPostgresDAO();
        boolean control = fot.eliminaContenutoFotografiaDAO(idFotoselezionata, idCollezioneSelezionato);

        if (control) {
            //se l'eliminazione ha avuto successo allora elimino anche dal model
            assert fotografiaSelezionata != null;
            fotografiaSelezionata.removeContenutoFotografia(CollezioneSelezionata);

        }
    }
//-------------------------------------------       TAG_UTENTE     --------------------------------------------------//

    /**
     * Ottiene l'elenco dei tag utente associati a una fotografia.
     *
     * @param idFotoSelezionata ID univoco della fotografia di cui visualizzare i tag utente.
     * @return Lista di tag utente associati alla fotografia.
     */
    public ArrayList<String> vediTagUtente(int idFotoSelezionata) throws SQLException {
        FotografiaDAO c = new FotografiaPostgresDAO();

        //step 1°: trovo foto a cui si riferisce la vista
        Fotografia fotDaVisualizzare = null;
        for (Fotografia fot : listaFotografia)
            if (fot.getIdFoto() == idFotoSelezionata) {
                fotDaVisualizzare = fot;
                break;
            }

        //2 step : trovo utente che è stato taggato
        ArrayList<String> utenteAssociato = new ArrayList<>();
        boolean control = c.vediTagUtenteDAO(idFotoSelezionata, utenteAssociato);

        //2° step: inizializzo la lista di utenti taggati nella fotografia in questione.
        if (control) {

            for (Utente usr : listaUtente) {
                for (String s : utenteAssociato)
                    if (usr.getUsername().equals(s)) {

                        assert fotDaVisualizzare != null;
                        fotDaVisualizzare.aggiungiTagUtente(usr);
                    }
            }


        }

        return utenteAssociato;
    }

    /**
     * Aggiunge un tag a una fotografia per indicare l'utente selezionato.
     *
     * @param idFotoSelezionata ID univoco della fotografia a cui aggiungere il tag.
     * @param utenteSelezionato Nome utente da taggare nella fotografia.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void aggiungiTagUtente(int idFotoSelezionata, String utenteSelezionato) throws SQLException {
        FotografiaDAO fotografiaDAO = new FotografiaPostgresDAO();

        boolean control = fotografiaDAO.aggiungiTagUtenteDAO(idFotoSelezionata, utenteSelezionato);

        if (control) {
            //trovo la foto in questione nel model
            Fotografia fotoSelezionata = null;
            for (Fotografia fot : listaFotografia)
                if (fot.getIdFoto() == idFotoSelezionata) {
                    fotoSelezionata = fot;
                    break;
                }
            //trovo l'utente in questione nel model
            Utente utente = null;
            for (Utente usr : listaUtente) {
                if (usr.getUsername() == utenteSelezionato) {
                    utente = usr;
                    break;
                }
            }

            //come ultimo step aggiungo in tag_utente l'utente
            fotoSelezionata.aggiungiTagUtente(utente);
            utente.aggiungiTagUtente(fotoSelezionata);
        }
    }

    /**
     * Rimuove un tag utente da una fotografia.
     *
     * @param idFotoSelezionata ID univoco della fotografia da cui rimuovere il tag utente.
     * @param utenteSelezionato Nome utente da rimuovere come tag dalla fotografia.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void eliminaTagUtente(int idFotoSelezionata, String utenteSelezionato) throws SQLException {

        //come prima cosa trovo gli oggetti della fotografiaSelezionata e della collezione...
        Fotografia fotografiaSelezionata = null;
        for (Fotografia fot : listaFotografia)
            if (fot.getIdFoto() == idFotoSelezionata) {
                fotografiaSelezionata = fot;
                break;
            }

        //come seconda cosa trovo l'oggetto collezione
        Utente utenteselezionato = null;
        for (Utente usr : listaUtente)
            if (usr.getUsername() == utenteSelezionato) {
                utenteselezionato = usr;
                break;
            }

        FotografiaDAO fot = new FotografiaPostgresDAO();

        boolean control = fot.eliminaTagUtenteDAO(idFotoSelezionata, utenteSelezionato);

        if (control) {
            //se l'eliminazione ha avuto successo allora elimino anche dal model
            assert fotografiaSelezionata != null;
            fotografiaSelezionata.removeTagUtenteFotografia(utenteselezionato);

        }
    }

//-------------------------------------------       TAG_SOGGETTO     --------------------------------------------------//

    /**
     * Ottiene l'elenco dei tag soggetto associati a una fotografia.
     *
     * @param idFotoSelezionata ID univoco della fotografia di cui visualizzare i tag soggetto.
     * @return Lista di tag soggetto associati alla fotografia.
     */
    public ArrayList<String> vediTagSoggetto(int idFotoSelezionata) throws SQLException {
        FotografiaDAO c = new FotografiaPostgresDAO();

        //step 1°: trovo foto a cui si riferisce la vista
        Fotografia fotDaVisualizzare = null;
        for (Fotografia fot : listaFotografia)
            if (fot.getIdFoto() == idFotoSelezionata) {
                fotDaVisualizzare = fot;
                break;
            }

        //2 step : trovo soggetto che è stato taggato
        ArrayList<String> soggettoAssociato = new ArrayList<>();
        boolean control = c.vediTagSoggettoDAO(idFotoSelezionata, soggettoAssociato);

        //2° step: inizializzo la lista di utenti taggati nella fotografia in questione.
        if (control) {

            for (Soggetto sog : listaSoggetto) {
                for (String s : soggettoAssociato)
                    if (sog.getNome().equals(s)) {

                        assert fotDaVisualizzare != null;
                        fotDaVisualizzare.aggiungiTagSoggetto(sog);
                    }
            }


        }

        return soggettoAssociato;
    }

    /**
     * Aggiunge un tag a una fotografia per indicare il soggetto selezionato.
     *
     * @param idFotoSelezionata   ID univoco della fotografia a cui aggiungere il tag.
     * @param soggettoSelezionato Soggetto da aggiungere come tag alla fotografia.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void aggiungiTagSoggetto(int idFotoSelezionata, String soggettoSelezionato) throws SQLException {
        FotografiaDAO fotografiaDAO = new FotografiaPostgresDAO();

        boolean control = fotografiaDAO.aggiungiTagSoggettoDAO(idFotoSelezionata, soggettoSelezionato);

        if (control) {
            //trovo la foto in questione nel model
            Fotografia fotoSelezionata = null;
            for (Fotografia fot : listaFotografia)
                if (fot.getIdFoto() == idFotoSelezionata) {
                    fotoSelezionata = fot;
                    break;
                }
            //trovo l'utente in questione nel model
            Soggetto soggetto = null;
            for (Soggetto sog : listaSoggetto) {
                if (sog.getNome() == soggettoSelezionato) {
                    soggetto = sog;
                    break;
                }
            }

            //come ultimo step aggiungo in tag_utente l'utente
            fotoSelezionata.aggiungiTagSoggetto(soggetto);
            soggetto.aggiungiFotoSoggetto(fotoSelezionata);
        }
    }

    /**
     * Rimuove un tag da una fotografia associato a un soggetto selezionato.
     *
     * @param idFotoselezionata   ID univoco della fotografia da cui rimuovere il tag.
     * @param soggettoSelezionato Soggetto da rimuovere come tag dalla fotografia.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void eliminaTagSoggetto(int idFotoselezionata, String soggettoSelezionato) throws SQLException {

        //come prima cosa trovo gli oggetti della fotografiaSelezionata e del soggetto...
        Fotografia fotografiaSelezionata = null;
        for (Fotografia fot : listaFotografia)
            if (fot.getIdFoto() == idFotoselezionata) {
                fotografiaSelezionata = fot;
                break;
            }

        //come seconda cosa trovo l'oggetto soggetto
        Soggetto sogselezionato = null;
        for (Soggetto sog : listaSoggetto)
            if (sog.getNome().equals(soggettoSelezionato)) {
                sogselezionato = sog;
                break;
            }

        FotografiaDAO fot = new FotografiaPostgresDAO();

        boolean control = fot.eliminaTagSoggettoDAO(idFotoselezionata, soggettoSelezionato);

        if (control) {
            //se l'eliminazione ha avuto successo allora elimino anche dal model
            assert fotografiaSelezionata != null;
            fotografiaSelezionata.removeTagSoggettoFotografia(sogselezionato);

        }
    }

//____________________________________________________________________________________________________________________//
//____________________________________________________________________________________________________________________//


//_______________________________________FUNZIONI PER SOGGETTO//

    /**
     * Aggiunge un soggetto con il nome e la categoria specificati al sistema.
     *
     * @param nome      Nome del soggetto da aggiungere.
     * @param categoria Categoria del soggetto.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void aggiungiSoggetto(String nome, String categoria) throws SQLException {
        SoggettoDAO sog = new SoggettoPostgresDAO();

        boolean control = sog.aggiungiSoggettoDAO(nome, categoria);

        if (control) {
            System.out.println("Soggetto aggiunto con successo!");
            Soggetto s = new Soggetto(nome, categoria);
            listaSoggetto.add(s);
        }
    }

    /**
     * Elimina un soggetto dal sistema in base al nome selezionato.
     *
     * @param nomeSelezionato Nome del soggetto da eliminare.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void eliminaSoggetto(String nomeSelezionato) throws SQLException {
        SoggettoDAO s = new SoggettoPostgresDAO();

        boolean control = s.eliminaSoggettoDAO(nomeSelezionato);

        if (control) {
            System.out.println("Eliminazione avvenuta con successo ...!");

            //SE L'ELIMINAZIONE HA AVUTO SUCCESSO ALLORA ELIMINO ANCHE DAL MODEL IL SOGGETTO...
            for (Soggetto sog : listaSoggetto) {
                if (nomeSelezionato.equals(sog.getNome())) {
                    listaSoggetto.remove(sog);
                    break;
                }
            }
        } else {
            System.out.println("Impossibile rimuovere il soggetto...");
        }
    }

    /**
     * Modifica le informazioni di un utente esistente nel sistema.
     *
     * @param nomeSelezionato Il nome utente dell'utente da modificare.
     * @param categoria       La nuova password dell'utente.
     * @param nuovoNome       True se l'utente deve essere impostato come amministratore, False altrimenti.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void modificaSoggettoString(String nomeSelezionato, String categoria, String nuovoNome) throws SQLException {

        SoggettoDAO s = new SoggettoPostgresDAO();

        boolean control = s.modificaSoggettoDAO(nomeSelezionato, categoria, nuovoNome);


        //se modifico l'utente nel database allora lo modifico anche nel MODEL
        if (control) {
            for (Soggetto sog : listaSoggetto)
                if (sog.getNome().equals(nomeSelezionato)) {
                    sog.setCategoria(categoria);
                    sog.setNome(nuovoNome);
                    break;
                }
        }

    }

    /**
     * Modifica il nome e/o la categoria di un soggetto esistente nel sistema.
     *
     * @param nomeSelezionato Nome del soggetto da modificare.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public ArrayList<Integer> vediFotoAssociate(String nomeSelezionato) throws SQLException {
        SoggettoDAO s = new SoggettoPostgresDAO();

        //trovo soggetto a cui si riferisce la vista
        Soggetto displaySog = null;
        for (Soggetto sog : listaSoggetto)
            if (sog.getNome().equals(nomeSelezionato)) {
                displaySog = sog;
                break;
            }


        //trovo le fotografie associate
        ArrayList<Integer> fotografiaAssociato = new ArrayList<>();
        boolean control = s.vediFotoAssociateDAO(nomeSelezionato, fotografiaAssociato);

        //inzializzo la lista di fotografie a cui afferisce il soggetto
        if (control) {
            for (Fotografia fot : listaFotografia) {
                for (int f : fotografiaAssociato) {
                    if (fot.getIdFoto() == f) {
                        assert displaySog != null;
                        displaySog.aggiungiFotoSoggetto(fot);
                    }
                }

            }
        }
        return fotografiaAssociato;
    }

    //____________________________________________________________________________________________________________________//
//____________________________________________________________________________________________________________________//


//_______________________________________FUNZIONI PER LUOGO//

    /**
     * Aggiunge un luogo  al sistema.
     *
     * @param longitudine     latitudine del luogo da aggiungere.
     * @param latitudine      longitudine del luogo da aggiungere.
     * @param nome             nome del luogo.
     * @param descrizione       descrizione del luogo.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */

    public void aggiungiLuogo(Float latitudine,Float longitudine, String nome, String descrizione) throws SQLException {
        LuogoDAO sog = new LuogoPostgresDAO();

        boolean control = sog.aggiungiLuogoDAO(latitudine, longitudine, nome, descrizione);

        if (control) {
            System.out.println("Luogo aggiunto con successo!");
            Luogo l = new Luogo(latitudine, longitudine, nome, descrizione);
            listaLuogo.add(l);
        }
    }

    /**
     * Elimina un Luogo dal sistema in base all'nome fornito.
     *
     * @param LuogoSelezionato nome univoco del luogo da eliminare.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */

    public void eliminaLuogoDAO(String LuogoSelezionato) throws SQLException {

        DAO.LuogoDAO l = new LuogoPostgresDAO();

        boolean control = l.eliminaLuogoDAO(LuogoSelezionato);

        if (control) {
            System.out.println("Eliminazione avvenuta con successo ...!");

            //SE L'ELIMINAZIONE HA AVUTO SUCCESSO ALLORA ELIMINO ANCHE DAL MODEL IL SOGGETTO...
            for (Luogo luogo : listaLuogo) {
                if (LuogoSelezionato.equals(luogo.getNome())) {
                    listaLuogo.remove(luogo);
                    break;
                }
            }
        } else {
            System.out.println("Impossibile rimuovere il luogo...");
        }
    }

   /* public List<Luogo> getClassificaLuoghi() {

        List<MODEL.Luogo> classifica = new ArrayList<>();
        DAO.LuogoDAO l = new LuogoPostgresDAO();

        boolean control = l.classificaLuoghiDAO(classifica);

        if (control){
            try {
                return classifica;
            } catch (SQLException e) {
                e.printStackTrace();
                return new ArrayList<>(); // Restituisce una lista vuota in caso di errore
            }
        }

        return classifica;
    }

    */

    public void modificaLuogoDAO(String LuogoSelezionato, String descrizione, float latitudine, float longitudine) throws SQLException {
        DAO.LuogoDAO l = new LuogoPostgresDAO();

        boolean control = l.modificaLuogoDAO(LuogoSelezionato,  descrizione, latitudine, longitudine);

        if (control) {

            //trovo il mio oggetto Luogo[...]
            Luogo luogo = null;
            for (Luogo lu : listaLuogo)
                if (Objects.equals(lu.getNome(), LuogoSelezionato))
                    luogo = lu;

            //a questo punto aggiorno il mio model con i nuovi dati avuti dalla gui
            luogo.setDescrizione(descrizione);
            luogo.setLatitudine(latitudine);
            luogo.setLongitudine(longitudine);
        }
    }

    public ArrayList<Integer> vediFotoAssociateLuogo(String nomeSelezionato) throws SQLException {
        LuogoDAO s = new LuogoPostgresDAO();

        //trovo soggetto a cui si riferisce la vista
        Luogo displaySog = null;
        for (Luogo lug : listaLuogo)
            if (lug.getNome().equals(nomeSelezionato)) {
                displaySog = lug;
                break;
            }

        //trovo le fotografie associate
        ArrayList<Integer> fotografiaAssociato = new ArrayList<>();
        boolean control = s.vediFotoAssociateLuogoDAO(nomeSelezionato, fotografiaAssociato);

        //inzializzo la lista di fotografie a cui afferisce il soggetto
        if (control) {
            for (Fotografia fot : listaFotografia) {
                for (int f : fotografiaAssociato) {
                    if (fot.getIdFoto() == f) {
                        assert displaySog != null;
                        displaySog.aggiungiFotografia(fot);
                    }
                }

            }
        }
        return fotografiaAssociato;

    }


//____________________________________________________________________________________________________________________//
//____________________________________________________________________________________________________________________//


//_______________________________________FUNZIONI PER VIDEO//

    /**
     * Aggiunge un nuovo video al database.
     *
     * @param idVideo L'identificatore univoco del video da aggiungere.
     * @param autore L'autore del video.
     * @param titolo Il titolo del video.
     * @param descrizione Una breve descrizione del video.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void aggiungiVideo(int idVideo, String autore, String titolo, String descrizione) throws SQLException {
        VideoDAO v = new VideoPostgresDAO();
        int numero_frames = 0;
        int durata = 0;
        boolean control = v.aggiungiVideoDAO(idVideo, autore, titolo, descrizione);

        if (control) {
        Utente proprietario = null;
        for (Utente u : listaUtente) {
            if (u.getUsername().equals(autore)) {
                proprietario = u;
                break;
            }
        }
        Video video = new Video(idVideo, proprietario, titolo, numero_frames, durata, descrizione);
        listaVideo.add(video);

        }
    }


    /**
     * Elimina un video dal database in base all'ID del video selezionato.
     *
     * @param idVideoSelezionato L'identificatore univoco del video da eliminare.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void eliminaVideo(int idVideoSelezionato) throws SQLException{
        VideoDAO v = new VideoPostgresDAO();

        boolean control = v.eliminaVideoDAO(idVideoSelezionato);

        if (control) {
            //se L'ELIMINAZIONE HA AVUTO SUCCESSO ALLORA ELIMINO ANCHE DAL MODEL IL VIDEO...
            for (Video video : listaVideo)
                if (idVideoSelezionato == video.getIdVideo()) {
                    listaVideo.remove(video);
                    break;
                }

        }
    }


    /**
     * Modifica il titolo e/o la descrizione di un video esistente nel database in base all'ID del video selezionato.
     *
     * @param idVideoSelezionato L'identificatore univoco del video da modificare.
     * @param titolo Il nuovo titolo del video.
     * @param descrizione La nuova descrizione del video.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void modificaVideo(int idVideoSelezionato, String titolo, String descrizione) throws SQLException{
        VideoDAO vid = new VideoPostgresDAO();

        boolean control = vid.modificaVideoDAO(idVideoSelezionato,titolo,descrizione);

        if(control){
            //allora modifico anche il model...
            Video video = null;
            for(Video v : listaVideo)
                if(v.getIdVideo() == idVideoSelezionato){
                    video = v;
                }

            //trovato il video allora setto tutti gli attributi.
            assert video != null;
            video.setTitolo(titolo);
            video.setDescrizione(descrizione);

        }
    }



    /**
     * Restituisce un elenco di frame di un video specifico in base all'ID del video selezionato.
     *
     * @param idVideoSelezionato L'identificatore univoco del video di cui si vogliono visualizzare i frame.
     * @return Un ArrayList di Integer contenente l'elenco dei frame del video.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public ArrayList<Integer> vediFrameVideo(int idVideoSelezionato) throws SQLException{
        VideoDAO v = new VideoPostgresDAO();
        //come prima cosa recupero qual è il video scelto
        Video videoselezionato = null;
        for(Video vid : listaVideo){
            if(vid.getIdVideo() == idVideoSelezionato){
                videoselezionato = vid;
            }
        }


        //2 step : trovo i frame associati
        ArrayList<Integer> frameAssociati = new ArrayList<>();
        boolean control = v.vediFrameVideoDAO(idVideoSelezionato, frameAssociati);

        //2 step : inzializzo la lista di frame presenti in video
        if (control) {
            for (Frame frame : listaFrame)
                for (int f : frameAssociati)
                    if (frame.getIdFoto().equals(f)) {
                        assert videoselezionato != null;
                        videoselezionato.aggiungiFrame(frame);
                    }
        }

        return frameAssociati;


    }

//____________________________________________________________________________________________________________________//
//____________________________________________________________________________________________________________________//


//_______________________________________FUNZIONI PER FRAME//



    /**
     * Aggiunge un nuovo frame al database associato a un video specifico.
     *
     * @param idVideo L'identificatore univoco del video a cui appartiene il frame.
     * @param idFoto L'identificatore univoco del frame da aggiungere.
     * @param durata La durata del frame in millisecondi.
     * @param ordine L'ordine del frame all'interno del video.
     * @throws SQLException Eccezione sollevata in caso di problemi con il database.
     */
    public void aggiungiFrame(int idVideo, int idFoto, int durata, int ordine) throws SQLException {
        FrameDAO f = new FramePostgresDAO();

        boolean control = f.aggiungiFrameDAO(idVideo, idFoto, durata, ordine);

        if (control) {
            Fotografia foto = null;
            Video video = null;
            for (Fotografia fot : listaFotografia) {
                if (fot.getIdFoto() == idFoto) {
                    foto = fot;
                    break;
                }
            }

            for (Video vid : listaVideo) {
                if (vid.getIdVideo() == idVideo) {
                    video = vid;
                    break;
                }
            }
            Frame frame = new Frame(video, foto, durata, ordine);
            listaFrame.add(frame);

        }
    }















    //________________________________________________________________________________________________________________//
    //________________________________________________________________________________________________________________//



//___________________________________________FUNZIONI PER LA GUI__________________________________________________//

//------------------------------------------------UTENTE--------------------------------------------------------------//
    public ArrayList<String> getListaUtentiUsernameGUI() {
        ArrayList<String> stringUsername = new ArrayList<>();

        for (Utente usr : listaUtente)
            stringUsername.add(usr.getUsername());

        return stringUsername;
    }


    public ArrayList<String> getListaUtentiPasswordGUI() {
        ArrayList<String> stringPassword = new ArrayList<>();

        for (Utente usr : listaUtente)
            stringPassword.add(usr.getPassword());

        return stringPassword;
    }
    public ArrayList<Boolean> getListaUtentiAdminGUI() {
        ArrayList<Boolean> adminbool = new ArrayList<>();

        for (Utente usr : listaUtente) {
            adminbool.add(usr.isAdmin());
        }

        return adminbool;
    }





    public String getUsernameViewGUI(String usernameSelezionato){

        Utente usrSelezionato = null;
        for(Utente usr : listaUtente){
            if(usr.getUsername().equals(usernameSelezionato)){
                usrSelezionato = usr;
                break;
            }
        }
        return usrSelezionato.getUsername();
    }

    public String getUsernamePasswordViewGUI(String usernameSelezionato){

        Utente usrSelezionato = null;
        for(Utente usr : listaUtente){
            if(usr.getUsername().equals(usernameSelezionato)){
                usrSelezionato = usr;
                break;
            }
        }
        return usrSelezionato.getPassword();
    }

    public boolean getUtenteadminviewGUI(String usernameSelezionato){

        Utente usrSelezionato = null;
        for(Utente usr : listaUtente){
            if(usr.getUsername().equals(usernameSelezionato)){
                usrSelezionato = usr;
                break;
            }
        }

        return usrSelezionato.isAdmin();

    }


//------------------------------------------------COLLEZIONE------------------------------------------------------------//

    public void getListaCollezioniGUI(ArrayList<Integer> listaIdCollezione, ArrayList<String> listaUsername, ArrayList<String> listaTitolo, ArrayList<java.sql.Timestamp> listaDataCollezioni, ArrayList<Integer> listaNumeroElementi){
        for (Collezione collezione : listaCollezione) {
            listaIdCollezione.add(collezione.getIdCollezione());
            listaUsername.add(collezione.getUsername().getUsername());
            listaTitolo.add(collezione.getTitolo());
            listaDataCollezioni.add(collezione.getDataCollezione());
            listaNumeroElementi.add(collezione.getNumeroElementi());
        }
    }

    public ArrayList<String> getListaUsernameDisponibileCollezioneGUI() {
        ArrayList<String> userDisponibili = new ArrayList<>();

        for (Utente usr : listaUtente) {
            // Aggiungi tutti gli utenti presenti nella lista
            userDisponibili.add(usr.getUsername());
        }

        return userDisponibili;
    }


    public int getCollezioneIDcolViewGUI(int idCollezioneSelezionato) {
        Collezione colSelezionata = null;
        for (Collezione col : listaCollezione) {
            if (col.getIdCollezione() == idCollezioneSelezionato) {
                colSelezionata = col;
                break;
            }
        }
        if (colSelezionata != null) {
            return colSelezionata.getIdCollezione();
        } else {
            // Gestione dell'errore o valore predefinito in caso di nessuna corrispondenza trovata
            return -1; // Ad esempio, restituisco -1 in caso di nessuna corrispondenza
        }
    }
    public String getCollezioneTitoloViewGUI(int idCollezioneSelezionato) {
        Collezione colSelezionata = null;
        for (Collezione col : listaCollezione) {
            if (col.getIdCollezione() == idCollezioneSelezionato) {
                colSelezionata = col;
                break;
            }
        }
        if (colSelezionata != null) {
            return colSelezionata.getTitolo();
        } else {
            // Gestione dell'errore o valore predefinito in caso di nessuna corrispondenza trovata
            return null; // Ad esempio, restituisco null in caso di nessuna corrispondenza
        }
    }


    public String getCollezioneUsernameViewGUI(int idCollezioneSelezionato){

        Collezione colSelezionata = null;
        for(Collezione col : listaCollezione){
            if(col.getUsername().equals(idCollezioneSelezionato)){
                colSelezionata = col;
                break;
            }
        }
        return colSelezionata.getTitolo();
    }




//------------------------------------------------FOTOGRAFIA------------------------------------------------------------//

    public void getListaFotografiaGUI(ArrayList<Integer> listaIdFoto, ArrayList<String> listaAutoreFoto, ArrayList<byte[]> listaDatiFoto,ArrayList<String> listaDispositivoFoto, ArrayList<java.sql.Timestamp> listaDatafoto, ArrayList<Float> listaLatitudineFoto, ArrayList<Float> listaLongitudineFoto,ArrayList<Boolean> listaCondivisaFoto, ArrayList<String> listaTitoloFoto) {
        for (Fotografia fotografia : listaFotografia) {
            DecimalFormat decimalFormat = new DecimalFormat("#.######"); // Formatta con 6 cifre decimali

            listaIdFoto.add(fotografia.getIdFoto());
            // Verifica se l'autore non è null prima di ottenere l'username
            if (fotografia.getUsernameAutore() != null) {
                listaAutoreFoto.add(fotografia.getUsernameAutore().getUsername());
            } else {
                // Se l'autore è null, assegna una stringa vuota come autore
                listaAutoreFoto.add("Sconosciuto"); // Specifichiamo un valore predefinito come "Sconosciuto"
            }
            listaDatiFoto.add(fotografia.getDatiFoto());
            listaDispositivoFoto.add(fotografia.getDispositivo());
            listaDatafoto.add(fotografia.getDataFoto());

            // Verifica se il luogo latitudine non è null prima di ottenere la latitudine
            if (fotografia.getLuogolat() != null) {
                listaLatitudineFoto.add(Float.parseFloat(decimalFormat.format(fotografia.getLuogolat().getLatitudine())));
            } else {
                // Se la latitudine è null, assegna un valore predefinito o gestisci il caso come preferisci
                listaLatitudineFoto.add(0.0f); // Ad esempio, assegniamo 0.0 come valore predefinito
            }

            // Verifica se il luogo longitudine non è null prima di ottenere la longitudine
            if (fotografia.getLuogolon() != null) {
                listaLongitudineFoto.add(Float.parseFloat(decimalFormat.format(fotografia.getLuogolon().getLongitudine())));
            } else {
                // Se la longitudine è null, assegna un valore predefinito o gestisci il caso come preferisci
                listaLongitudineFoto.add(0.0f); // Ad esempio, assegniamo 0.0 come valore predefinito
            }

            listaCondivisaFoto.add(fotografia.isCondivisa());
            listaTitoloFoto.add(fotografia.getTitolo());
        }
    }
    public ArrayList<String> getListaLuoghiDisponibiliGUI() {
        ArrayList<String> luoghiDisponibili = new ArrayList<>();

        for (Luogo luo : listaLuogo) {
            luoghiDisponibili.add(luo.getNome());
        }
        return luoghiDisponibili;
    }


    public ArrayList<Float> getLatitudiniByNomeLuogo(String nomeLuogo) {
        ArrayList<Float> latitudini = new ArrayList<>();

        // Scorrere la lista dei luoghi e trovare quelli con il nome specificato
        for (Luogo luogo : listaLuogo) {
            if (luogo.getNome().equals(nomeLuogo)) {
                latitudini.add(luogo.getLatitudine());
            }
        }

        return latitudini;
    }

    public ArrayList<Float> getLongitudineByNomeLuogo(String nomeLuogo) {
        ArrayList<Float> Longitudini = new ArrayList<>();

        // Scorrere la lista dei luoghi e trovare quelli con il nome specificato
        for (Luogo luogo : listaLuogo) {
            if (luogo.getNome().equals(nomeLuogo)) {
                Longitudini.add(luogo.getLongitudine());
            }
        }

        return Longitudini;
    }






    public ArrayList<Integer> getListaFotografieIdFotoGUI() {
        ArrayList<Integer> intidfoto = new ArrayList<>();

        for (Fotografia fot : listaFotografia)
            intidfoto.add(fot.getIdFoto());

        return intidfoto;
    }

    public ArrayList<String> getListaFotografieAutoreGUI() {
        ArrayList<String> stringAutore = new ArrayList<>();

        for (Fotografia aut : listaFotografia)
            stringAutore.add(aut.getUsernameAutore().getUsername());

        return stringAutore;
    }

    public ArrayList<Byte> getListaFotografieDatiGUI() {
        ArrayList<Byte> bytefoto = new ArrayList<>();

        for (Fotografia foto : listaFotografia) {
            byte[] datiFoto = foto.getDatiFoto();
            for (byte b : datiFoto) {
                bytefoto.add(b);
            }
        }

        return bytefoto;
    }


    public ArrayList<String> getListaFotografieDispositivoGUI() {
        ArrayList<String> stringDispositivo = new ArrayList<>();

        for (Fotografia dis : listaFotografia)
            stringDispositivo.add(dis.getDispositivo());

        return stringDispositivo;
    }



    public ArrayList<java.sql.Timestamp> getListaFotografieDataGUI() {
        ArrayList<java.sql.Timestamp> datafoto = new ArrayList<>();

        for (Fotografia data : listaFotografia)
            datafoto.add(data.getDataFoto());

        return datafoto;
    }

    public ArrayList<Float> getListaFotografieLatitudineGUI() {
        ArrayList<Float> floatlatitudine = new ArrayList<>();

        for (Fotografia foto : listaFotografia) {
            Luogo luogo = foto.getLuogolat();
            if (luogo != null) { // Assicurati che il luogo non sia null
                floatlatitudine.add(luogo.getLatitudine());
            }
        }

        return floatlatitudine;
    }


    public ArrayList<Float> getListaFotografieLongitudineGUI() {
        ArrayList<Float> floatlongitudine = new ArrayList<>();

        for (Fotografia foto : listaFotografia) {
            Luogo luogo = foto.getLuogolon();
            if (luogo != null) { // Assicurati che il luogo non sia null
                floatlongitudine.add(luogo.getLongitudine());
            }
        }

        return floatlongitudine;
    }

    public ArrayList<Boolean> getListaFotografieCondivisaGUI() {
        ArrayList<Boolean> condivisabool = new ArrayList<>();

        for (Fotografia con : listaFotografia)
            condivisabool.add(con.isCondivisa());

        return condivisabool;
    }

    public ArrayList<String> getListaFotografieTitoloGUI() {
        ArrayList<String> stringTitolo = new ArrayList<>();

        for (Fotografia tit : listaFotografia)
            stringTitolo.add(tit.getTitolo());

        return stringTitolo;
    }



//------------------------------------------------VIDEO------------------------------------------------------------//

    public void getListaVideoGUI(ArrayList<Integer> listaIdVideo, ArrayList<String> listaAutore, ArrayList<String> listaTitolo, ArrayList<Integer> listaNumeroFrames, ArrayList<Integer> listaDurata, ArrayList<String> ListaDescrizione) {

        for (Video video : listaVideo) {
            listaIdVideo.add(video.getIdVideo());
            listaAutore.add(video.getAutore().getUsername());
            listaTitolo.add(video.getTitolo());
            listaNumeroFrames.add(video.getNumeroFrames());
            listaDurata.add(video.getDurata());
            ListaDescrizione.add(video.getDescrizione());

        }
    }


    public ArrayList<String> getListaUsernameDisponibileVideoGUI() {
        ArrayList<String> userDisponibili = new ArrayList<>();

        for (Utente usr : listaUtente) {
            // Aggiungi tutti gli utenti presenti nella lista
            userDisponibili.add(usr.getUsername());
        }

        return userDisponibili;
    }

//------------------------------------------------LUOGO------------------------------------------------------------//

    public void getListaLuogoGUI(ArrayList<Float> listaLatitudine, ArrayList<Float> listaLongitudine, ArrayList<String> listaNome, ArrayList<String> ListaDescrizione) {

        for (Luogo luogo : listaLuogo) {
            listaLatitudine.add(luogo.getLatitudine());
            listaLongitudine.add(luogo.getLongitudine());
            listaNome.add(luogo.getNome());
            ListaDescrizione.add(luogo.getDescrizione());

        }
    }

    public float getLatitudineViewGUI(String luogoSelezionato){

        Luogo lugSelezionato = null;
        for(Luogo lug : listaLuogo){
            if(lug.getNome().equals(luogoSelezionato)){
                lugSelezionato = lug;
                break;
            }
        }
        return lugSelezionato.getLatitudine();
    }

    public float getLongitudineViewGUI(String luogoSelezionato){

        Luogo lugSelezionato = null;
        for(Luogo lug : listaLuogo){
            if(lug.getNome().equals(luogoSelezionato)){
                lugSelezionato = lug;
                break;
            }
        }
        return lugSelezionato.getLongitudine();
    }

    public String  getNomeViewGUI(String luogoSelezionato){

        Luogo lugSelezionato = null;
        for(Luogo lug : listaLuogo){
            if(lug.getNome().equals(luogoSelezionato)){
                lugSelezionato = lug;
                break;
            }
        }
        return lugSelezionato.getNome();
    }

    public String  getDescrizioneViewGUI(String luogoSelezionato){

        Luogo lugSelezionato = null;
        for(Luogo lug : listaLuogo){
            if(lug.getNome().equals(luogoSelezionato)){
                lugSelezionato = lug;
                break;
            }
        }
        return lugSelezionato.getDescrizione();
    }



    //------------------------------------------------SOGGETTO------------------------------------------------------------//

    public void getListaSoggettoGUI(ArrayList<String> listaNome, ArrayList<String> listaCategoria) {

        for (Soggetto soggetto : listaSoggetto) {
            listaNome.add(soggetto.getNome());
            listaCategoria.add(soggetto.getCategoria());
        }
    }

    public String getNomeSoggettoViewGUI(String soggettoSelezionato){

        Soggetto sogSelezionato = null;
        for(Soggetto sog : listaSoggetto){
            if(sog.getNome().equals(soggettoSelezionato)){
                sogSelezionato = sog;
                break;
            }
        }
        return sogSelezionato.getNome();
    }

    public String getCategoriaSoggettoViewGUI(String soggettoSelezionato){

        Soggetto sogSelezionato = null;
        for(Soggetto sog : listaSoggetto){
            if(sog.getNome().equals(soggettoSelezionato)){
                sogSelezionato = sog;
                break;
            }
        }
        return sogSelezionato.getCategoria();
    }


}









