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

        // COMMENTARE PER RIMUOVERE LA GRIGLIA DI DEBUG GUI
        showGrill(credentialsPanel);
        showGrill(imagePanel);


    }

    private JPanel createCredentialsPanel() {
        JPanel credentialsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Etichetta Nome Utente
        JLabel usernameLabel = new JLabel("Nome Utente:");
        addComponent(credentialsPanel, usernameLabel, constraints, 0, 0, 1, GridBagConstraints.WEST);


        // Campo di testo per il Nome Utente
        JTextField usernameField = new JTextField(15);  //Indica il numero di colonne da occupare della cella
        addComponent(credentialsPanel, usernameField, constraints, 1, 0,1, GridBagConstraints.CENTER);

        // Etichetta Password
        JLabel passwordLabel = new JLabel("Password:");
        addComponent(credentialsPanel, passwordLabel, constraints, 0, 1,1, GridBagConstraints.CENTER);   //NONE NON PARTE

        // Campo di testo per la Password
        JPasswordField passwordField = new JPasswordField(15);
        addComponent(credentialsPanel, passwordField, constraints, 1, 1,1, GridBagConstraints.CENTER);

        // Bottone Accedi
        JButton loginButton = new JButton("Accedi");
        addComponent(credentialsPanel, loginButton, constraints, 0, 2, 2, GridBagConstraints.CENTER);

        return credentialsPanel;
    }

    /**
     * Funzione per creare un pannello che contenga un immagine
     * Usato solo nel login, tuttavia il pannello è modificabile una volta creato, come un qualsiasi componente
     * Può quindi essere riutilizzato se necessario
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

    /**
     * Crea un effetto a griglia in tutto il frame.
     * Usata in fase di debug per controllare le misure dei componenti, senza dover inserire una linea o background manualmente per ogni componente
     * Rosso i componenti, verde il parente a cui appartengono
     * @param parent Componente "parente", ovvero quella contenente le altre componenti, come i panel
     */

    void showGrill(Container parent){
        for (Component c : parent.getComponents()){
            if(c instanceof JComponent) ((JComponent) c).setBorder(BorderFactory.createLineBorder(Color.red, 2));
        }

        if(parent instanceof JPanel) ((JPanel) parent).setBorder(BorderFactory.createLineBorder(Color.green, 2));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login frame = new Login();
            frame.setVisible(true);
        });
    }

}


