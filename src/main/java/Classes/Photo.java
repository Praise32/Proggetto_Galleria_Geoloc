package Classes;

public class Photo {

    /*
    In ordine:
    ID della foto
    ID dell'utente che l'ha scattata, presa da un oggetto di classe User
    ID luogo in cui è stata scattata, presa da oggetto di classe Luogo
    ID dei soggetti della foto, presi da oggetti di classe Soggetto
    Stringa contenente il modello del dispositivo con cui è stata scattata
    Flag di controllo per l'accesso dalla galleria
     */
    int id_photo, id_user, id_location, id_tags;
    String device;
    boolean accessible;

    Photo(int id_photo, int id_user, int id_location, int id_tags, String device, boolean accessible) {
        this.id_photo = id_photo;
        this.id_user = id_user;
        this.id_location = id_location;
        this.id_tags = id_tags;
        this.device = device;
        this.accessible = accessible;
    }

}
