package GUI;

import CONTROLLER.Controller;
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
 * The type Menu impiegati gui.
 */

public class MenuUtenti {

    private final JFrame frameMenuUtenti;
    private final JTable table;
    private final JTextField searchBar;

    /**
     * Instantiates a new Menu impiegati gui.
     *
     * @param controller          the controller
     * @param frameMenuPrincipale the frame menu principale
     */


    public MenuUtenti(Controller controller, JFrame frameMenuPrincipale) {
//----------------------------------------------FINESTRA--------------------------------------------------------//

        frameMenuUtenti = new JFrame("Finestra Utenti");
        frameMenuUtenti.setSize(800, 600);
        frameMenuUtenti.setLocationRelativeTo(null);
        frameMenuUtenti.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//----------------------------------------------TABELLA UTENTI--------------------------------------------------------//

        //Creazione
        String[] colonneTabella = {"Username", "Password", "Admin"};
        ArrayList<String> listaUsername = (ArrayList<String>) controller.getListaUtentiUsernameGUI();
        ArrayList<String> listaPassword = (ArrayList<String>) controller.getListaUtentiPasswordGUI();
        ArrayList<Boolean> listaAdmin = (ArrayList<Boolean>) controller.getListaUtentiAdminGUI();
        Object[][] data = new Object[listaUsername.size()][3];
        for (int i = 0; i < listaUsername.size(); i++) {
            data[i][0] = listaUsername.get(i);
            data[i][1] = listaPassword.get(i);
            data[i][2] = listaAdmin.get(i);
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
        frameMenuUtenti.add(scrollPane, BorderLayout.CENTER);


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
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchString, 0));
            }
        });

        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSearch.add(new JLabel("Cerca per username: "));
        panelSearch.add(searchBar);

        frameMenuUtenti.add(panelSearch, BorderLayout.NORTH);
        frameMenuUtenti.setVisible(true);


//----------------------------------------------BOTTONI---------------------------------------------------------------//
        //MENU PRINCIPALE
        JButton bottoneMenuPrincipale = new JButton("Menù Principale");
        bottoneMenuPrincipale.addActionListener(e -> {
            frameMenuUtenti.dispose();
            frameMenuPrincipale.setVisible(true);
        });


        //BOTTONE INSERISCI UTENTE
        JButton bottoneInserisci = new JButton("Inserisci");
        bottoneInserisci.addActionListener(e -> {
            boolean check;
            try {
                check = controller.controlloAdmin(User.getInstance().getUsername());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            if (!check) {
                JOptionPane.showMessageDialog(null, "Non posiedi le autorizzazioni necessarie");

            } else {
                InserimentoUtenteGUI dialog = new InserimentoUtenteGUI(controller, frameMenuUtenti);
                frameMenuUtenti.setVisible(false);
                dialog.setVisible(true);
                // Aggiungo un listener per la finestra di dialogo
                dialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        // Chiamo il metodo updateTable() dopo la chiusura della finestra di dialogo
                        updateTable(controller, colonneTabella);
                    }
                });
            }
        });

        //BOTTONE ELIMINA UTENTE
        JButton bottoneElimina = new JButton("Elimina");
        bottoneElimina.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();

            if (selectedRow != -1 && selectedColumn != -1) {
                // L'utente si trova nella prima colonna della tabella
                String usernameSelezionato = table.getValueAt(table.getSelectedRow(), 0).toString();
                int response = JOptionPane.showOptionDialog(frameMenuUtenti, "Sei sicuro di voler eliminare l'utente " + usernameSelezionato + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                if (response == JOptionPane.YES_OPTION) {
                    //elimino l'utente con l'username selezionata
                    try {
                        controller.eliminaUtente(usernameSelezionato);
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
                JOptionPane.showMessageDialog(frameMenuUtenti, "Seleziona un utente per eliminarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });


        //BOTTONE PROFILO UTENTE

        JButton bottoneProfiloUtente = new JButton("Profilo Utente");
        bottoneProfiloUtente.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();
            // L'utente ha selezionato una cella
            if (selectedRow != -1 && selectedColumn != -1) {
                // l'username è nella prima colonna della tabella
                String usernameSelezionato = table.getValueAt(table.getSelectedRow(), 0).toString();

                //Variabili di controllo per assicurarsi che l'utente non possa modificare altri utenti
                //a meno che non sia admin
                String tmpAccessedUser = User.getInstance().getUsername();
                boolean tmpUserAdminFlag;
                try {
                    tmpUserAdminFlag = controller.controlloAdmin(tmpAccessedUser);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if(usernameSelezionato.equals(tmpAccessedUser) || tmpUserAdminFlag) {
                    try {

                        ViewUserGUI profiloUtente = new ViewUserGUI(usernameSelezionato, controller, frameMenuUtenti);
                        frameMenuUtenti.setVisible(false);
                        profiloUtente.setVisible(true);

                    } catch (java.sql.SQLException ex) {
                        ex.printStackTrace(); // Stampa la traccia dell'eccezione
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Non puoi modificare altri utenti!");
                }
            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(frameMenuUtenti, "Seleziona un utente per continuare", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Variabile usata per controllare quali pulsanti inserire in base ai permessi
        boolean adminCheck;
        try {
            adminCheck = controller.controlloAdmin(User.getInstance().getUsername());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Aggiungiamo i pulsanti alla finestra
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBottoniLeft.add(bottoneMenuPrincipale);

        //Pulsanti per funzioni da admin
        if(adminCheck){
            panelBottoniRight.add(bottoneInserisci);
            panelBottoniRight.add(bottoneElimina);
        }

        panelBottoniRight.add(bottoneProfiloUtente);
        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);

        frameMenuUtenti.add(panelBottoni, BorderLayout.SOUTH);


    }

    private void updateTable(Controller controller, String[] colonneTabella) {

        //LOAD DEI NUOVI DATI
        ArrayList<String> listaUsername = (ArrayList<String>) controller.getListaUtentiUsernameGUI();
        ArrayList<String> listaPassword = (ArrayList<String>) controller.getListaUtentiPasswordGUI();
        ArrayList<Boolean> listaAdmin = (ArrayList<Boolean>) controller.getListaUtentiAdminGUI();
        Object[][] nuoviDati = new Object[listaUsername.size()][3];
        for (int i = 0; i < listaUsername.size(); i++) {
            nuoviDati[i][0] = listaUsername.get(i);
            nuoviDati[i][1] = listaPassword.get(i);
            nuoviDati[i][2] = listaAdmin.get(i);
        }

        //CODICE PER AGGIORNARE LA TABELLA CON I NUOVI DATI
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(nuoviDati, colonneTabella);
    }


}
