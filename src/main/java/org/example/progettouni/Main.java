package org.example.progettouni;

import org.example.progettouni.entities.User;
import org.example.progettouni.services.ApplicationService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static Connection conn;
    public static User user;
    public static void main(String[] args) throws SQLException {
        ApplicationService.start();

        user.userInfo();
    }
}