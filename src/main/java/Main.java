import Classes.*;

import java.sql.*;  //Libreria per implementare sql

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;

        try {
            //Creo la connessione salvando le informazione nell'oggetto conn
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gpgdb", "ggsolaire", "Francesino3102");
            if (conn != null) System.out.println("\nConnessione eseguita: ");

            //Preparo lo statement per eseguire la query
            stm = conn.createStatement();

            //Eseguo la query
            rs = stm.executeQuery("SELECT * FROM utente");

            while (rs.next()) {
                System.out.printf("\n\nID %d\nNome %s\nAdmin %b", rs.getInt("id_utente"), rs.getString("username"), rs.getBoolean("admin"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //Chiusura connessione
            try {
                if (!rs.isClosed()) rs.close();
                if (!stm.isClosed()) stm.close();
                if (!conn.isClosed()) conn.close();
                System.out.println("\nConnessione terminata.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}