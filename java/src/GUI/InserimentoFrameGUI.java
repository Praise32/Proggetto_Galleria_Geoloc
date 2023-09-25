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
 * The type Inserimento Utente gui.
 */
public class InserimentoFrameGUI extends JDialog {
    private final JTextField IdVideoField;
    private final JComboBox<Integer> FotoComboBox;
    private final JTextField durataField;
    private final JTextField ordineField;


    /**
     * Instantiates a new Inserimento impiegato gui.
     *
     * @param controller     the controller
     * @param framePadre     the frame padre
     */
    public InserimentoFrameGUI(int IdVideoSelezionato, Controller controller, JFrame framePadre) {
//----------------------------------------------PANNELLO CAMPI-------------------------------------------------------//

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 10, 100));
        setTitle("Inserimento Frame");


        // CAMPO IDVIDEO
        inputPanel.add(new JLabel("Id Video:"));
        IdVideoField = new JTextField();
        inputPanel.add(IdVideoField);
        IdVideoField.setText(String.valueOf(IdVideoSelezionato));
        IdVideoField.setEditable(false);

        // CAMPO IDFOTO
        inputPanel.add(new JLabel("Id_Foto:"));
        FotoComboBox = new JComboBox<>();
        ArrayList<Integer> FotoList = controller.getListaFotoDisponibileCollezioneGUI();
        for (int i : FotoList)
            FotoComboBox.addItem(i);
        inputPanel.add(FotoComboBox);

        // CAMPO TITOLO
        inputPanel.add(new JLabel("Durata:"));
        durataField = new JTextField();
        inputPanel.add(durataField);

        // CAMPO Ordine
        inputPanel.add(new JLabel("Ordine:"));
        ordineField = new JTextField();
        inputPanel.add(ordineField);






//----------------------------------------------BOTTONI---------------------------------------------------------------//


        // BOTTONE SALVA
        JButton bottoneSalva = new JButton("Salva");
        bottoneSalva.addActionListener(e -> {
            setVisible(false);


            //controllo che non ci siano dei campi vuoti
            if (FotoComboBox.getSelectedItem() == null || durataField.getText().isEmpty())  {
                setVisible(true);
            } else {
                //Tutti i campi sono stati inseriti
                int idVideo = Integer.parseInt(IdVideoField.getText());
                int idFoto = (int) FotoComboBox.getSelectedItem();
                int durata = Integer.parseInt(durataField.getText());
                int ordine = Integer.parseInt(ordineField.getText());


                try {
                    controller.aggiungiFrame(idVideo, idFoto,durata,ordine);
                    JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);
                } catch (PSQLException ex) {
                    JOptionPane.showMessageDialog(null, "Errore durante il salvataggio dei dati del frame:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
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
