package GUI;

import CONTROLLER.Controller;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * The type Menu impiegati gui.
 */
public class MenuFotografieGUI {

    private final JFrame frameMenuFotografie;
    private final JTable table;
    private final JTextField searchBar;

    /**
     * Instantiates a new Menu impiegati gui.
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
        ArrayList<Float> listaLatitudineFoto = new ArrayList<>();
        ArrayList<Float> listaLongitudineFoto = new ArrayList<>();
        ArrayList<String> listaDispositivoFoto = new ArrayList<>();
        ArrayList<java.sql.Timestamp> listaDatafoto = new ArrayList<>();
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
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchString, 0));
            }
        });

        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSearch.add(new JLabel("Cerca per username: "));
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
                String fotoSelezionata = table.getValueAt(table.getSelectedRow(), 0).toString();
                int response = JOptionPane.showOptionDialog(frameMenuFotografie, "Sei sicuro di voler eliminare la fotografia " + fotoSelezionata + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                if (response == JOptionPane.YES_OPTION) {
                    //elimino l'utente con l'username selezionata
                    try {
                        controller.eliminaUtente(fotoSelezionata);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione dei dati dell'utente:\n" + ex.getMessage(), "Errore di Eliminazione", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                    //aggiorno la tabella appena dopo l'eliminazione dell'utente
                    updateTable(controller, colonneTabella);
                }
            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(frameMenuFotografie, "Seleziona una fotografia per eliminarla.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Aggiungiamo i pulsanti alla finestra
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBottoniLeft.add(bottoneMenuPrincipale);
        panelBottoniRight.add(bottoneInserisci);
        panelBottoniRight.add(bottoneElimina);
        //panelBottoniRight.add(bottoneProfiloFotografia);


        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);
        frameMenuFotografie.add(panelBottoni, BorderLayout.SOUTH);


    }

    private void updateTable(Controller controller, String[] colonneTabella) {

        //LOAD DEI NUOVI DATI
        ArrayList<Integer> listaIdFoto = (ArrayList<Integer>) controller.getListaFotografieIdFotoGUI();
        ArrayList<String> listaAutore = (ArrayList<String>) controller.getListaFotografieAutoreGUI();
        ArrayList<Byte> listaDatiFoto = (ArrayList<Byte>) controller.getListaFotografieDatiGUI();
        ArrayList<String> listaDispositivoFoto = (ArrayList<String>) controller.getListaFotografieDispositivoGUI();
        ArrayList<Float> listaLatitudinefoto = (ArrayList<Float>) controller.getListaFotografieLatitudineGUI();
        ArrayList<Float> listaLonitudinefoto = (ArrayList<Float>) controller.getListaFotografieLongitudineGUI();
        ArrayList<Boolean> listaCondivisaFoto = (ArrayList<Boolean>) controller.getListaFotografieCondivisaGUI();
        ArrayList<String> listaTitoloFoto = (ArrayList<String>) controller.getListaFotografieTitoloGUI();


        Object[][] newdata = new Object[listaIdFoto.size()][3];
        for (int i = 0; i < listaIdFoto.size(); i++) {
            newdata[i][0] = listaAutore.get(i);
            newdata[i][1] = listaDatiFoto.get(i);
            newdata[i][2] = listaDispositivoFoto.get(i);
            newdata[i][3] = listaLatitudinefoto.get(i);
            newdata[i][4] = listaLonitudinefoto.get(i);
            newdata[i][5] = listaCondivisaFoto.get(i);
            newdata[i][6] = listaTitoloFoto.get(i);
        }

        //CODICE PER AGGIORNARE LA TABELLA CON I NUOVI DATI
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(newdata, colonneTabella);
    }
    // Creiamo il pulsante per aprire la finestra d'inserimento utente

    /**
     * The Bottone inserisci.
     */
    JButton bottoneInserisci = new JButton("Inserisci");


}


/**
 * //BOTTONE PROFILO UTENTE
 * <p>
 * JButton bottoneProfiloUtente = new JButton("Profilo Utente");
 * bottoneProfiloUtente.addActionListener(e -> {
 * int selectedRow = table.getSelectedRow();
 * int selectedColumn = table.getSelectedColumn();
 * // L'utente ha selezionato una cella
 * if (selectedRow != -1 && selectedColumn != -1) {
 * // l'username è nella prima colonna della tabella
 * String usernameSelezionato = table.getValueAt(table.getSelectedRow(), 0).toString();
 * try {
 * // Creo un'istanza della finestra di dialogo ProfiloImpiegato
 * ViewUserGUI profiloUtente = new ViewUserGUI(usernameSelezionato, controller, frameMenuUtenti);
 * frameMenuUtenti.setVisible(false);
 * // Mostro la finestra di dialogo
 * profiloUtente.setVisible(true);
 * } catch (java.sql.SQLException ex) {
 * // Gestisci l'eccezione qui, ad esempio mostrando un messaggio di errore
 * ex.printStackTrace(); // Stampa la traccia dell'eccezione
 * }
 * } else {
 * // L'utente non ha selezionato una cella
 * JOptionPane.showMessageDialog(frameMenuUtenti, "Seleziona un utente per continuare", "Errore", JOptionPane.ERROR_MESSAGE);
 * }
 * });
 * <p>
 * <p>
 * //BOTTONE INSERISCI FOTOGRAFIA
 * bottoneInserisci.addActionListener(e -> {
 * InserimentoFotografiaGUI dialog = new InserimentoFotografiaGUI(controller, frameMenuFotografie);
 * frameMenuFotografie.setVisible(false);
 * dialog.setVisible(true);
 * // Aggiungo un listener per la finestra di dialogo
 * dialog.addWindowListener(new WindowAdapter() {
 *
 * @Override public void windowClosed(WindowEvent e) {
 * // Chiamo il metodo updateTable() dopo la chiusura della finestra di dialogo
 * updateTable(controller, colonneTabella);
 * }
 * });
 * });
 */
