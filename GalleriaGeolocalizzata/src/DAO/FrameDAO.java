package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

// Interfaccia per definire operazioni su frame associati a video e foto
public interface FrameDAO {

    // Aggiunge un nuovo frame a un video
    public boolean aggiungiFrameDAO(int idVideo, int idFoto, int durata, int ordine) throws SQLException;
    // Elimina un frame associato a un video e una foto
    public boolean eliminaFrameDAO(int idVideoSelezionato, int idFotoSelezionata) throws SQLException;

    // Modifica un frame associato a un video e una foto
    public boolean modificaFrameDAO(int idVideoSelezionato, int idFotoSelezionata, int durata, int ordine) throws SQLException;

    // Ottiene l'elenco dei video associati a una foto specifica
    public boolean vediVideoAssociato(int idFotoSelezionata, ArrayList<Integer> idVideoAssociato) throws SQLException;
}
