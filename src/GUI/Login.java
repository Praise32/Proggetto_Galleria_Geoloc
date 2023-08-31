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
        //addComponent(credentialsPanel, usernameLabel, constraints, 0, 0, 1, GridBagConstraints.WEST);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        credentialsPanel.add(usernameLabel, constraints);

        // Campo di testo per il Nome Utente
        JTextField usernameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        credentialsPanel.add(usernameField, constraints);
        //addComponent(credentialsPanel, usernameField, constraints, 1, 0,1, GridBagConstraints.CENTER);

        // Etichetta Password
        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        credentialsPanel.add(passwordLabel, constraints);
      //addComponent(credentialsPanel, passwordLabel, constraints, 0, 1,1, GridBagConstraints.CENTER);   //NONE NON PARTE

        // Campo di testo per la Password
        JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        credentialsPanel.add(passwordField, constraints);
      // addComponent(credentialsPanel, passwordField, constraints, 1, 1,1, GridBagConstraints.CENTER);

        // Bottone Accedi
        JButton loginButton = new JButton("Accedi");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        credentialsPanel.add(loginButton, constraints);
        //addComponent(credentialsPanel, loginButton, constraints, 0, 2, 2, GridBagConstraints.CENTER);

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

    //TODO Vedere perché questa funzione non fa avviare il programma per via di int anchor = NONE oppure HORIZONTAL

    /**
     * Funzione che permette di designare delle costraints per un JComponent, per poi inserirlo in un panel
     * @param panel Pannello a cui si inserisce il componente
     * @param component Componente inserito nel JPanel panel
     * @param constraints Constraints del componente inserito
     * @param gridx Posizione nella cella di riga X della griglia
     * @param gridy Posizione nella cella di colonna Y della griglia
     * @param gridwidth Quante celle della griglia usa
     * @param anchor    Da GridBagConstraints.java:
     *                  This field is used when the component is smaller than its display area.
     *                  It determines where, within the display area, to place the component
     */
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
