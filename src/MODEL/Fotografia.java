package MODEL;

import java.sql.Timestamp;

public class Fotografia {
    private int idFoto;
    private Utente usernameAutore;
    private byte[] datiFoto;
    private String dispositivo;
    private Timestamp dataFoto;
    private Luogo latitudine;
    private Luogo longitudine;
    private boolean condivisa;
    private String titolo;

    public Fotografia() {
    }

    public Fotografia(int idFoto, Utente usernameAutore, byte[] datiFoto, String dispositivo, Timestamp dataFoto,
                      Luogo latitudine, Luogo longitudine, boolean condivisa, String titolo) {
        this.idFoto = idFoto;
        this.usernameAutore = usernameAutore;
        this.datiFoto = datiFoto;
        this.dispositivo = dispositivo;
        this.dataFoto = dataFoto;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.condivisa = condivisa;
        this.titolo = titolo;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public Utente getUsernameAutore() {
        return usernameAutore;
    }

    public void setUsernameAutore(Utente usernameAutore) {
        this.usernameAutore = usernameAutore;
    }

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

    public Luogo getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(Luogo latitudine) {
        this.latitudine = latitudine;
    }

    public Luogo getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(Luogo longitudine) {
        this.longitudine = longitudine;
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
}
