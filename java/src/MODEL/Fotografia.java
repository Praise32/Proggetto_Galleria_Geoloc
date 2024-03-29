package MODEL;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class Fotografia {
    private int idFoto;
    private Utente usernameAutore;
    private byte[] datiFoto;
    private String dispositivo;
    private Timestamp dataFoto;
    private Luogo luogolat;
    private Luogo luogolon;
    private boolean condivisa;
    private String titolo;

    //il seguente attributo considera una lista di collezioni in cui è contenuta una fotografia
    private List<Collezione> listaContenutoFotografia = new ArrayList<>();
    //il seguente attributo considera una lista di frame associati ad una fotografia
    private List<Frame> listaFrameAssociati = new ArrayList<>();

    //il seguente attributo considera una lista di utenti taggati una fotografia
    private List<Utente> listaTagUtenteFotografia = new ArrayList<>();

    //il seguente attributo considera una lista di utenti taggati una fotografia
    private List<Soggetto> listaTagSoggetto = new ArrayList<>();

    public Fotografia(int idFoto, Utente usernameAutore, byte[] datiFoto, String dispositivo, Timestamp dataFoto,
                      Luogo luogolat, Luogo luogolon, boolean condivisa, String titolo) {
        this.idFoto = idFoto;
        this.usernameAutore = usernameAutore;
        this.datiFoto = datiFoto;
        this.dispositivo = dispositivo;
        this.dataFoto = dataFoto;
        this.luogolat = luogolat;
        this.luogolon = luogolon;
        this.condivisa = condivisa;
        this.titolo = titolo;
    }

    public void aggiungiCollezione(Collezione col) {
        this.listaContenutoFotografia.add(col);
    }

    public void aggiungiTagUtente(Utente usr) {this.listaTagUtenteFotografia.add(usr);}

    public void aggiungiTagSoggetto(Soggetto sog) {this.listaTagSoggetto.add(sog);}

    public void aggiungiFrame(Frame frame) {this.listaFrameAssociati.add(frame);}

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public Utente getUsernameAutore() {
        return usernameAutore;
    }

    public void setUsernameAutore(Utente usernameAutore){this.usernameAutore = usernameAutore;}

    public Luogo getLuogolat() {
        return luogolat;
    }

    public Luogo getLuogolon() {
        return luogolon;
    }


    public void setLuogolat(Luogo luogolat){this.luogolat = luogolat;}

    public void setLuogolon(Luogo luogolon){this.luogolon = luogolon;}

    public byte[] getDatiFoto() {
        return datiFoto;
    }

    public void setDatiFoto(byte[] datiFoto) {
        this.datiFoto = datiFoto;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Timestamp getDataFoto() {
        return dataFoto;
    }

    public void setDataFoto(Timestamp dataFoto) {
        this.dataFoto = dataFoto;
    }



    public boolean isCondivisa() {
        return condivisa;
    }

    public void setCondivisa(boolean condivisa) {
        this.condivisa = condivisa;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public List<Collezione> getListaContenutoFotografia() {
        return listaContenutoFotografia;
    }

    public void setListaContenutoFotografia(List<Collezione> listaContenutoFotografia) {
        this.listaContenutoFotografia = listaContenutoFotografia;
    }

    public List<Utente> getListaTagUtenteFotografia() {
        return listaTagUtenteFotografia;
    }

    public void setListaTagUtenteFotografia(List<Utente> listaTagUtenteFotografia) {
        this.listaTagUtenteFotografia = listaTagUtenteFotografia;
    }

    public List<Soggetto> getListaTagSoggetto() {
        return listaTagSoggetto;
    }

    public void setListaTagSoggetto(List<Soggetto> listaTagSoggetto) {
        this.listaTagSoggetto = listaTagSoggetto;
    }

    public List<Frame> getListaFrameAssociati() {
        return listaFrameAssociati;
    }

    public void setListaFrameAssociati(List<Frame> listaFrameAssociati) {
        this.listaFrameAssociati = listaFrameAssociati;
    }

    public void removeContenutoFotografia (Collezione col) {
        this.listaContenutoFotografia.remove(col);
    }

    public void removeTagSoggettoFotografia (Soggetto sog) {
        this.listaTagSoggetto.remove(sog);
    }

    public void removeTagUtenteFotografia (Utente usr) {
        this.listaTagUtenteFotografia.remove(usr);
    }

    public void removeFrameFotografia (Frame fra) {
        this.listaFrameAssociati.remove(fra);
    }


}
