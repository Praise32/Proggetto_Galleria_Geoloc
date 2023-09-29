package GUI;

import CONTROLLER.Controller;
import MAIN.User;
import org.jdesktop.swingx.JXDatePicker;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * The type Inserimento Fotografia gui.
 */
public class InserimentoFotografiaGUI extends JDialog {
    private final JComboBox<String> nomeLuogoCombobox;

    private final JTextField titoloField;
    private final JXDatePicker dataFotoPicker;
    private final JCheckBox condivisaCheckBox;

    /**
     * Instantiates a new Inserimento Fotografia gui.
     *
     * @param controller the controller
     * @param framePadre the frame padre
     */
    public InserimentoFotografiaGUI(Controller controller, JFrame framePadre) {
//----------------------------------------------PANNELLO CAMPI-------------------------------------------------------//

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 10, 100));
        setTitle("Inserimento Fotografia");


        JPanel panel = new JPanel();
        JXDatePicker datePicker = new JXDatePicker();

        // Cambia il colore del testo nel selettore di data
        datePicker.setForeground(Color.WHITE);

        // Cambia il colore di sfondo nel selettore di data
        datePicker.setBackground(Color.DARK_GRAY);

        // Cambia il colore di sfondo nell'editor
        datePicker.getEditor().setBackground(Color.DARK_GRAY);


        // USERNAME DELL'UTENTE CHE EFFETTUA L'INSERIMENTO
        inputPanel.add(new JLabel("Username:")); //+ User.getInstance().getUsername()));
        inputPanel.add(new JLabel(User.getInstance().getUsername()));

        //CAMPO NOME LUOGO
        inputPanel.add(new JLabel("Luogo Scatto:"));
        nomeLuogoCombobox = new JComboBox<>();
        ArrayList<String> luoghiDisponibili = controller.getListaLuoghiDisponibiliGUI();

        for (String l : luoghiDisponibili) {
            nomeLuogoCombobox.addItem(l);
        }
        inputPanel.add(nomeLuogoCombobox);

        // CAMPO DATI FOTO
        inputPanel.add(new JLabel("Dati Foto:"));
        JTextField DatiField = new JTextField();
        inputPanel.add(DatiField);

        // CAMPO DISPOSITIVO
        inputPanel.add(new JLabel("Dispositivo:"));
        JTextField DispositivoField = new JTextField();
        inputPanel.add(DispositivoField);

        //CAMPO DATA FOTOGRAFIA
        inputPanel.add(new JLabel("Data foto:"));
        dataFotoPicker = new JXDatePicker();
        dataFotoPicker.setFormats(new SimpleDateFormat("yy-MM-dd"));
        inputPanel.add(dataFotoPicker);

        // CAMPO LATITUDINE
        inputPanel.add(new JLabel("Latitudine:"));
        JComboBox<Float> latitudineCombobox = new JComboBox<>();
        inputPanel.add(latitudineCombobox);


        //CAMPO LONGITUDINE
        inputPanel.add(new JLabel("Longitudine:"));
        JComboBox<Float> longitudineCombobox = new JComboBox<>();
        inputPanel.add(longitudineCombobox);

        // CAMPO TITOLO
        inputPanel.add(new JLabel("Titolo:"));
        titoloField = new JTextField();
        inputPanel.add(titoloField);

        // CHECKBOX CONDIVISA
        condivisaCheckBox = new JCheckBox("Condivisa");
        inputPanel.add(condivisaCheckBox);
//LISTENER PER I CAMPI LATITUDINE E LONGITUDINE

        // Aggiungi un listener per il JComboBox del nome del luogo
        nomeLuogoCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ottieni il nome del luogo selezionato
                String nomeLuogoSelezionato = (String) nomeLuogoCombobox.getSelectedItem();

                // Ora puoi ottenere una lista di valori di latitudine associati al nome del luogo
                ArrayList<Float> latitudini = controller.getLatitudiniByNomeLuogo(nomeLuogoSelezionato);
                // Rimuovi tutti gli elementi esistenti dal combobox di latitudine
                latitudineCombobox.removeAllItems();

                // Aggiungi i valori di latitudine al combobox di latitudine
                for (float latitudine : latitudini) {
                    latitudineCombobox.addItem(latitudine);
                }
            }
        });

        // Aggiungi un listener per il JComboBox del nome del luogo
        nomeLuogoCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ottieni il nome del luogo selezionato
                String nomeLuogoSelezionato = (String) nomeLuogoCombobox.getSelectedItem();

                // Ora puoi ottenere una lista di valori di latitudine associati al nome del luogo
                ArrayList<Float> longitudini = controller.getLongitudineByNomeLuogo(nomeLuogoSelezionato);
                // Rimuovi tutti gli elementi esistenti dal combobox di latitudine
                longitudineCombobox.removeAllItems();


                // Aggiungi i valori di latitudine al combobox di latitudine
                for (float longitudine : longitudini) {
                    longitudineCombobox.addItem(longitudine);
                }
            }
        });


//----------------------------------------------BOTTONI---------------------------------------------------------------//

        // BOTTONE SALVA
        JButton bottoneSalva = new JButton("Salva");
        bottoneSalva.addActionListener(e -> {
            setVisible(false);


            //controllo che non ci siano dei campi vuoti
            if (titoloField.getText().isEmpty()) {
                setVisible(true);
            } else {
                //Tutti i campi sono stati inseriti
                String usernameAutore = User.getInstance().getUsername();
                String datiFotoText = DatiField.getText();
                byte[] datiFoto = datiFotoText.getBytes();
                String dispositivo = DispositivoField.getText();
                java.util.Date utilDate = dataFotoPicker.getDate();
                java.sql.Timestamp dataFoto = new java.sql.Timestamp(utilDate.getTime());
                float luogolat = (float) latitudineCombobox.getSelectedItem();
                float luogolon = (float) longitudineCombobox.getSelectedItem();
                boolean condivisa = condivisaCheckBox.isSelected();
                String titolo = titoloField.getText();

                try {
                    controller.aggiungiFotografia(usernameAutore, datiFoto, dispositivo, dataFoto, luogolat, luogolon, condivisa, titolo);
                    JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);
                } catch (PSQLException ex) {
                    JOptionPane.showMessageDialog(null, "Errore durante il salvataggio dei dati della fotografia:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
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


