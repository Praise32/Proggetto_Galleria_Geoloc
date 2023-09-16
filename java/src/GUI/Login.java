package GUI;

import CONTROLLER.Controller;
import ImplementazionePostgresDAO.UtentePostgresDAO;
import MAIN.Main;
import MAIN.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * The type Menu principale gui.
 */
public class Login {
    /**
     * The Controller.
     */
    Controller controller = new Controller();

    private final JFrame frame;

    /**
     * Instantiates a new Menu principale gui.
     */
    public Login() {
        frame = new JFrame("Finestra Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Imposta il titolo della finestra
        frame.setTitle("Finestra di Login");

        // Chiudi l'applicazione quando la finestra viene chiusa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


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
        frame.add(container);

        // Imposta le dimensioni minime
        frame.setMinimumSize(new Dimension(900, 700));

        // Centra la finestra nella schermata
        frame.setLocationRelativeTo(null);

        // Impedisce il ridimensionamento
        frame.setResizable(false);

        frame.setVisible(true);

        // Recupera il default button da credentialsPanel, in questo caso il pulsante Accedi
        // setDefaultButton imposta il pulsante di default con keyListener sul pulsante Invio, senza dover creare Override appositi
        frame.getRootPane().setDefaultButton(credentialsPanel.getRootPane().getDefaultButton());

        frame.requestFocusInWindow();


    }

    private JPanel createCredentialsPanel() {
        JPanel credentialsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Etichetta Nome Utente
        JLabel usernameLabel = new JLabel("Nome Utente:");
        addComponent(credentialsPanel, usernameLabel, constraints, 0, 0, 1, GridBagConstraints.WEST);

        // Campo di testo per il Nome Utente
        JTextField usernameField = new JTextField(15);  //Indica il numero di colonne da occupare della cella
        usernameField.setText("Username");

        //Aggiunge o toglie la parola all'interno dei texfield
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //Se nella casella c'è scritto il testo di default allora svuota la casella al focus, altrimenti lascia l'input utente
                if(usernameField.getText().equals("Username")) usernameField.setText(null);
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isBlank()) usernameField.setText("Username");
            }

        });
        addComponent(credentialsPanel, usernameField, constraints, 1, 0, 1, GridBagConstraints.CENTER);

        // Etichetta Password
        JLabel passwordLabel = new JLabel("Password:");
        addComponent(credentialsPanel, passwordLabel, constraints, 0, 1, 1, GridBagConstraints.CENTER);   //NONE NON PARTE

        // Campo di testo per la Password
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setText(null); // Empty the text field when it receives focus
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isBlank()) usernameField.setText("Password");
            }

        });
        addComponent(credentialsPanel, passwordField, constraints, 1, 1, 1, GridBagConstraints.CENTER);

        // Bottone Accedi
        JButton loginButton = new JButton("Accedi");

        /**
         * Funzione di accesso collegata al pulsante
         */
        loginButton.addActionListener(e -> {
            boolean accesso = false;

            try {
                accesso = controller.accessoUtente(usernameField.getText(), passwordField.getText());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            if (accesso){

                frame.dispose();
                //Apro il menu principale
                GUI.MenuPrincipaleGUI menuPrincipaleGUI = new GUI.MenuPrincipaleGUI(controller, frame);

            }else{
                JOptionPane.showMessageDialog(null, "Credenziali errate!\n", "Accesso non riuscito.", JOptionPane.INFORMATION_MESSAGE);
            }

        });
        //loginButton.addKeyListener(); TODO Controllare come aggiungere un KeyListener sul tasto invio per accedere
        addComponent(credentialsPanel, loginButton, constraints, 0, 2, 2, GridBagConstraints.CENTER);

        frame.getRootPane().setDefaultButton(loginButton);

        return credentialsPanel;
    }


    /**
     * Funzione per creare un pannello che contenga un immagine
     * Usato solo nel login, tuttavia il pannello è modificabile una volta creato, come un qualsiasi componente
     * Può quindi essere riutilizzato se necessario
     *
     * @return Pannello creato contenente l'immagine del logo
     */
    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel(new GridBagLayout());

        // Inserisce immagine nell'imagePanel
        JLabel imageLabel = new JLabel(new ImageIcon("Logo.png"));

        // Aggiorna i vincoli dell'imageLabel per posizionarla al centro
        GridBagConstraints imageConstraints = new GridBagConstraints();
        imageConstraints.gridx = 0;
        imageConstraints.gridy = 2;
        imageConstraints.anchor = GridBagConstraints.CENTER;

        imagePanel.add(imageLabel, imageConstraints);

        return imagePanel;
    }

    //TODO Capire perché il programma non parte quando anchor = NONE o HORIZONTAL

    /**
     * Funzione che permette di designare delle costraints per un JComponent, per poi inserirlo in un panel
     *
     * @param panel       Pannello a cui si inserisce il componente
     * @param component   Componente inserito nel JPanel panel
     * @param constraints Constraints del componente inserito
     * @param gridx       Posizione nella cella di riga X della griglia
     * @param gridy       Posizione nella cella di colonna Y della griglia
     * @param gridwidth   Quante celle della griglia usa
     * @param anchor      Da GridBagConstraints.java:
     *                    This field is used when the component is smaller than its display area.
     *                    It determines where, within the display area, to place the component
     */
    private void addComponent(JPanel panel, JComponent component, GridBagConstraints constraints, int gridx, int gridy, int gridwidth, int anchor) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.anchor = anchor;
        panel.add(component, constraints);
    }

    /**
     * Crea un effetto a griglia in tutto il frame.
     * Usata in fase di debug per controllare le misure dei componenti, senza dover inserire una linea o background manualmente per ogni componente
     * Rosso i componenti, verde il parente a cui appartengono
     *
     * @param parent Componente "parente", ovvero quella contenente le altre componenti, come i panel
     */
    void showGrill(Container parent) {
        for (Component c : parent.getComponents()) {
            if (c instanceof JComponent) ((JComponent) c).setBorder(BorderFactory.createLineBorder(Color.red, 2));
        }

        if (parent instanceof JPanel) ((JPanel) parent).setBorder(BorderFactory.createLineBorder(Color.green, 2));
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        new Login();
    }


}
