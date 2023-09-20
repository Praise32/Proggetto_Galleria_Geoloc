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
public class InserimentoLuogoGUI extends JDialog {

    private final JTextField latitudineField;
    private final JTextField longitudineField;

    private final JTextField nomeField;
    private final JTextField descrizioneField;


    /**
     * Instantiates a new Inserimento impiegato gui.
     *
     * @param controller the controller
     * @param framePadre the frame padre
     */
    public InserimentoLuogoGUI (Controller controller, JFrame framePadre) {
//----------------------------------------------PANNELLO CAMPI-------------------------------------------------------//

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 10, 100));
        setTitle("Inserimento Soggetto");

        // CAMPO LATITUDINE
        inputPanel.add(new JLabel("Latitudine:"));
        latitudineField = new JTextField();
        inputPanel.add(latitudineField);

        // CAMPO LONGITUDINE
        inputPanel.add(new JLabel("Longitudine:"));
        longitudineField = new JTextField();
        inputPanel.add(longitudineField);

        // CAMPO NOME
        inputPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        inputPanel.add(nomeField);

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
            if (nomeField.getText().isEmpty() || latitudineField.getText().isEmpty()) {
                setVisible(true);
            } else {
                //Tutti i campi sono stati inseriti
                Float latitudine = Float.parseFloat(latitudineField.getText());
                Float longitudine = Float.parseFloat(longitudineField.getText());
                String nome = nomeField.getText();
                String descrizione = descrizioneField.getText();


                try {
                    controller.aggiungiLuogo(latitudine,longitudine,nome,descrizione);
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
