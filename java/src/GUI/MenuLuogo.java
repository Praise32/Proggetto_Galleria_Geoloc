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
 * The type Menu Video gui.
 */

public class MenuLuogo
{

    private final JFrame frameMenuLuogo;
    private final JTable table;
    private final JTextField searchBar;

    /**
     * Instantiates a new Menu impiegati gui.
     *
     * @param controller          the controller
     * @param frameMenuPrincipale the frame menu principale
     * */


    public MenuLuogo(Controller controller, JFrame frameMenuPrincipale) {
//----------------------------------------------FINESTRA--------------------------------------------------------//

        frameMenuLuogo = new JFrame("Finestra Video");
        frameMenuLuogo.setSize(800, 600);
        frameMenuLuogo.setLocationRelativeTo(null);
        frameMenuLuogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//----------------------------------------------TABELLA LUOGO--------------------------------------------------------//

        //Creazione
        String[] colonneTabella = {"Latitudine", "Longitudine", "Nome", "Descrizione"};
        ArrayList<Float> listaLatitudine = new ArrayList<>();
        ArrayList<Float> listaLongitudine = new ArrayList<>();
        ArrayList<String> listaNome = new ArrayList<>();
        ArrayList<String> listaDescrizione = new ArrayList<>();

        controller.getListaLuogoGUI(listaLatitudine, listaLongitudine, listaNome, listaDescrizione);
        Object[][] data = new Object[listaLatitudine.size()][4];
        for (int i = 0; i < listaLatitudine.size(); i++) {
            data[i][0] = listaLatitudine.get(i);
            data[i][1] = listaLongitudine.get(i);
            data[i][2] = listaNome.get(i);
            data[i][3] = listaDescrizione.get(i);
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
        frameMenuLuogo.add(scrollPane, BorderLayout.CENTER);


//----------------------------------------------BARRA DI RICERCA---------------------------------------------------------------//

        // Creiamo la barra di ricerca
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
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchString, 2));
            }
        });

        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSearch.add(new JLabel("Cerca per nome luogo: "));
        panelSearch.add(searchBar);

        frameMenuLuogo.add(panelSearch, BorderLayout.NORTH);
        frameMenuLuogo.setVisible(true);


//----------------------------------------------BOTTONI---------------------------------------------------------------//
        //MENU PRINCIPALE
        JButton bottoneMenuPrincipale = new JButton("Menù Principale");
        bottoneMenuPrincipale.addActionListener(e -> {
            frameMenuLuogo.dispose();
            frameMenuPrincipale.setVisible(true);
        });


        //BOTTONE INSERISCI LUOGO
        JButton bottoneInserisci = new JButton("Inserisci");
        bottoneInserisci.addActionListener(e -> {
            InserimentoLuogoGUI dialog = new InserimentoLuogoGUI(controller, frameMenuLuogo);
            frameMenuLuogo.setVisible(false);
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

        //BOTTONE ELIMINA LUOGO
        JButton bottoneElimina = new JButton("Elimina");
        bottoneElimina.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();

            if (selectedRow != -1 && selectedColumn != -1) {
                // L'utente si trova nella prima colonna della tabella
                String usernameSelezionato = table.getValueAt(table.getSelectedRow(), 0).toString();
                int response = JOptionPane.showOptionDialog(frameMenuLuogo, "Sei sicuro di voler eliminare il luogo" + usernameSelezionato + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                if (response == JOptionPane.YES_OPTION) {
                    //elimino l'utente con l'username selezionata
                    try {
                        controller.eliminaUtente(usernameSelezionato);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione dei dati del luogo:\n" + ex.getMessage(), "Errore di Eliminazione", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                    //aggiorno la tabella appena dopo l'eliminazione dell'utente
                    updateTable(controller,colonneTabella);
                }
            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(frameMenuLuogo, "Seleziona un luogo per eliminarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });


        //BOTTONE PROFILO UTENTE

        JButton bottoneProfiloVideo = new JButton("Classifica Luoghi");
        bottoneProfiloVideo.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();
            // L'utente ha selezionato una cella
            if (selectedRow != -1 && selectedColumn != -1) {
                // l'username è nella prima colonna della tabella
                String usernameSelezionato = table.getValueAt(table.getSelectedRow(), 0).toString();
                try {
                    // Creo un'istanza della finestra di dialogo ProfiloImpiegato
                    ViewUserGUI profiloUtente = new ViewUserGUI(usernameSelezionato, controller, frameMenuLuogo);
                    frameMenuLuogo.setVisible(false);
                    // Mostro la finestra di dialogo
                    profiloUtente.setVisible(true);
                } catch (java.sql.SQLException ex) {
                    // Gestisci l'eccezione qui, ad esempio mostrando un messaggio di errore
                    ex.printStackTrace(); // Stampa la traccia dell'eccezione
                }
            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(frameMenuLuogo, "Seleziona un luogo per continuare", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });



        // Aggiungiamo i pulsanti alla finestra
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBottoniLeft.add(bottoneMenuPrincipale);
        panelBottoniRight.add(bottoneInserisci);
        panelBottoniRight.add(bottoneElimina);
        panelBottoniRight.add(bottoneProfiloVideo);


        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);
        frameMenuLuogo.add(panelBottoni, BorderLayout.SOUTH);









    }

    private void updateTable(Controller controller,String[] colonneTabella) {

        //LOAD DEI NUOVI DATI
        ArrayList<Float> listaLatitudine = new ArrayList<>();
        ArrayList<Float> listaLongitudine = new ArrayList<>();
        ArrayList<String> listaNome = new ArrayList<>();
        ArrayList<String> listaDescrizione = new ArrayList<>();

        controller.getListaLuogoGUI(listaLatitudine, listaLongitudine, listaNome, listaDescrizione);
        Object[][] newdata = new Object[listaLatitudine.size()][4];
        for (int i = 0; i < listaLatitudine.size(); i++) {
            newdata[i][0] = listaLatitudine.get(i);
            newdata[i][1] = listaLongitudine.get(i);
            newdata[i][2] = listaNome.get(i);
            newdata[i][3] = listaDescrizione.get(i);
        }


        //CODICE PER AGGIORNARE LA TABELLA CON I NUOVI DATI
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(newdata, colonneTabella);
    }






}