package org.example.progettouni.entities;

//Classe per istanziare utenti nel sistema
public class User {
    //Variabili
    private String username, password;    //Nome utente
    private boolean admin;      //Flag permessi di admin nel db

    /**
     * Costruttore usato dopo aver ricevuto i dati dal database
     * @param username USERNAME - PK
     * @param password PASSWORD
     * @param admin    FLAG ADMIN
     */
    public User(String username, String password, boolean admin){
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String getUsername(){
        return username;
    }
    public String getPassword() {
        return password;
    }
    public boolean getAdmin(){
        return admin;
    }

    public void userInfo(){
        System.out.println("Username: " + username + "\nAdmin: " + admin);
    }
}

