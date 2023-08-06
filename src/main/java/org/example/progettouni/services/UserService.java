package org.example.progettouni.services;

import org.example.progettouni.entities.User;

import java.sql.SQLException;
import java.util.Scanner;

public class UserService {
    public static User login() throws SQLException {
        String inputUsername, inputPassword;
        inputUsername = readUsername();
        inputPassword = readPassword();
        return ConnectionService.loginQuery(inputUsername, inputPassword);

    }

    public static String readUsername() {
        Scanner userInput = new Scanner(System.in);
        String input;
        do {
            System.out.println("\nUsername: ");
            input = userInput.nextLine();
            if (input.length() < 3 || input.isBlank()) System.out.println("\nUsername non valido!");
        } while (input.length() < 3 || input.isBlank());

        return input;
    }

    public static String readPassword() {
        Scanner userInput = new Scanner(System.in);
        String input;
        do {
            System.out.println("\nPassword: ");
            input = userInput.nextLine();
            if (input.length() < 3 || input.isBlank()) System.out.println("\nPassword non valido!");
        } while (input.length() < 3 || input.isBlank());

        return input;
    }
}
