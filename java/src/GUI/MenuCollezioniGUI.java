package GUI;
import CONTROLLER.Controller;
import org.postgresql.util.PSQLException;

import java.sql.Timestamp;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.util.Date;
import java.text.SimpleDateFormat;
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

public class MenuCollezioniGUI
{

    private final JFrame frameMenuCollezioni;
    private final JTable table;
    private final JTextField searchBar;

    /**
     * Instantiates a new Menu impiegati gui.
     *
     * @param controller          the controller
     * @param frameMenuPrincipale the frame menu principale
     * */


    public MenuCollezioniGUI(Controller controller, JFrame frameMenuPrincipale) {
//----------------------------------------------FINESTRA--------------------------------------------------------//

        frameMenuCollezioni = new JFrame("Finestra Collezioni");
        frameMenuCollezioni.setSize(800, 600);
        frameMenuCollezioni.setLocationRelativeTo(null);
        frameMenuCollezioni.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//----------------------------------------------TABELLA COLLEZIONI--------------------------------------------------------//

        String[] colonneTabella = {"Id Collezione", "Username", "Titolo", "Data Collezione", "Numero Elementi"};
        ArrayList<Integer> listaIdCollezione = new ArrayList<>();
        ArrayList<String> listaUsername = new ArrayList<>();
        ArrayList<String> listaTitolo = new ArrayList<>();
        ArrayList<java.sql.Timestamp> listaDataCollezioni= new ArrayList<>();
        ArrayList<Integer> listaNumeroElementi = new ArrayList<>();

        controller.getListaCollezioniGUI(listaIdCollezione,listaUsername, listaTitolo, listaDataCollezioni, listaNumeroElementi);
        Object[][] data = new Object[listaUsername.size()][5];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < listaIdCollezione.size(); i++) {
            data[i][0] = listaIdCollezione.get(i);
            data[i][1] = listaUsername.get(i);
            data[i][2] = listaTitolo.get(i);
            data[i][3] = dateFormat.format(listaDataCollezioni.get(i)); // Converti Timestamp in stringa
            data[i][4] = listaNumeroElementi.get(i);
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
        frameMenuCollezioni.add(scrollPane, BorderLayout.CENTER);


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
        panelSearch.add(new JLabel("Cerca per nome collezione: "));
        panelSearch.add(searchBar);

        frameMenuCollezioni.add(panelSearch, BorderLayout.NORTH);
        frameMenuCollezioni.setVisible(true);


//----------------------------------------------BOTTONI---------------------------------------------------------------//
        //MENU PRINCIPALE
        JButton bottoneMenuPrincipale = new JButton("Menù Principale");
        bottoneMenuPrincipale.addActionListener(e -> {
            frameMenuCollezioni.dispose();
            frameMenuPrincipale.setVisible(true);
        });

// BOTTONE INSERISCI COLLEZIONE
        JButton bottoneInserisci = new JButton("Inserisci");
        bottoneInserisci.addActionListener(e -> {
            InserimentoCollezioneGUI dialog = new InserimentoCollezioneGUI(controller, frameMenuCollezioni);
            frameMenuCollezioni.setVisible(false);
            dialog.setVisible(true);
            // Aggiungo un listener per la finestra di dialogo
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // Chiamo il metodo updateTable() dopo la chiusura della finestra di dialogo
                    loadTable(controller, colonneTabella);

                    // Aggiungi qui eventuali altre azioni dopo l'inserimento della collezione
                }
            });
        });
//BOTTONE ELIMINA COLLEZIONE
        JButton bottoneElimina = new JButton("Elimina");
        bottoneElimina.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();

            if (selectedRow != -1 && selectedColumn != -1) {
                // La si trova nella prima colonna della tabella
                Object idCollezioneSelezionatoObj = table.getValueAt(table.getSelectedRow(), 0);

                if (idCollezioneSelezionatoObj != null) {
                    String idCollezioneSelezionatoStr = idCollezioneSelezionatoObj.toString();
                    try {
                        int idCollezioneSelezionato = Integer.parseInt(idCollezioneSelezionatoStr);
                        int response = JOptionPane.showOptionDialog(frameMenuCollezioni, "Sei sicuro di voler eliminare la collezione " + idCollezioneSelezionato + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                        if (response == JOptionPane.YES_OPTION) {
                            //elimino la collezione con l'id selezionato
                            try {
                                controller.eliminaCollezione(idCollezioneSelezionato);
                                // Aggiornamento della tabella dopo l'eliminazione con successo
                                loadTable(controller, colonneTabella);
                            } catch (PSQLException ex) {
                                JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione dei dati della collezione:\n" + ex.getMessage(), "Errore di Eliminazione", JOptionPane.ERROR_MESSAGE);
                            } catch (Exception ee) {
                                JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frameMenuCollezioni, "L'ID della collezione selezionata non è un numero valido.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frameMenuCollezioni, "L'ID della collezione selezionata è nullo.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(frameMenuCollezioni, "Seleziona una collezione per eliminarla.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });




        // Aggiungiamo i pulsanti alla finestra
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBottoniRight.add(bottoneInserisci);
        panelBottoniRight.add(bottoneElimina);
        panelBottoniLeft.add(bottoneMenuPrincipale);


        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);
        frameMenuCollezioni.add(panelBottoni, BorderLayout.SOUTH);

    }

    private void loadTable(Controller controller,String[] colonneTabella) {

        //LOAD DEI NUOVI DATI
        ArrayList<Integer> listaIdCollezione = new ArrayList<>();
        ArrayList<String> listaUsername = new ArrayList<>();
        ArrayList<String> listaTitolo = new ArrayList<>();
        ArrayList<java.sql.Timestamp> listaDataCollezioni= new ArrayList<>();
        ArrayList<Integer> listaNumeroElementi = new ArrayList<>();

        controller.getListaCollezioniGUI(listaIdCollezione,listaUsername, listaTitolo, listaDataCollezioni, listaNumeroElementi);

        Object[][] newdata = new Object[listaUsername.size()][5];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < listaIdCollezione.size(); i++) {
            newdata[i][0] = listaIdCollezione.get(i);
            newdata[i][1] = listaUsername.get(i);
            newdata[i][2] = listaTitolo.get(i);
            newdata[i][3] = dateFormat.format(listaDataCollezioni.get(i)); // Converti Timestamp in stringa
            newdata[i][4] = listaNumeroElementi.get(i);
        }

        //CODICE PER AGGIORNARE LA TABELLA CON I NUOVI DATI
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(newdata, colonneTabella);

    }






}


