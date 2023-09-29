package GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.*;

import CONTROLLER.Controller;
import org.postgresql.util.PSQLException;

/**
 * The type View fotografia gui.
 */
public class ViewFotografiaGUI extends JDialog {

    final JTable tabellaFotografie;
    final JTable tabellaCollezione;


    public ViewFotografiaGUI(int idFotografiaSelezionata, Controller controller, JFrame framePadre) throws SQLException {
        setTitle("View Fotografia");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creiamo le variabili per ricevere dal controller i dati dell'utente
        idFotografiaSelezionata = controller.getFotografiaIdViewGUI(idFotografiaSelezionata);
        String usernameSelezionato = controller.getFotografiaUsernameViewGUI(idFotografiaSelezionata);
        byte[] datiFotoSelezionato = controller.getFotografiaDatiFotoViewGUI(idFotografiaSelezionata);
        String dispositivoSelezionato = controller.getFotografiaDispositivoViewGUI(idFotografiaSelezionata);
        String titoloSelezionato = controller.getFotografiaTitoloViewGUI(idFotografiaSelezionata);



        // Creiamo il pannello principale
        JPanel panel = new JPanel(new BorderLayout());

        // Creiamo il pannello per i dati
        JPanel datiPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        datiPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 10, 20));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // ID FOTO
        JLabel idFotoLabel = new JLabel("id foto:", SwingConstants.CENTER);
        idFotoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField idFotoField = new JTextField();
        idFotoField.setText(String.valueOf(idFotografiaSelezionata));
        datiPanel.add(idFotoLabel);
        datiPanel.add(idFotoField);
        idFotoField.setEditable(false);

        // USERNAME AUTORE
        JLabel autoreLabel = new JLabel("Autore:", SwingConstants.CENTER);
        autoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField autoreField = new JTextField();
        autoreField.setText(usernameSelezionato);
        datiPanel.add(autoreLabel);
        datiPanel.add(autoreField);
        autoreField.setEditable(false);

        // DATIFOTO
        JLabel datiFotoLabel = new JLabel("Dati Foto:", SwingConstants.CENTER);
        datiFotoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField datiFotoField = new JTextField();
        datiFotoField.setText(String.valueOf(datiFotoSelezionato));
        datiPanel.add(datiFotoLabel);
        datiPanel.add(datiFotoField);
        datiFotoField.setEditable(false);

        // DISPOSITIVO
        JLabel dispositivoLabel = new JLabel("dispositivo:", SwingConstants.CENTER);
        dispositivoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField dispositivoField = new JTextField();
        dispositivoField.setText(dispositivoSelezionato);
        datiPanel.add(dispositivoLabel);
        datiPanel.add(dispositivoField);

        // TITOLO
        JLabel titoloLabel = new JLabel("titolo:", SwingConstants.CENTER);
        titoloLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField titoloField = new JTextField();
        titoloField.setText(titoloSelezionato);
        datiPanel.add(titoloLabel);
        datiPanel.add(titoloField);


        // AGGIUNGI DATI AL PANNELLO
        leftPanel.add(datiPanel);
        leftPanel.setPreferredSize(new Dimension(100, 100));
        panel.add(leftPanel, BorderLayout.CENTER);


        //-----------------------------------------------TABELLE-----------------------------------------------------------------//

        //TABELLA VIDEO
        // Creiamo il pannello destro per la tabella video
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Compare in:"));

        // TABELLA CONTENUTO IN VIDEO a destra
        ArrayList<Integer> listaCollezioniAssociati = controller.vediContenutoFotografia(idFotografiaSelezionata);
        DefaultTableModel tabellaFotografieModel = new DefaultTableModel();
        Object[][] data = new Object[listaCollezioniAssociati.size()][2];
        for (int i = 0; i < listaCollezioniAssociati.size(); i++) {
            data[i][0] = listaCollezioniAssociati.get(i);
            data[i][1] = listaCollezioniAssociati.get(i);

        }
        tabellaFotografieModel.setDataVector(data, new Object[]{"ID Collezione"});
        this.tabellaFotografie = new JTable(tabellaFotografieModel);
        this.tabellaFotografie.setShowGrid(true);

        //allineo il testo delle colonne al centro
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        // Applicazione del renderizzatore personalizzato a tutte le colonne
        int columnCount = this.tabellaFotografie.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            this.tabellaFotografie.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tabellaFotografieModel);
        // Impostiamo il TableRowSorter sulla tabella
        this.tabellaFotografie.setRowSorter(sorter);

        tabellaFotografie.setDefaultEditor(Object.class, null);
        tabellaFotografie.setDefaultEditor(Object.class, null);
        tabellaFotografie.getTableHeader().setReorderingAllowed(false);
        tabellaFotografie.setShowGrid(true);
        //COLORI TABELLA
        tabellaFotografie.setGridColor(Color.DARK_GRAY);
        tabellaFotografie.setBackground(Color.DARK_GRAY);
        tabellaFotografie.getTableHeader().setBackground(Color.DARK_GRAY);
        tabellaFotografie.getTableHeader().setForeground(Color.WHITE);

        //barra di scorrimento
        JScrollPane scrollPane = new JScrollPane(this.tabellaFotografie);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        rightPanel.add(new JScrollPane(this.tabellaFotografie), BorderLayout.CENTER);
        rightPanel.setPreferredSize(new Dimension(150, 700));
        this.tabellaFotografie.getTableHeader().setReorderingAllowed(false);
        panel.add(rightPanel, BorderLayout.EAST);



        //TABELLA TAG
        // Creiamo il pannello inferiore per la tabella
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("TAG:"));

        // Tabella della fotografia
        ArrayList<String> listaTagUtente = controller.vediTagUtente(idFotografiaSelezionata);
        ArrayList<String> listaTagSoggetto = controller.vediTagSoggetto(idFotografiaSelezionata);

        DefaultTableModel tabellaFotografiaTagModel = new DefaultTableModel();

        // Impostiamo la colonna "Scatto junior" nella tabella
        tabellaFotografiaTagModel.addColumn("Utente Taggato");
        tabellaFotografiaTagModel.addColumn("Soggetto Tagagto");

        // Aggiungi una riga vuota per "TAG UTENTE"
        tabellaFotografiaTagModel.addRow(new Object[]{""});

        // Creiamo una stringa per contenere gli IDCOLLEZIONI separati da virgole
        StringBuilder idStringBuilderTagUtente = new StringBuilder();
        for (int i = 0; i < listaTagUtente.size(); i++) {
            idStringBuilderTagUtente.append(listaTagUtente.get(i));

            // Aggiungi una virgola se non è l'ultimo elemento
            if (i < listaTagUtente.size() - 1) {
                idStringBuilderTagUtente.append(", ");
            }
        }

        // Creiamo una stringa per contenere i SOGGETTI separati da virgole
        StringBuilder idStringBuilderSoggetto = new StringBuilder();
        for (int i = 0; i < listaTagSoggetto.size(); i++) {
            idStringBuilderSoggetto.append(listaTagSoggetto.get(i));

            // Aggiungi una virgola se non è l'ultimo elemento
            if (i < listaTagSoggetto.size() - 1) {
                idStringBuilderSoggetto.append(", ");
            }
        }

        // Imposta la stringhe ottenute
        tabellaFotografiaTagModel.setValueAt(idStringBuilderTagUtente.toString(), 0, 0); // 0, 0 rappresenta la riga e la colonna in cui vuoi visualizzare gli ID
        tabellaFotografiaTagModel.setValueAt(idStringBuilderSoggetto.toString(), 0, 1); // 0, 1 rappresenta la riga e la colonna in cui vuoi visualizzare gli ID

        tabellaCollezione = new JTable(tabellaFotografiaTagModel);
        tabellaCollezione.setEnabled(false);
        bottomPanel.add(new JScrollPane(tabellaCollezione), BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setPreferredSize(new Dimension(1000, 75));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        tabellaCollezione.getColumnModel().getColumn(0).setCellRenderer(renderer);
        tabellaCollezione.getColumnModel().getColumn(1).setCellRenderer(renderer);


        tabellaCollezione.setDefaultEditor(Object.class, null);
        tabellaCollezione.getTableHeader().setReorderingAllowed(false);
        tabellaCollezione.setShowGrid(true);

        // COLORI TABELLA
        tabellaCollezione.setGridColor(Color.DARK_GRAY);
        tabellaCollezione.setBackground(Color.DARK_GRAY);
        tabellaCollezione.getTableHeader().setBackground(Color.DARK_GRAY);
        tabellaCollezione.getTableHeader().setForeground(Color.WHITE);


//---------------------------------------------------BOTTONI----------------------------------------------------------------//


        // Crea il pannello dei bottoni
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));


        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);

        //BOTTONE TAG UTENTE
        JButton bottoneAggiungiTagUtente;
        bottoneAggiungiTagUtente = new JButton("Add Tag Utente");
        panelBottoniRight.add(bottoneAggiungiTagUtente);

        int finalIdFotografiaSelezionata = idFotografiaSelezionata;
        int finalIdFotografiaSelezionata1 = idFotografiaSelezionata;
        bottoneAggiungiTagUtente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Chiamata al metodo per ottenere gli utenti disponibili
                ArrayList<String> utentiDisponibili = controller.getListaUsernameDisponibileCollezioneGUI();

                // Converte l'ArrayList in un array di stringhe
                String[] utentiArray = utentiDisponibili.toArray(new String[0]);

                // Mostra un dialog per selezionare gli utenti disponibili
                String utenteSelezionato = (String) JOptionPane.showInputDialog(
                        ViewFotografiaGUI.this, "Seleziona un utente da aggiungere:", "Aggiungi Tag Utente",
                        JOptionPane.QUESTION_MESSAGE, null, utentiArray, utentiArray[0]);

                if (utenteSelezionato != null) {
                    // Esegui l'aggiunta dell'utente selezionato nella tabella del database
                    try {
                        // Chiamata al metodo per aggiungere l'utente alla tabella
                        controller.aggiungiTagUtente(finalIdFotografiaSelezionata, utenteSelezionato);

                        // Aggiorna la tabella dei tag utente con il nuovo utente
                        // Implementa questa parte in base alla struttura della tua tabella e dell'interfaccia utente.
                        // Aggiungi l'utente alla tabella grafica o aggiorna la visualizzazione.
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(ViewFotografiaGUI.this,
                                "Errore durante l'aggiunta dell'utente: " + ex.getMessage(),
                                "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
                updateTabella(controller, finalIdFotografiaSelezionata1);
            }
        });

        //BOTTONE TAG SOGGETTO
        JButton bottoneAggiungiTagSoggetto;
        bottoneAggiungiTagUtente = new JButton("Add Tag Soggetto");
        panelBottoniRight.add(bottoneAggiungiTagUtente);

        int finalIdFotografiaSelezionata2 = idFotografiaSelezionata;
        int finalIdFotografiaSelezionata3 = idFotografiaSelezionata;
        bottoneAggiungiTagUtente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Chiamata al metodo per ottenere gli utenti disponibili
                ArrayList<String> soggettiDisponibili = controller.getListaSoggettiDisponibiliGUI();

                // Converte l'ArrayList in un array di stringhe
                String[] soggettiArray = soggettiDisponibili.toArray(new String[0]);

                // Mostra un dialog per selezionare gli utenti disponibili
                String soggettoSelezionato = (String) JOptionPane.showInputDialog(
                        ViewFotografiaGUI.this, "Seleziona un utente da aggiungere:", "Aggiungi Tag Utente",
                        JOptionPane.QUESTION_MESSAGE, null, soggettiArray, soggettiArray[0]);

                if (soggettoSelezionato != null) {
                    // Esegui l'aggiunta dell'utente selezionato nella tabella del database
                    try {
                        // Chiamata al metodo per aggiungere l'utente alla tabella
                        controller.aggiungiTagSoggetto(finalIdFotografiaSelezionata, soggettoSelezionato);

                        // Aggiorna la tabella dei tag utente con il nuovo utente
                        // Implementa questa parte in base alla struttura della tua tabella e dell'interfaccia utente.
                        // Aggiungi l'utente alla tabella grafica o aggiorna la visualizzazione.
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(ViewFotografiaGUI.this,
                                "Errore durante l'aggiunta del soggetto: " + ex.getMessage(),
                                "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
                updateTabella(controller, finalIdFotografiaSelezionata1);
            }
        });



        //BOTTONE RIMUOVI TAG UTENTE
        JButton bottoneRimuoviTagUtente;
        bottoneRimuoviTagUtente = new JButton("Remove Tag Utente");
        panelBottoniRight.add(bottoneRimuoviTagUtente);
        int finalIdFotografiaSelezionata4 = idFotografiaSelezionata;
        bottoneRimuoviTagUtente.addActionListener(e -> {
            int rowCount = tabellaFotografiaTagModel.getRowCount();
            ArrayList<String> utentiTaggati = new ArrayList<>();

            // Ottieni gli utenti taggati dalla colonna "Utente Taggato"
            for (int i = 0; i < rowCount; i++) {
                String utente = tabellaFotografiaTagModel.getValueAt(i, 0).toString();
                if (!utente.isEmpty()) {
                    utentiTaggati.add(utente);
                }
            }

            if (utentiTaggati.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Nessun utente taggato presente.", "Nessun utente", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Chiedi all'utente di selezionare un utente da eliminare utilizzando una finestra di dialogo
                String utenteSelezionato = (String) JOptionPane.showInputDialog(
                        panel,
                        "Seleziona un utente da eliminare:",
                        "Elimina Utente Taggato",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        utentiTaggati.toArray(),
                        utentiTaggati.get(0)
                );

                if (utenteSelezionato != null) {
                    // Elimina l'utente selezionato dalla tabella o dal database
                    try {
                        controller.eliminaTagUtente(finalIdFotografiaSelezionata4, utenteSelezionato);
                        // Aggiorna la tabella dopo l'eliminazione
                        updateTabella(controller, finalIdFotografiaSelezionata4);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione del tag:\n" + ex.getMessage(), "Errore di eliminazione", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        //BOTTONE RIMUOVI TAG SOGGETTO
        JButton bottoneRimuoviTagSoggetto;
        bottoneRimuoviTagSoggetto = new JButton("Remove Tag Soggetto");
        panelBottoniRight.add(bottoneRimuoviTagSoggetto);
        int finalIdFotografiaSelezionata5 = idFotografiaSelezionata;
        bottoneRimuoviTagSoggetto.addActionListener(e -> {
            int rowCount = tabellaFotografiaTagModel.getRowCount();
            ArrayList<String> soggettiTaggati = new ArrayList<>();

            // Ottieni gli utenti taggati dalla colonna "Utente Taggato"
            for (int i = 0; i < rowCount; i++) {
                String soggetto = tabellaFotografiaTagModel.getValueAt(i, 1).toString();
                if (!soggetto.isEmpty()) {
                    soggettiTaggati.add(soggetto);
                }
            }

            if (soggettiTaggati.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Nessun soggetto taggato presente.", "Nessun Soggetto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Chiedi all'utente di selezionare un utente da eliminare utilizzando una finestra di dialogo
                String soggettoSelezionato = (String) JOptionPane.showInputDialog(
                        panel,
                        "Seleziona un soggetto da eliminare:",
                        "Elimina Soggetto Taggato",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        soggettiTaggati.toArray(),
                        soggettiTaggati.get(0)
                );

                if (soggettoSelezionato != null) {
                    // Elimina l'utente selezionato dalla tabella o dal database
                    try {
                        controller.eliminaTagSoggetto(finalIdFotografiaSelezionata5, soggettoSelezionato);
                        // Aggiorna la tabella dopo l'eliminazione
                        updateTabella(controller, finalIdFotografiaSelezionata5);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione del tag:\n" + ex.getMessage(), "Errore di eliminazione", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // BOTTONE SALVA
        JButton bottoneSalva;
        bottoneSalva = new JButton("Salva Modifiche");
        int finalIdFotografiaSelezionata6 = idFotografiaSelezionata;
        bottoneSalva.addActionListener(e -> {
            setVisible(false);
            String dispositivoModificato = dispositivoField.getText();
            String titoloModificato = titoloField.getText();

            try {
                controller.modificaFotografia(finalIdFotografiaSelezionata6, dispositivoModificato, titoloModificato);
                JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante la modifica dei dati della fotografia:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } finally {
                dispose();
                framePadre.setVisible(true);
            }

        });
        panelBottoniLeft.add(bottoneSalva);

        //BOTTONE ANNULLA
        JButton bottoneTornaIndietro;
        bottoneTornaIndietro = new JButton("Indietro");
        bottoneTornaIndietro.addActionListener(e -> {
            //chiudo la finestra di dialogo
            dispose();
            framePadre.setVisible(true);
        });
        panelBottoniLeft.add(bottoneTornaIndietro);















        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);

        // Aggiungo i pannelli alla finestra principale
        add(panel,BorderLayout.CENTER);



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

    private void updateTabella(Controller controller, int idFotoSelezionata) {
        // Creiamo il modello per la tabella
        DefaultTableModel tabellaFotografiaModel = new DefaultTableModel();
        tabellaFotografiaModel.addColumn("Utente Taggato");
        tabellaFotografiaModel.addColumn("Soggetto Taggato");

        try {
            // Otteniamo i dati dai metodi del controller
            ArrayList<String> listaTagUtente = controller.vediTagUtente(idFotoSelezionata);
            ArrayList<String> listaTagSoggetto = controller.vediTagSoggetto(idFotoSelezionata);

            // Aggiungiamo i dati alla tabella
            for (String tagUtente : listaTagUtente) {
                tabellaFotografiaModel.addRow(new Object[]{tagUtente, ""});
            }

            for (String tagSoggetto : listaTagSoggetto) {
                tabellaFotografiaModel.addRow(new Object[]{"", tagSoggetto});
            }

            // Aggiorna il modello della tabella
            tabellaCollezione.setModel(tabellaFotografiaModel);
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            tabellaCollezione.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Colonna "Utente Taggato"
            tabellaCollezione.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Colonna "Soggetto Taggato"
        } catch (SQLException e) {
            // Gestisci l'eccezione qui, ad esempio, mostra un messaggio di errore
            e.printStackTrace(); // Stampa l'errore a scopo di debug
            JOptionPane.showMessageDialog(this, "Errore durante il recupero dei dati: " + e.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

}

