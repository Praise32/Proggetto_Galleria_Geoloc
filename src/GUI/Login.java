import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    public Login() {
        // Imposta il titolo della finestra
        setTitle("Finestra di Login");

        // Chiudi l'applicazione quando la finestra viene chiusa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Crea il contenitore principale con BorderLayout
        JPanel container = new JPanel(new BorderLayout());

        // Crea un pannello per le credenziali
        JPanel credentialsPanel = createCredentialsPanel();

        // Aggiungi il pannello delle credenziali a sinistra
        container.add(credentialsPanel, BorderLayout.WEST);

        // Crea un pannello per l'immagine
        JPanel imagePanel = createImagePanel();

        // Aggiungi il pannello dell'immagine al centro
        container.add(imagePanel, BorderLayout.CENTER);

        // Aggiungi il contenitore principale alla finestra
        add(container);

        // Imposta le dimensioni minime
        setMinimumSize(new Dimension(900, 700));

        // Centra la finestra nella schermata
        setLocationRelativeTo(null);

        // Impedisce il ridimensionamento
        setResizable(false);
    }

    private JPanel createCredentialsPanel() {
        JPanel credentialsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Etichetta Nome Utente
        JLabel usernameLabel = new JLabel("Nome Utente:");
        addComponent(credentialsPanel, usernameLabel, constraints, 0, 0, 1, GridBagConstraints.WEST);

        // Campo di testo per il Nome Utente
        JTextField usernameField = new JTextField(20);
        addComponent(credentialsPanel, usernameField, constraints, 1, 0,1, GridBagConstraints.HORIZONTAL); //TODO FIXARE HORIZONTAL FA CRASHARE TUTTO

        // Etichetta Password
        JLabel passwordLabel = new JLabel("Password:");
        addComponent(credentialsPanel, passwordLabel, constraints, 0, 1,1, GridBagConstraints.NONE);

        // Campo di testo per la Password
        JPasswordField passwordField = new JPasswordField(20);
        addComponent(credentialsPanel, passwordField, constraints, 1, 1,1, GridBagConstraints.HORIZONTAL); //TODO IDEM

        // Bottone Accedi
        JButton loginButton = new JButton("Accedi");
        addComponent(credentialsPanel, loginButton, constraints, 0, 2, 2, GridBagConstraints.CENTER);

        return credentialsPanel;
    }

    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel(new GridBagLayout());

        // Inserisce immagine nell'imagePanel
        JLabel imageLabel = new JLabel(new ImageIcon("GPGIcon.png"));

        // Aggiorna i vincoli dell'imageLabel per posizionarla al centro
        GridBagConstraints imageConstraints = new GridBagConstraints();
        imageConstraints.gridx = 0;
        imageConstraints.gridy = 2;
        imageConstraints.anchor = GridBagConstraints.CENTER;

        imagePanel.add(imageLabel, imageConstraints);

        return imagePanel;
    }

    private void addComponent(JPanel panel, JComponent component, GridBagConstraints constraints, int gridx, int gridy, int gridwidth, int anchor) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.anchor = anchor;
        panel.add(component, constraints);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login frame = new Login();
            frame.setVisible(true);
        });
    }
}
