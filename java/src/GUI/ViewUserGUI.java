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
 * The type Profilo impiegato gui.
 */
public class ViewUserGUI extends JDialog {
    private final JCheckBox adminCheckBox;
    final JTable tabellaFotografie;


    public ViewUserGUI(String usernameSelezionato, Controller controller, JFrame framePadre) throws SQLException {
        setTitle("Profilo Utente");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creiamo le variabili per ricevere dal controller i dati dell'utente
        String UsernameSelezionato = controller.getUsernameViewGUI(usernameSelezionato);
        String passwordSelezionata = controller.getUsernamePasswordViewGUI(usernameSelezionato);
        boolean adminSelezionato = controller.getUtenteadminviewGUI(usernameSelezionato);


        // Creiamo il pannello principale
        JPanel panel = new JPanel(new BorderLayout());

        // Creiamo il pannello per i dati
        JPanel datiPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        datiPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 10, 20));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // USERNAME
        JLabel usernameLabel = new JLabel("Username:", SwingConstants.CENTER);
        usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField usernameField = new JTextField();
        usernameField.setText(UsernameSelezionato);
        datiPanel.add(usernameLabel);
        datiPanel.add(usernameField);

        // PASSWORD
        JLabel passwordLabel = new JLabel("Password:", SwingConstants.CENTER);
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField passwordField = new JTextField();
        passwordField.setText(passwordSelezionata);
        datiPanel.add(passwordLabel);
        datiPanel.add(passwordField);

        // ADMIN
        JLabel adminLabel = new JLabel("Admin:", SwingConstants.LEFT);
        adminCheckBox = new JCheckBox();
        adminCheckBox.setSelected(adminSelezionato);
        datiPanel.add(adminLabel);
        datiPanel.add(adminCheckBox);

        // AGGIUNGI DATI AL PANNELLO
        leftPanel.add(datiPanel);
        leftPanel.setPreferredSize(new Dimension(700, 600));
        panel.add(leftPanel, BorderLayout.CENTER);




//-----------------------------------------------TABELLE-----------------------------------------------------------------//
        // Creiamo il pannello destro per la tabella tag_utente
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("ID Foto scattate:"));

        // TABELLA FOTO POSSEDUTE
        ArrayList<Integer> listaFotoAssociate = controller.VediFotografiaPerUtente(usernameSelezionato);
        DefaultTableModel tabellaFotografieModel = new DefaultTableModel();
        Object[][] data = new Object[listaFotoAssociate.size()][1];
        for (int i = 0; i < listaFotoAssociate.size(); i++) {
            data[i][0] = listaFotoAssociate.get(i);

        }
        tabellaFotografieModel.setDataVector(data, new Object[]{"ID"});
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





















        //BOTTONI



        // Crea il pannello dei bottoni
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton bottoneSalva = new JButton("Salva modifiche");
        JButton bottoneAnnulla = new JButton("Annulla modifiche");
        JButton bottoneAggiungiAfferenza = new JButton("Aggiungi afferenza");
        JButton bottoneRimuoviAfferenza = new JButton("Rimuovi afferenza");
        panelBottoniLeft.add(bottoneSalva);
        panelBottoniLeft.add(bottoneAnnulla);

        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);


        // Aggiungo i pannelli alla finestra principale
        add(panel,BorderLayout.CENTER);



        // Logica per salvare le modifiche
        bottoneSalva.addActionListener(e -> {
            setVisible(false);

            String passwordModificata = passwordField.getText();
            boolean adminModificato = adminCheckBox.isSelected();

            try {

                controller.modificaUtente(usernameSelezionato, passwordModificata, adminModificato);
                JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante la modifica dei dati dell'utente:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } finally {
                dispose();
                framePadre.setVisible(true);
            }
            //}

        });


        //bottone che torna alla vista impiegati
        bottoneAnnulla.addActionListener(e -> {
            //chiudo la finestra di dialogo
            dispose();
            framePadre.setVisible(true);
        });








        // Aggiungo i pannelli alla finestra principale
        add(panel,BorderLayout.CENTER);
        add(panelBottoni, BorderLayout.SOUTH);


        // Rendo non modificabili alcuni campi
        usernameField.setEditable(false);







        // Imposto la dimensione della finestra e la rendo visibile
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

        //QUESTO METODO SERVE A RENDERE MODALE LA FINESTRA, IN MODO DA DISATTIVARE LA FINESTRA PADRE MENTRE E ATTIVA QUELLA DI DIALOGO
        //NON LA UTILIZZIAMO PERCHE UNA VOLTA SETTATA A TRUE RISULTANO ALCUNI COMPORTAMENTI GRAFICI ANOMALI NELL APP

        //setModal(true);

        setVisible(true);


    }
/*


*/


















}
/**
        // Creiamo il pannello inferiore per la tabella dello storico
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Collezioni:"));

        ArrayList<Integer> listaCollezioni = controller.VediCollezioniPerUtente(usernameSelezionato);
        DefaultTableModel tabellaCollezioniModel = new DefaultTableModel();
        JTable tabellaCollezioni = new JTable(tabellaCollezioniModel);
        tabellaCollezioni.setEnabled(false);
        bottomPanel.add(new JScrollPane(tabellaCollezioni), BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setPreferredSize(new Dimension(1000,75));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        tabellaCollezioni.getColumnModel().getColumn(0).setCellRenderer(renderer);

        tabellaCollezioni.setDefaultEditor(Object.class, null);
        tabellaCollezioni.setDefaultEditor(Object.class, null);
        tabellaCollezioni.getTableHeader().setReorderingAllowed(false);
        tabellaCollezioni.setShowGrid(true);
        //COLORI TABELLA
        tabellaCollezioni.setGridColor(Color.DARK_GRAY);
        tabellaCollezioni.setBackground(Color.DARK_GRAY);
        tabellaCollezioni.getTableHeader().setBackground(Color.DARK_GRAY);
        tabellaCollezioni.getTableHeader().setForeground(Color.WHITE);



        //BOTTONI


        // Crea il pannello dei bottoni
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton bottoneSalva = new JButton("Salva modifiche");
        JButton bottoneAnnulla = new JButton("Annulla modifiche");
        JButton bottoneAggiungiAfferenza = new JButton("Aggiungi afferenza");
        JButton bottoneRimuoviAfferenza = new JButton("Rimuovi afferenza");
        panelBottoniLeft.add(bottoneSalva);
        panelBottoniLeft.add(bottoneAnnulla);
        panelBottoniRight.add(bottoneAggiungiAfferenza);
        panelBottoniRight.add(bottoneRimuoviAfferenza);

        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);


        // Logica per salvare le modifiche
        bottoneSalva.addActionListener(e -> {
            setVisible(false);

            float stipendioModificato = ((Number) stipendioSpinner.getValue()).floatValue();
            boolean dirigenteModificato = dirigenteCheckBox.isSelected();
            String curriculumModificato = curriculumTextArea.getText();
            Date dataLicenziamentoModificata = dataLicenziamentoChooser.getDate();
            java.sql.Date sqlDataLicenziamento = null;
            if(dataLicenziamentoChooser.getDate() != null) {
                sqlDataLicenziamento = new java.sql.Date(dataLicenziamentoModificata.getTime());
            }


            try {

                controller.modificaUtente(usernameSelezionato, password, admin);
                JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante la modifica dei dati dell'utente:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } finally {
                dispose();
                framePadre.setVisible(true);
            }
            //}

        });


        //bottone che torna alla vista utenti
        bottoneAnnulla.addActionListener(e -> {
            //chiudo la finestra di dialogo
            dispose();
            framePadre.setVisible(true);
        });


        // Logica per aggiungere afferenza
        bottoneAggiungiAfferenza.addActionListener(e -> {
            // Creazione e visualizzazione della finestra di dialogo
            JDialog dialog = new JDialog(); // Puoi utilizzare una classe di finestra di dialogo personalizzata
            dialog.setModal(true); // Imposta la finestra di dialogo come modale per bloccare l'interazione con la finestra principale
            dialog.setTitle("Aggiungi afferenza:\n");

            //creo la tabella
            DefaultTableModel tabellaAfferenzaModel = new DefaultTableModel();
            tabellaAfferenzaModel.addColumn("ID");
            ArrayList<String> listaCodLab = controller.getListaCodiciLaboratoriGUI();
            //rimuovo dalla lista dei laboratori quelli a cui l'impiegato afferisce gia
            listaCodLab.removeAll(listaLabAfferiti);
            //riempio la tabella
            Object[][] data1 = new Object[listaCodLab.size()][1];
            for (int i = 0; i < listaCodLab.size(); i++) {
                data1[i][0] = listaCodLab.get(i);
            }
            tabellaAfferenzaModel.setDataVector(data1, new Object[]{"ID"});
            JTable tabellaCodiciLab = new JTable(tabellaAfferenzaModel);
            tabellaCodiciLab.setShowGrid(true);

            //allineo il testo delle colonne al centro
            DefaultTableCellRenderer centerRenderer1 = new DefaultTableCellRenderer();
            centerRenderer1.setHorizontalAlignment(SwingConstants.CENTER);
            // Applicazione del renderizzatore personalizzato a tutte le colonne
            int columnCount1 = tabellaCodiciLab.getColumnCount();
            for (int i = 0; i < columnCount1; i++) {
                tabellaCodiciLab.getColumnModel().getColumn(i).setCellRenderer(centerRenderer1);
            }

            // Aggiungi la tabella a un componente di scorrimento
            JScrollPane scrollPane1 = new JScrollPane(tabellaCodiciLab);
            // Aggiungi il componente di scorrimento alla finestra di dialogo
            dialog.add(scrollPane1);

            // Quando l'utente tocca un codice, parte questo Listener
            tabellaCodiciLab.getSelectionModel().addListSelectionListener(e1 -> {
                // Ottieni il codice selezionato
                int selectedRow = tabellaCodiciLab.getSelectedRow();
                String codLabSelezionato = (String) tabellaCodiciLab.getValueAt(selectedRow, 0);

                // L utente ha selezionato una colonna
                int response = JOptionPane.showOptionDialog(dialog, "Aggiungo alla matricola " + matricolaSelezionata + " l'afferenza al laboratorio " + codLabSelezionato + "?", "Conferma Salvataggio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");
                if (response == JOptionPane.YES_OPTION) {
                    //aggiungo l'afferenza al codLabSelezionato
                    try {
                        controller.aggiungiAfferenza(matricolaSelezionata,codLabSelezionato);
                        dialog.setVisible(false);
                        JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'aggiunta dell'afferenza al laboratorio:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        dialog.dispose();
                    }
                    updateTabella(controller,matricolaSelezionata);
                }
            });

            // Imposta le dimensioni e la posizione della finestra di dialogo
            dialog.setSize(250, 400);
            dialog.setLocationRelativeTo(null); // Posiziona la finestra di dialogo al centro dello schermo
            dialog.setVisible(true);
        });


        // Logica per rimuovere afferenza
        bottoneRimuoviAfferenza.addActionListener(e -> {
            int selectedRow = tabellaAfferenze.getSelectedRow();
            int selectedColumn = tabellaAfferenze.getSelectedColumn();

            if (selectedRow != -1 && selectedColumn != -1) {
                // La matricola si trova nella prima colonna della tabella
                String codLabSelezionato = tabellaAfferenze.getValueAt(tabellaAfferenze.getSelectedRow(), 0).toString();
                int response = JOptionPane.showOptionDialog( panel, "Sei sicuro di voler eliminare l'afferenza al laboratorio " + codLabSelezionato + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                if (response == JOptionPane.YES_OPTION) {
                    //elimino l'afferenza
                    try {
                        controller.eliminaAfferenza(matricolaSelezionata,codLabSelezionato);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione dell'afferenza:\n" + ex.getMessage(), "Errore di eliminazione", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                    //aggiorno la tabella appena dopo l'eliminazione dell'impiegato
                    updateTabella(controller,matricolaSelezionata);
                }
            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(panel, "Seleziona un codice laboratorio per eliminarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });



        // Aggiungo i pannelli alla finestra principale
        add(panel,BorderLayout.CENTER);
        add(panelBottoni, BorderLayout.SOUTH);


        // Rendo non modificabili alcuni campi
        matricolaField.setEditable(false);
        nomeField.setEditable(false);
        cognomeField.setEditable(false);
        codiceFiscaleField.setEditable(false);
        sessoField.setEditable(false);
        tipoImpiegatoField.setEditable(false);
        dataAssunzioneChooser.setEditable(false);


        // Imposto la dimensione della finestra e la rendo visibile
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

        //QUESTO METODO SERVE A RENDERE MODALE LA FINESTRA, IN MODO DA DISATTIVARE LA FINESTRA PADRE MENTRE E ATTIVA QUELLA DI DIALOGO
        //NON LA UTILIZZIAMO PERCHE UNA VOLTA SETTATA A TRUE RISULTANO ALCUNI COMPORTAMENTI GRAFICI ANOMALI NELL APP

        //setModal(true);

        setVisible(true);
    }

    private void updateTabella(Controller controller, String matricolaSelezionata) {
        //load dei nuovi dati
        ArrayList<String> listaLabAfferiti = controller.leggiAfferenzeImpiegato(matricolaSelezionata);
        Object[][] nuoviDati = new Object[listaLabAfferiti.size()][listaLabAfferiti.size()];
        for (int i = 0; i < listaLabAfferiti.size(); i++) {
            nuoviDati[i][0] = listaLabAfferiti.get(i);
        }
        // Aggiungi le nuove righe alla tabella
        DefaultTableModel model = (DefaultTableModel) tabellaAfferenze.getModel();
        model.setDataVector(nuoviDati,new Object[]{"ID"});
    }



 */
