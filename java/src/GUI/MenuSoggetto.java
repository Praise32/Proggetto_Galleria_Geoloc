package GUI;

import CONTROLLER.Controller;
import MAIN.Main;
import MAIN.User;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The type Menu fotografie gui.
 */

public class MenuFotografieGUI {

    private final JFrame frameMenuFotografie;
    private final JTable table;
    private final JTextField searchBar;

    /**
     * Instantiates a new Menu fotografie gui.
     *
     * @param controller          the controller
     * @param frameMenuPrincipale the frame menu principale
     */


    public MenuFotografieGUI(Controller controller, JFrame frameMenuPrincipale) {
//----------------------------------------------FINESTRA--------------------------------------------------------//

        frameMenuFotografie = new JFrame("Finestra Fotografie");
        frameMenuFotografie.setSize(800, 600);
        frameMenuFotografie.setLocationRelativeTo(null);
        frameMenuFotografie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//----------------------------------------------TABELLA FOTOGRAFIE--------------------------------------------------------//


        //Creazione
        String[] colonneTabella = {"Id_foto", "Autore", "Dati Foto", "Dispositivo", "Data Foto", "latitudine", "longitudine", "condivisa", "titolo"};
        ArrayList<Integer> listaIdFoto = new ArrayList<>();
        ArrayList<String> listaAutoreFoto = new ArrayList<>();
        ArrayList<byte[]> listaDatiFoto = new ArrayList<>();
        ArrayList<String> listaDispositivoFoto = new ArrayList<>();
        ArrayList<java.sql.Timestamp> listaDatafoto = new ArrayList<>();
        ArrayList<Float> listaLatitudineFoto = new ArrayList<>();
        ArrayList<Float> listaLongitudineFoto = new ArrayList<>();
        ArrayList<Boolean> listaCondivisaFoto = new ArrayList<>();
        ArrayList<String> listaTitoloFoto = new ArrayList<>();

        controller.getListaFotografiaGUI(listaIdFoto, listaAutoreFoto, listaDatiFoto, listaDispositivoFoto, listaDatafoto, listaLatitudineFoto, listaLongitudineFoto, listaCondivisaFoto, listaTitoloFoto);

        Object[][] data = new Object[listaIdFoto.size()][9];
        for (int i = 0; i < listaIdFoto.size(); i++) {
            data[i][0] = listaIdFoto.get(i);
            data[i][1] = listaAutoreFoto.get(i);
            data[i][2] = listaDatiFoto.get(i);
            data[i][3] = listaDispositivoFoto.get(i);
            data[i][4] = listaDatafoto.get(i);
            data[i][5] = listaLatitudineFoto.get(i);
            data[i][6] = listaLongitudineFoto.get(i);
            data[i][7] = listaCondivisaFoto.get(i);
            data[i][8] = listaTitoloFoto.get(i);
        }

        // Creiamo il modello di tabella
        DefaultTableModel modelloTabella = new DefaultTableModel(data, colonneTabella);

        // Creiamo la tabella
        table = new JTable(modelloTabella);

        // Creiamo il TableRowSorter con il tipo di modello di tabella corretto
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelloTabella);
        sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));

        // Impostiamo il TableRowSorter sulla tabella
        table.setRowSorter(sorter);
        table.setDefaultEditor(Object.class, null);
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowGrid(true);

        //COLORI TABELLA
        table.setGridColor(Color.WHITE);
        table.setBackground(Color.DARK_GRAY);
        table.setForeground(Color.WHITE);
        table.getTableHeader().setBackground(Color.BLACK);
        table.getTableHeader().setForeground(Color.WHITE);

        //barra di scorrimento
        JScrollPane scrollPane = new JScrollPane(table);
        frameMenuFotografie.add(scrollPane, BorderLayout.CENTER);


//----------------------------------------------BARRA DI RICERCA---------------------------------------------------------------//

        // Creiamo la barra di ricerca per autore
        searchBar = new JTextField(20);
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(searchBar.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search(searchBar.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search(searchBar.getText());
            }

            public void search(String searchString) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchString, 1));
            }
        });

        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSearch.add(new JLabel("Cerca per autore: "));
        panelSearch.add(searchBar);

        frameMenuFotografie.add(panelSearch, BorderLayout.NORTH);
        frameMenuFotografie.setVisible(true);


//----------------------------------------------BOTTONI---------------------------------------------------------------//
        //MENU PRINCIPALE
        JButton bottoneMenuPrincipale = new JButton("Menù Principale");
        bottoneMenuPrincipale.addActionListener(e -> {
            frameMenuFotografie.dispose();
            frameMenuPrincipale.setVisible(true);
        });


        //BOTTONE ELIMINA FOTOGRAFIA
        JButton bottoneElimina = new JButton("Elimina");
        bottoneElimina.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();

            if (selectedRow != -1 && selectedColumn != -1) {
                // La foto si trova nella prima colonna della tabella
                int fotoSelezionata = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());

                //Variabile per avviare o meno la funzione di eliminazione
                boolean ownerCheck;

                try {
                    ownerCheck = controller.controlloProprietario(fotoSelezionata, User.getInstance().getUsername());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if (ownerCheck) {
                    int response = JOptionPane.showOptionDialog(frameMenuFotografie, "Sei sicuro di voler eliminare la fotografia " + fotoSelezionata + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                    if (response == JOptionPane.YES_OPTION) {
                        //elimino l'utente con l'username selezionata
                        try {
                            controller.eliminaFotografia(fotoSelezionata);
                        } catch (PSQLException ex) {
                            JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione dei dati della fotografia:\n" + ex.getMessage(), "Errore di Eliminazione", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception ee) {
                            JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                        //aggiorno la tabella appena dopo l'eliminazione dell'utente
                        updateTable(controller, colonneTabella);
                    }
                } else { JOptionPane.showMessageDialog(null, "Puoi eliminare solo foto di cui sei il proprietario!");
                }

            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(frameMenuFotografie, "Seleziona una fotografia per eliminarla.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        // BOTTONE INSERISCI FOTOGRAFIA
        JButton bottoneInserisci = new JButton("Inserisci");
        bottoneInserisci.addActionListener(e -> {
            InserimentoFotografiaGUI dialog = new InserimentoFotografiaGUI(controller, frameMenuFotografie);
            frameMenuFotografie.setVisible(false);
            dialog.setVisible(true);
            // Aggiungo un listener per la finestra di dialogo
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // Chiamo il metodo updateTable() dopo la chiusura della finestra di dialogo
                    updateTable(controller, colonneTabella);

                }
            });
        });

        //BOTTONE PROFILO FOTO

        JButton bottoneProfiloFotografia = new JButton("Profilo Fotografia");
        bottoneProfiloFotografia.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();
            // L'utente ha selezionato una cella
            if (selectedRow != -1 && selectedColumn != -1) {
                // l'username è nella prima colonna della tabella
                int fotoSelezionata = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                try {
                    //Variabile che controlla che la foto selezionata appartenga all'utente che ha fatto l'accesso, usata per pulizia del codice
                    boolean ownerCheck = controller.controlloProprietario(fotoSelezionata, User.getInstance().getUsername());

                    if (ownerCheck) {
                        ViewFotografiaGUI profiloFoto = new ViewFotografiaGUI(fotoSelezionata, controller, frameMenuFotografie);
                        frameMenuFotografie.setVisible(false);
                        // Mostro la finestra di dialogo
                        profiloFoto.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Non hai i permessi per modificare questa foto");
                    }

                } catch (java.sql.SQLException ex) {
                    // Gestisci l'eccezione qui, ad esempio mostrando un messaggio di errore
                    ex.printStackTrace(); // Stampa la traccia dell'eccezione
                }
            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(frameMenuFotografie, "Seleziona una fotografia per continuare", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Aggiungiamo i pulsanti alla finestra
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBottoniLeft.add(bottoneMenuPrincipale);
        panelBottoniRight.add(bottoneInserisci);
        panelBottoniRight.add(bottoneElimina);
        panelBottoniRight.add(bottoneProfiloFotografia);


        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);
        frameMenuFotografie.add(panelBottoni, BorderLayout.SOUTH);
        frameMenuFotografie.setVisible(true);


    }

    private void updateTable(Controller controller, String[] colonneTabella) {

        //LOAD DEI NUOVI DATI
        ArrayList<Integer> listaIdFoto = new ArrayList<>();
        ArrayList<String> listaAutoreFoto = new ArrayList<>();
        ArrayList<byte[]> listaDatiFoto = new ArrayList<>();
        ArrayList<String> listaDispositivoFoto = new ArrayList<>();
        ArrayList<java.sql.Timestamp> listaDatafoto = new ArrayList<>();
        ArrayList<Float> listaLatitudineFoto = new ArrayList<>();
        ArrayList<Float> listaLongitudineFoto = new ArrayList<>();
        ArrayList<Boolean> listaCondivisaFoto = new ArrayList<>();
        ArrayList<String> listaTitoloFoto = new ArrayList<>();

        controller.getListaFotografiaGUI(listaIdFoto, listaAutoreFoto, listaDatiFoto, listaDispositivoFoto, listaDatafoto, listaLatitudineFoto, listaLongitudineFoto, listaCondivisaFoto, listaTitoloFoto);

        Object[][] newdata = new Object[listaIdFoto.size()][9];
        for (int i = 0; i < listaIdFoto.size(); i++) {
            newdata[i][0] = listaIdFoto.get(i);
            newdata[i][1] = listaAutoreFoto.get(i);
            newdata[i][2] = listaDatiFoto.get(i);
            newdata[i][3] = listaDispositivoFoto.get(i);
            newdata[i][4] = listaDatafoto.get(i);
            newdata[i][5] = listaLatitudineFoto.get(i);
            newdata[i][6] = listaLongitudineFoto.get(i);
            newdata[i][7] = listaCondivisaFoto.get(i);
            newdata[i][8] = listaTitoloFoto.get(i);
        }

        //CODICE PER AGGIORNARE LA TABELLA CON I NUOVI DATI
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(newdata, colonneTabella);

    }


}
