package GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

import CONTROLLER.Controller;

/**
 * The type Modifica Video gui.
 */
public class ViewVideoGUI extends JDialog {

    private final JTextField titoloField;
    private final JTextField autoreField;
    private final JTextField descrizioneField;



    public ViewVideoGUI(int idVideoSelezionato, Controller controller, JFrame framePadre) throws SQLException {
        setTitle("Profilo Video");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creiamo le variabili per ricevere dal controller i dati della collezione
        idVideoSelezionato = controller.getIdVideoViewGUI(idVideoSelezionato);
        String AutoreSelezionato = controller.getAutoreVideoViewGUI(idVideoSelezionato);
        String TitoloSelezionato = controller.getTitoloVideoViewGUI(idVideoSelezionato);
        int NumeroFramesSelezionati = controller.getNumeroFramesVideoViewGUI(idVideoSelezionato);
        int DurataSelezionata = controller.getDurataVideoViewGUI(idVideoSelezionato);
        String DescrizioneSelezionata = controller.getDescrizioneVideoViewGUI(idVideoSelezionato);



        // Creiamo il pannello principale
        JPanel panel = new JPanel(new BorderLayout());

        // Creiamo il pannello per i dati
        JPanel datiPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        datiPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        //ID VIDEO
        JLabel VideoLabel = new JLabel("ID Video:", SwingConstants.CENTER);
        VideoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField VideoField = new JTextField();
        VideoField.setText(String.valueOf(idVideoSelezionato));
        datiPanel.add(VideoLabel);
        datiPanel.add(VideoField);
        VideoField.setEditable(false);


        // AUTORE
        JLabel autoreLabel = new JLabel("Autore:", SwingConstants.CENTER);
        autoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        autoreField = new JTextField();
        autoreField.setText(AutoreSelezionato);
        autoreField.setPreferredSize(new Dimension(200, 30));
        datiPanel.add(autoreLabel);
        datiPanel.add(autoreField);
        autoreField.setEditable(false);


        // TITOLO
        JLabel titoloLabel = new JLabel("Titolo:", SwingConstants.CENTER);
        titoloLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titoloField = new JTextField();
        titoloField.setText(TitoloSelezionato);
        titoloField.setPreferredSize(new Dimension(200, 30));
        datiPanel.add(titoloLabel);
        datiPanel.add(titoloField);


        //NUMERO FRAMES
        JLabel NumeroFramesLabel = new JLabel("Numero Frames:", SwingConstants.CENTER);
        NumeroFramesLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField  NumeroFramesField = new JTextField();
        NumeroFramesField.setText(String.valueOf(NumeroFramesSelezionati));
        datiPanel.add(NumeroFramesLabel);
        datiPanel.add(NumeroFramesField);
        NumeroFramesField.setEditable(false);

        //DURATA
        JLabel DurataLabel = new JLabel("Durata:", SwingConstants.CENTER);
        DurataLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField DurataField = new JTextField();
        DurataField.setText(String.valueOf(DurataSelezionata));
        datiPanel.add(DurataLabel);
        datiPanel.add(DurataField);
        DurataField.setEditable(false);


        //DESCRIZIONE
        JLabel descrizioneLabel = new JLabel("Descrizione:", SwingConstants.CENTER);
        descrizioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
        descrizioneField = new JTextField();
        descrizioneField.setText(DescrizioneSelezionata);
        descrizioneField.setPreferredSize(new Dimension(200, 30));
        datiPanel.add(descrizioneLabel);
        datiPanel.add(descrizioneField);




        // AGGIUNGI DATI AL PANNELLO
        leftPanel.add(datiPanel);
        leftPanel.setPreferredSize(new Dimension(100, 100));
        panel.add(leftPanel, BorderLayout.CENTER);




//-----------------------------------------------BOTTONI-----------------------------------------------------------------//


        // Crea il pannello dei bottoni
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);
        JButton bottoneSalva = new JButton("Salva modifiche");
        JButton bottoneAnnulla = new JButton("Annulla modifiche");



        panelBottoniLeft.add(bottoneSalva);
        panelBottoniLeft.add(bottoneAnnulla);

        // Aggiungo i pannelli alla finestra principale
        add(panel,BorderLayout.CENTER);

        // BOTTONE SALVA
        final int idVideo = controller.getIdVideoViewGUI(idVideoSelezionato);
        int finalIdVideoSelezionato = idVideoSelezionato;
        bottoneSalva.addActionListener(e -> {
            setVisible(false);
            String titoloModificato = titoloField.getText();
            String descrizioneModificata = descrizioneField.getText();


            try {

                controller.modificaVideo(finalIdVideoSelezionato,titoloModificato,descrizioneModificata);
                JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante la modifica dei dati del video:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } finally {
                dispose();
                framePadre.setVisible(true);
            }

        });

        //BOTTONE ANNULLA
        bottoneAnnulla.addActionListener(e -> {
            //chiudo la finestra di dialogo
            dispose();
            framePadre.setVisible(true);
        });



        // Aggiungo i pannelli alla finestra principale
        add(panel,BorderLayout.CENTER);
        add(panelBottoni, BorderLayout.SOUTH);


        // impostazioni finestra
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //listener per mostrare la finestra padre quando viene chiusa quella figlia
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                framePadre.setVisible(true);
            }
        });


        setVisible(true);


    }















}

