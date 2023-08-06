package org.example.progettouni.services;

import org.example.progettouni.Main;

import java.sql.SQLException;

/**
 * Classe utilizzata per far partire l'applicativo
 */
public class ApplicationService {
    public static void start() throws SQLException {
        Main.conn = ConnectionService.connection(); //Stabilisce la connessione al server

        do {
            Main.user = UserService.login();
            if(Main.user.getUsername().isEmpty() && !Main.user.getAdmin()) System.out.println("Credenziali errate");

        } while (Main.user.getUsername().isEmpty() && !Main.user.getAdmin());
    }
}
