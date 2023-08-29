package Basi;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Finestra di Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 righe, 2 colonne, spaziatura 10px

        JLabel usernameLabel = new JLabel("Nome Utente:");
        JLabel passwordLabel = new JLabel("Password:");

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        // Imposta la dimensione massima per il campo di testo
        Dimension maxFieldSize = new Dimension(100, 10); // Imposta le dimensioni massime desiderate (larghezza x altezza)
        usernameField.setMaximumSize(maxFieldSize);
        passwordField.setMaximumSize(maxFieldSize);

        JButton loginButton = new JButton("Accedi");
        JButton cancelButton = new JButton("Annulla");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(cancelButton);
        add(panel);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
