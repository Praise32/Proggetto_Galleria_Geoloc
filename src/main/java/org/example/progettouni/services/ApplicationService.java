package org.example.progettouni.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilizzata per far partire l'applicativo
 */
public class ApplicationService {
    public static void start() {
        Connection conn = null;
        try {
            //Creo la connessione salvando le informazione nell'oggetto conn
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gpgdb", "ggsolaire", "Francesino3102");
            if (conn != null) System.out.println("\nConnessione eseguita...");
        } catch (SQLException e) {
            System.out.println(e.getMessage()); //Stampa una sola riga specifica in base all'errore riscontrato
        } finally {
            try {
                conn.close();
                if (conn != null) {
                    if (!conn.isClosed()) conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
