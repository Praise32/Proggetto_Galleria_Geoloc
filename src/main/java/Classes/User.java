package Classes;

//Classe per istanziare utenti nel sistema
public class User {
    //Variabili
    int id_user;      //ID Utente
    String username;    //Nome utente
    boolean admin;      //Flag permessi di admin nel db

    /**
     * Costruttore usato dopo aver ricevuto i dati dal database
     * @param id_user
     * @param username
     * @param admin
     */
    User(int id_user, String username, boolean admin){
        this.id_user = id_user;
        this.username = username;
        this.admin = admin;
    }
}

