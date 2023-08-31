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

        // Crea il contenitore principale con BorderLayout
        JPanel container = new JPanel(new BorderLayout());

        // Crea un pannello per le credenziali
        JPanel credentialsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Etichetta Nome Utente
        JLabel usernameLabel = new JLabel("Nome Utente:");
        constraints.gridx = 0; // Colonna 0
        constraints.gridy = 0; // Riga 0
        constraints.anchor = GridBagConstraints.WEST; // Allinea l'etichetta a sinistra
        credentialsPanel.setBackground(Color.blue);
        credentialsPanel.add(usernameLabel, constraints);

        // Testo per il Nome Utente
        JTextField usernameField = new JTextField(20);
        constraints.gridx = 1; // Colonna 1
        constraints.gridy = 0; // Riga 0
        constraints.fill = GridBagConstraints.HORIZONTAL; // Espandi orizzontalmente
        credentialsPanel.add(usernameField, constraints);

        // Etichetta Password
        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0; // Colonna 0
        constraints.gridy = 2; // Riga 1
        constraints.fill = GridBagConstraints.NONE; // Non espandere
        credentialsPanel.add(passwordLabel, constraints);

        // Campo di testo per la Password
        JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1; // Colonna 1
        constraints.gridy = 2; // Riga 1
        constraints.fill = GridBagConstraints.HORIZONTAL; // Espandi orizzontalmente
        credentialsPanel.add(passwordField, constraints);

        // Bottone Accedi
        JButton loginButton = new JButton("Accedi");
        constraints.gridx = 1; // Colonna 0
        constraints.gridy = 3; // Riga 2
        constraints.gridwidth = 2; // Occupa 2 colonne
        constraints.fill = GridBagConstraints.NONE; // Non espandere
        constraints.anchor = GridBagConstraints.CENTER; // Allinea al centro
        credentialsPanel.add(loginButton, constraints);

        // Aggiungi il pannello delle credenziali a sinistra
        container.add(credentialsPanel, BorderLayout.WEST);

        // Crea un pannello per l'immagine
        JPanel imagePanel = new JPanel();

        // Inserisce immagine nell'imagePanel
        JLabel imageLabel = new JLabel(new ImageIcon("GPGIcon.png"));

        // Modifica la posizione dell'imagePanel nella griglia
        // Crea oggetti GridBagConstraints per il posizionamento
        GridBagConstraints imageConstraints = new GridBagConstraints();

        // Imposta le nuove coordinate gridx e gridy per spostare l'immagine
        imageConstraints.gridx = 3; // Ad esempio, sposta l'immagine alla colonna 1
        imageConstraints.gridy = 3; // Ad esempio, sposta l'immagine alla riga 1
        //imageConstraints.anchor = GridBagConstraints.CENTER; // Centra l'immagine

        // Aggiorna i vincoli dell'imageLabel
        imagePanel.add(imageLabel, imageConstraints);

        // Aggiungi il pannello dell'immagine al centro
        container.add(imagePanel, BorderLayout.CENTER);

        imagePanel.add(imageLabel);

        // Aggiungi il contenitore principale alla finestra
        add(container);

        // Imposta le dimensioni minime
        setMinimumSize(new Dimension(900, 700));

        // Centra la finestra nella schermata
        setLocationRelativeTo(null);

        // Impedisce il ridimensionamento
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
