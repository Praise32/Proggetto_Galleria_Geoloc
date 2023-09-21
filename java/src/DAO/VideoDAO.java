package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

// Interfaccia per definire operazioni su video
public interface VideoDAO {

    // Aggiunge un nuovo video con le informazioni specificate
    public boolean aggiungiVideoDAO(int idVideo, String autore, String titolo,  String descrizione) throws SQLException;
    // Elimina un video in base all'ID specificato
    boolean eliminaVideoDAO(int idVideoSelezionato) throws SQLException;

    // Modifica le informazioni di un video in base all'ID specificato
    boolean modificaVideoDAO(int idVideoSelezionato, String titolo, String descrizione) throws SQLException;

    // Ottiene l'elenco dei frame associati a un video specifico
    boolean vediFrameVideoDAO(int idVideoSelezionato, ArrayList<Integer> frameAssociati) throws SQLException;
}
