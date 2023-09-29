package GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

import CONTROLLER.Controller;

/**
 * The type Modifica Frame gui.
 */
public class ViewModificaFrameGUI extends JDialog {



    public ViewModificaFrameGUI(int frameSelezionato,int ordineSelezionato, Controller controller, JFrame framePadre) throws SQLException {
        setTitle("Modifica Frame");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creiamo le variabili per ricevere dal controller i dati del frame
        int IdVideoSelezionato = controller.getIdVideoFrameViewGUI(frameSelezionato,ordineSelezionato);
        int IdFotoSelezionata = controller.getIdFotoFrameViewGUI(frameSelezionato,ordineSelezionato);
        int DurataSelezionata = controller.getDurataViewGUI(frameSelezionato,ordineSelezionato);
        int OrdineSelezionato = controller.getOrdineViewGUI(frameSelezionato,ordineSelezionato);


        // Creiamo il pannello principale
        JPanel panel = new JPanel(new BorderLayout());

        // Creiamo il pannello per i dati
        JPanel datiPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        datiPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 10, 20));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // ID VIDEO
        JLabel idVideoLabel = new JLabel("ID Video:", SwingConstants.CENTER);
        idVideoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField idVideoField = new JTextField();
        idVideoField.setText(String.valueOf(IdVideoSelezionato));
        datiPanel.add(idVideoField);
        datiPanel.add(idVideoField);
        idVideoField.setEditable(false);

        // ID FOTO
        JLabel idFotoLabel = new JLabel("ID Foto:", SwingConstants.CENTER);
        idFotoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField idFotoField = new JTextField();
        idFotoField.setText(String.valueOf(IdFotoSelezionata));
        datiPanel.add(idFotoLabel);
        datiPanel.add(idFotoField);
        idFotoField.setEditable(false);

        // DURATA
        JLabel durataLabel = new JLabel("Durata:", SwingConstants.CENTER);
        durataLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField durataField = new JTextField();
        durataField.setText(String.valueOf(DurataSelezionata));
        datiPanel.add(durataLabel);
        datiPanel.add(durataField);

        // ORDINE
        JLabel OrdineLabel = new JLabel("Ordine:", SwingConstants.CENTER);
        OrdineLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField OrdineField = new JTextField();
        OrdineField.setText(String.valueOf(OrdineSelezionato));
        datiPanel.add(OrdineLabel);
        datiPanel.add(OrdineField);


        // AGGIUNGI DATI AL PANNELLO
        leftPanel.add(datiPanel);
        leftPanel.setPreferredSize(new Dimension(700, 600));
        panel.add(leftPanel, BorderLayout.CENTER);





//-----------------------------------------------BOTTONI-----------------------------------------------------------------//



        // Crea il pannello dei bottoni
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton bottoneSalva = new JButton("Salva modifiche");
        JButton bottoneAnnulla = new JButton("Annulla modifiche");
        panelBottoniLeft.add(bottoneSalva);
        panelBottoniLeft.add(bottoneAnnulla);

        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);

        // Aggiungo i pannelli alla finestra principale
        add(panel,BorderLayout.CENTER);

        // BOTTONE SALVA
        bottoneSalva.addActionListener(e -> {
            setVisible(false);
            String IDVideoModificatoText = idVideoField.getText();
            int IdVideoModificato = Integer.parseInt(IDVideoModificatoText);
            String IdFotoModificataText = idFotoField.getText();
            int IdFotoModificata = Integer.parseInt(IdFotoModificataText);
            String durataModificataText = durataField.getText();
            int durataModificata = Integer.parseInt(durataModificataText);
            String OrdineModificatoText = OrdineField.getText();
            int ordineModificato = Integer.parseInt(OrdineModificatoText);




            try {

                controller.modificaFrame(IdVideoModificato, IdFotoModificata,durataModificata, ordineModificato);
                JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante la modifica dei dati del soggetto:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } finally {
                dispose();
                framePadre.setVisible(true);
            }
            //}

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

        // id non modificabile
        idVideoField.setEditable(false);
        idFotoField.setEditable(false);


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
