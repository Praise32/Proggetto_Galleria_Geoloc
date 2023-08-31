import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        // Imposta il titolo della finestra
        setTitle("Finestra di Login");

        // Chiudi l'applicazione quando la finestra viene chiusa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crea un pannello con GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());

        // Crea un oggetto GridBagConstraints per configurare la posizione dei componenti
        GridBagConstraints constraints = new GridBagConstraints();

        // Etichetta Nome Utente
        JLabel usernameLabel = new JLabel("Nome Utente:");
        constraints.gridx = 0; // Colonna 0
        constraints.gridy = 0; // Riga 0
        constraints.anchor = GridBagConstraints.WEST; // Allinea l'etichetta a sinistra
        panel.add(usernameLabel, constraints);

        // Campo di testo per il Nome Utente
        JTextField usernameField = new JTextField(20);
        constraints.gridx = 1; // Colonna 1
        constraints.gridy = 0; // Riga 0
        constraints.fill = GridBagConstraints.HORIZONTAL; // Espandi orizzontalmente
        panel.add(usernameField, constraints);

        // Etichetta Password
        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0; // Colonna 0
        constraints.gridy = 1; // Riga 1
        constraints.fill = GridBagConstraints.NONE; // Non espandere
        panel.add(passwordLabel, constraints);

        // Campo di testo per la Password
        JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1; // Colonna 1
        constraints.gridy = 1; // Riga 1
        constraints.fill = GridBagConstraints.HORIZONTAL; // Espandi orizzontalmente
        panel.add(passwordField, constraints);

        // Bottone Accedi
        JButton loginButton = new JButton("Accedi");
        constraints.gridx = 0; // Colonna 0
        constraints.gridy = 2; // Riga 2
        constraints.gridwidth = 2; // Occupa 2 colonne
        constraints.fill = GridBagConstraints.NONE; // Non espandere
        constraints.anchor = GridBagConstraints.CENTER; // Allinea al centro
        panel.add(loginButton, constraints);

        // Azione del bottone Accedi
        //FUNZIONE LAMBDA DA IMPLEMENTARE, QUELLA CLASSICA VEDI SUCCESSIVA
        loginButton.addActionListener(e -> {
            // Aggiungi il codice per il login qui
            // Puoi accedere ai campi di testo con usernameField.getText() e passwordField.getPassword()
        });

        /*
         *  loginButton.addActionListener(new ActionListener() {
         *             public void actionPerformed(ActionEvent e) {
         *                 // Aggiungi il codice per il login qui
         *                 // Puoi accedere ai campi di testo con usernameField.getText() e passwordField.getPassword()
         *             }
         *         });
         */

        // Aggiungi il pannello al frame
        add(panel);

        // Imposta le dimensioni minime
        setMinimumSize(new Dimension(300, 150));

        // Centra la finestra nella schermata
        setLocationRelativeTo(null);

        this.setSize(1000,700);
        this.setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
