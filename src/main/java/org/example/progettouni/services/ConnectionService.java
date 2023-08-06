package org.example.progettouni.services;

import org.example.progettouni.Main;
import org.example.progettouni.entities.User;

import java.sql.*;

public class ConnectionService {
    public static Connection connection() {
        Connection conn;
        try {
            System.out.println("CONNECTION . . . ");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gpgdb", "postgres", "Francesino3102");
            if (!conn.isClosed()) System.out.print("OK");

            return conn;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static User loginQuery(String username, String password) throws SQLException {
        String query = "SELECT * FROM utente WHERE username = ? AND password = ?";
        String readUsername = "", readPassword = "";
        boolean readFlag = false;

        PreparedStatement ps = Main.conn.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            readUsername = rs.getString(1);
            readPassword = rs.getString(2);
            readFlag = rs.getBoolean(3);
        }
        rs.close();
        ps.close();
        return new User(readUsername, readPassword, readFlag);

    }
}

