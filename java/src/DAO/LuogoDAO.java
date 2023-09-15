package DAO;  // Questo codice è all'interno del package "DAO".

import MODEL.Luogo;

import java.sql.SQLException;
import java.util.List;


public interface LuogoDAO {

    // Metodo per aggiungere un Luogo dal database.
    public boolean aggiungiLuogoDAO(float latitudine, float longitudine, String nome, String descrizione) throws SQLException;

    // Metodo per eliminare un Luogo dal database.
    public boolean eliminaLuogoDAO(String LuogoSelezionato) throws SQLException;

    //Metodo per la Classifica dei 3 luoghi più immortalati
    public boolean classificaLuoghiDAO(List<Luogo> classifica) throws SQLException;

    public boolean modificaLuogoDAO(String LuogoSelezionato, String descrizione, float latitudine, float longitudine) throws SQLException;

}
