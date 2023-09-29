package GUI;

import CONTROLLER.*;
import org.postgresql.util.PSQLException;

import javax.swing.*;

import org.jdesktop.swingx.JXDatePicker;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * The type Inserimento video gui.
 */
public class InserimentoVideoGUI extends JDialog {
    private final JTextField IdVideoField;
    private final JComboBox<String> usernameComboBox;
    private final JTextField titoloField;

    private final JTextField descrizioneField;


    /**
     * Instantiates a new Inserimento video gui.
     *
     * @param controller     the controller
     * @param framePadre     the frame padre
     */
    public InserimentoVideoGUI(Controller controller, JFrame framePadre) {
//----------------------------------------------PANNELLO CAMPI-------------------------------------------------------//

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 10, 100));
        setTitle("Inserimento Video");


        // CAMPO IDCOLLEZIONE
        inputPanel.add(new JLabel("Id Video:"));
        IdVideoField = new JTextField();
        inputPanel.add(IdVideoField);

        // CAMPO USERNAME
        inputPanel.add(new JLabel("Autore:"));
        usernameComboBox = new JComboBox<>();
        ArrayList<String> usernameList = controller.getListaUsernameDisponibileVideoGUI();
        for (String i : usernameList)
            usernameComboBox.addItem(i);
        inputPanel.add(usernameComboBox);

        // CAMPO TITOLO
        inputPanel.add(new JLabel("Titolo:"));
        titoloField = new JTextField();
        inputPanel.add(titoloField);


        // CAMPO DESCRIZIONE
        inputPanel.add(new JLabel("Descrizione:"));
        descrizioneField = new JTextField();
        inputPanel.add(descrizioneField);





//----------------------------------------------BOTTONI---------------------------------------------------------------//


        // BOTTONE SALVA
        JButton bottoneSalva = new JButton("Salva");
        bottoneSalva.addActionListener(e -> {
            setVisible(false);


            //controllo che non ci siano dei campi vuoti
            if (usernameComboBox.getSelectedItem() == null || titoloField.getText().isEmpty())  {
                setVisible(true);
            } else {
                //Tutti i campi sono stati inseriti
                int idVideo = Integer.parseInt(IdVideoField.getText());
                String username = (String) usernameComboBox.getSelectedItem();
                String titolo = titoloField.getText();
                /*
                int numeroFrames = Integer.parseInt(numeroFrameField.getText());
                int durata = Integer.parseInt(durataField.getText());

                 */
                String descrizione = descrizioneField.getText();


                try {
                    controller.aggiungiVideo(idVideo, username, titolo, descrizione);
                    JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);
                } catch (PSQLException ex) {
                    JOptionPane.showMessageDialog(null, "Errore durante il salvataggio dei dati del video:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
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
