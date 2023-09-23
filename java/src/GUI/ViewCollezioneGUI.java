package GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.*;

import CONTROLLER.Controller;
import org.postgresql.util.PSQLException;

/**
 * The type Profilo Collezuibe gui.
 */
public class ViewCollezioneGUI extends JDialog {

    private final JTextField titoloField;
    private final JTextField autoreField;
    final JTable tabellaCollezioni;


    public ViewCollezioneGUI(int idCollezioneSelezionato, Controller controller, JFrame framePadre) throws SQLException {
        setTitle("Profilo Collezione");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creiamo le variabili per ricevere dal controller i dati della collezione
        idCollezioneSelezionato = controller.getCollezioneIDcolViewGUI(idCollezioneSelezionato);
        String UsernameSelezionato = controller.getCollezioneUsernameViewGUI(idCollezioneSelezionato);
        String TitoloSelezionato = controller.getCollezioneTitoloViewGUI(idCollezioneSelezionato);



        // Creiamo il pannello principale
        JPanel panel = new JPanel(new BorderLayout());

        // Creiamo il pannello per i dati
        JPanel datiPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        datiPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // AUTORE
        JLabel autoreLabel = new JLabel("Autore:", SwingConstants.CENTER);
        autoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        autoreField = new JTextField();
        autoreField.setText(UsernameSelezionato);
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


        // AGGIUNGI DATI AL PANNELLO
        leftPanel.add(datiPanel);
        leftPanel.setPreferredSize(new Dimension(100, 100));
        panel.add(leftPanel, BorderLayout.CENTER);




//-----------------------------------------------TABELLE-----------------------------------------------------------------//
        // Creiamo il pannello destro per la tabella foto contenute
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Id Foto:"));

        // TABELLA FOTO POSSEDUTE a destra
        ArrayList<Integer> listaFotoAssociate = controller.vediContenuto(idCollezioneSelezionato);
        DefaultTableModel tabellaFotografieModel = new DefaultTableModel();
        Object[][] data = new Object[listaFotoAssociate.size()][1];
        for (int i = 0; i < listaFotoAssociate.size(); i++) {
            data[i][0] = listaFotoAssociate.get(i);

        }
        tabellaFotografieModel.setDataVector(data, new Object[]{"Foto contenute"});
        this.tabellaCollezioni = new JTable(tabellaFotografieModel);
        this.tabellaCollezioni.setShowGrid(true);

        //allineo il testo delle colonne al centro
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        // Applicazione del renderizzatore personalizzato a tutte le colonne
        int columnCount = this.tabellaCollezioni.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            this.tabellaCollezioni.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tabellaFotografieModel);
        // Impostiamo il TableRowSorter sulla tabella
        this.tabellaCollezioni.setRowSorter(sorter);

        tabellaCollezioni.setDefaultEditor(Object.class, null);
        tabellaCollezioni.setDefaultEditor(Object.class, null);
        tabellaCollezioni.getTableHeader().setReorderingAllowed(false);
        tabellaCollezioni.setShowGrid(true);
        //COLORI TABELLA
        tabellaCollezioni.setGridColor(Color.DARK_GRAY);
        tabellaCollezioni.setBackground(Color.DARK_GRAY);
        tabellaCollezioni.getTableHeader().setBackground(Color.DARK_GRAY);
        tabellaCollezioni.getTableHeader().setForeground(Color.WHITE);

        //barra di scorrimento
        JScrollPane scrollPane = new JScrollPane(this.tabellaCollezioni);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        rightPanel.add(new JScrollPane(this.tabellaCollezioni), BorderLayout.CENTER);
        rightPanel.setPreferredSize(new Dimension(150, 700));
        this.tabellaCollezioni.getTableHeader().setReorderingAllowed(false);
        panel.add(rightPanel, BorderLayout.EAST);


        // Crea il pannello dei bottoni
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);
        JButton bottoneSalva = new JButton("Salva modifiche");
        JButton bottoneAnnulla = new JButton("Annulla modifiche");
        JButton bottoneAggiungiContenuto = new JButton("Aggiungi Contenuto");
        JButton bottoneRimuoviContenuto = new JButton("Rimuovi Contenuto");


        panelBottoniLeft.add(bottoneSalva);
        panelBottoniLeft.add(bottoneAnnulla);
        panelBottoniRight.add(bottoneAggiungiContenuto);
        panelBottoniRight.add(bottoneRimuoviContenuto);

        // Aggiungo i pannelli alla finestra principale
        add(panel,BorderLayout.CENTER);

        // BOTTONE SALVA
        final int idCollezione = controller.getCollezioneIDcolViewGUI(idCollezioneSelezionato);
        int finalIdCollezioneSelezionato = idCollezioneSelezionato;
        bottoneSalva.addActionListener(e -> {
            setVisible(false);
            String titoloModificato = titoloField.getText();


            try {

                controller.modificaCollezione(finalIdCollezioneSelezionato, titoloModificato);
                JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante la modifica dei dati della collezione:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
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


        //BOTTONE AGGIUNGI CONTENUTO
        int finalIdCollezioneSelezionato1 = idCollezioneSelezionato;
        bottoneAggiungiContenuto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Chiamata al metodo per ottenere gli ID delle foto disponibili
                ArrayList<Integer> fotoDisponibili = controller.getListaFotoDisponibileCollezioneGUI();

                // Converte l'ArrayList in un array di Integer
                Integer[] fotoArray = fotoDisponibili.toArray(new Integer[0]);

                // Mostra un dialog per selezionare gli ID delle foto disponibili
                Integer idFotoSelezionata = (Integer) JOptionPane.showInputDialog(
                        framePadre, "Seleziona un ID di foto da aggiungere:", "Aggiungi Contenuto",
                        JOptionPane.QUESTION_MESSAGE, null, fotoArray, fotoArray[0]);

                if (idFotoSelezionata != null) {
                    // Esegui l'aggiunta dell'ID della foto selezionata alla tabella delle foto possedute
                    DefaultTableModel model = (DefaultTableModel) tabellaCollezioni.getModel();
                    model.addRow(new Object[]{idFotoSelezionata});
                    // Ora, aggiorna i dati nel controller per riflettere il cambiamento
                    try {
                        controller.aggiungiContenuto(finalIdCollezioneSelezionato1, idFotoSelezionata);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(ViewCollezioneGUI.this,
                                "Errore durante l'aggiunta del contenuto: " + ex.getMessage(),
                                "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //BOTTONE RIMOVI CONTENUTO
        bottoneRimuoviContenuto.addActionListener(e -> {
            // Apri una finestra di dialogo per selezionare le foto da eliminare
            JFrame frame = new JFrame("Seleziona Foto da Rimuovere");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);

            // Creare una JDialog per selezionare le foto da rimuovere
            JDialog dialog = new JDialog(frame, "Seleziona Foto da Rimuovere", true);
            dialog.setLayout(new BorderLayout());

            // Aggiungi la tabella delle foto a questa finestra di dialogo
            dialog.add(scrollPane, BorderLayout.CENTER);

            // Aggiungi un pulsante "Conferma Rimozione" per consentire all'utente di confermare la rimozione delle foto selezionate
            JButton confermaRimozioneButton = new JButton("Conferma Rimozione");
            confermaRimozioneButton.addActionListener(confirmEvent -> {
                int[] selectedRows = tabellaCollezioni.getSelectedRows();
                if (selectedRows.length > 0) {
                    // Hai selezionato alcune righe da rimuovere
                    int response = JOptionPane.showConfirmDialog(frame, "Sei sicuro di voler rimuovere le foto selezionate?", "Conferma Rimozione", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        // Rimuovi le foto selezionate dalla tabella e dal database
                        for (int i = selectedRows.length - 1; i >= 0; i--) {
                            int selectedRow = selectedRows[i];
                            int idFoto = (int) tabellaFotografieModel.getValueAt(selectedRow, 0);
                            // Inserisci qui la logica per rimuovere la foto dal database
                            // E.g., controller.rimuoviFoto(idFoto);
                            tabellaFotografieModel.removeRow(selectedRow);
                        }
                        // Chiudi il dialog dopo l'eliminazione
                        dialog.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Seleziona almeno una foto da rimuovere.", "Nessuna Selezione", JOptionPane.WARNING_MESSAGE);
                }
            });

            // Aggiungi il pulsante "Conferma Rimozione" nella parte inferiore della finestra di dialogo
            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottomPanel.add(confermaRimozioneButton);
            dialog.add(bottomPanel, BorderLayout.SOUTH);

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
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

