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

        // Latitudine
        JLabel passwordLabel = new JLabel("ID Video:", SwingConstants.CENTER);
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField passwordField = new JTextField();
        passwordField.setText(String.valueOf(IdVideoSelezionato));
        datiPanel.add(passwordLabel);
        datiPanel.add(passwordField);
        passwordField.setEditable(false);

        // Longitudine
        JLabel LongitudineLabel = new JLabel("ID Foto:", SwingConstants.CENTER);
        LongitudineLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField LongitudineField = new JTextField();
        LongitudineField.setText(String.valueOf(IdFotoSelezionata));
        datiPanel.add(LongitudineLabel);
        datiPanel.add(LongitudineField);
        LongitudineField.setEditable(false);

        // NOME
        JLabel usernameLabel = new JLabel("Durata:", SwingConstants.CENTER);
        usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField usernameField = new JTextField();
        usernameField.setText(String.valueOf(DurataSelezionata));
        datiPanel.add(usernameLabel);
        datiPanel.add(usernameField);

        // CATEGORIA
        JLabel CategoriaLabel = new JLabel("Ordine:", SwingConstants.CENTER);
        CategoriaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField CategoriaField = new JTextField();
        CategoriaField.setText(String.valueOf(OrdineSelezionato));
        datiPanel.add(CategoriaLabel);
        datiPanel.add(CategoriaField);


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
            String IDVideoModificatoText = passwordField.getText();
            int IdVideoModificato = Integer.parseInt(IDVideoModificatoText);
            String IdFotoModificataText = LongitudineField.getText();
            int IdFotoModificata = Integer.parseInt(IdFotoModificataText);
            String durataModificataText = usernameField.getText();
            int durataModificata = Integer.parseInt(durataModificataText);
            String OrdineModificatoText = CategoriaField.getText();
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

        // username non modificabile
        passwordField.setEditable(false);
        LongitudineField.setEditable(false);


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
