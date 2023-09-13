package DAO;  // Questo codice è all'interno del package "DAO".

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;


public interface LuogoDAO {

    public boolean aggiungiLuogoDAO(int idCollezione, String username, String titolo, Timestamp dataCollezione, int numeroElementi) throws SQLException;

    // Metodo per eliminare una collezione dal database.
    public boolean eliminaLuogoDAO(int collezioneSelezionato) throws SQLException;


}
