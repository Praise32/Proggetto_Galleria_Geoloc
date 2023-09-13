package GUI;

import CONTROLLER.*;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The type Inserimento Utente gui.
 */
public class InserimentoUtenteGUI extends JDialog {
    private final JTextField usernameField;
    private final JTextField passwordField;

    private final JCheckBox adminCheckBox;

    /**
     * Instantiates a new Inserimento impiegato gui.
     *
     * @param controller the controller
     * @param framePadre the frame padre
     */
    public InserimentoUtenteGUI(Controller controller, JFrame framePadre) {
//----------------------------------------------PANNELLO CAMPI-------------------------------------------------------//

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 10, 100));
        setTitle("Inserimento Utente");

        // CAMPO USERNAME
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);

        // CAMPO PASSWORD
        inputPanel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        inputPanel.add(passwordField);


        // CHECKBOX ADMIN
        adminCheckBox = new JCheckBox("Admin");
        inputPanel.add(adminCheckBox);


//----------------------------------------------BOTTONI---------------------------------------------------------------//


        // BOTTONE SALVA
        JButton bottoneSalva = new JButton("Salva");
        bottoneSalva.addActionListener(e -> {
            setVisible(false);

            //controllo che non ci siano dei campi vuoti
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Servizi di admin è opzionale, inserisci il resto dei dati per continuare", "Attenzione", JOptionPane.WARNING_MESSAGE);
                setVisible(true);
            } else {
                //Tutti i campi sono stati inseriti
                String username = usernameField.getText();
                String password = passwordField.getText();
                boolean admin = adminCheckBox.isSelected();

                try {
                    controller.aggiungiUtente(username, password, admin);
                    JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);
                } catch (PSQLException ex) {
                    JOptionPane.showMessageDialog(null, "Errore durante il salvataggio dei dati dell'impiegato:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                } finally {
                    dispose();
                    framePadre.setVisible(true);
                }
            }
        });


        // BOTTONE ANNULLA
        JButton bottoneAnnulla = new JButton("Annulla");
        bottoneAnnulla.addActionListener(e -> {
            dispose();
            framePadre.setVisible(true);
        });


        // Creiamo un pannello per contenere i bottoni "Salva" e "Annulla"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(bottoneSalva);
        buttonPanel.add(bottoneAnnulla);


        // Aggiungiamo i pannelli alla finestra
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Impostiamo le dimensioni della finestra
        setSize(800, 600);
        setLocationRelativeTo(null);

        //disattivo la finestra padre
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //listener per mostrare la finestra padre quando viene chiusa la finestra di dialogo
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                framePadre.setVisible(true);
            }
        });
        //mostriamo la finestra
        setVisible(true);
    }

}
