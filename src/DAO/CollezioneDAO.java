package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimeStamp;

public interface CollezioneDAO {







    public boolean aggiungiCollezioneDAO(int idCollezione, String username, String titolo, Timestamp dataCollezione, int numeroElementi) throws SQLException;


    public boolean eliminaCollezioneDAO(int collezioneSelezionato) throws SQLException;

    public boolean aggiungiContenutoDAO(int idCollezioneSelezionato, ArrayString<int> idFotoSelezionata) throws SQLException;

    public boolean eliminaContenutoDAO(int idCollezioneSelezionato, ArrayString<int> idFotoSelezionata) throws SQLException;

    public boolean vediContenutoDAO(int idCollezioneSelezionato, ArrayString<int> idFotoAssociate) throws SQLException;


    public boolean modificaCollezioneDAO(int idCollezione, String username, String titolo, Timestamp dataCollezione, int numeroElementi) throws SQLException;
}
