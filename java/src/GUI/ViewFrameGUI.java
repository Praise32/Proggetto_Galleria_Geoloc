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
 * The type Menu impiegati gui.
 */

public class ViewFrameGUI {

    private final JFrame frameMenuFotografie;
    private final JTable table;
    private final JTextField searchBar;
    private final int idvideosec;

    /**
     * Instantiates a new Menu impiegati gui.
     *
     * @param controller          the controller
     * @param frameMenuPrincipale the frame menu principale
     */


    public ViewFrameGUI(int IdVideoSelezionato, Controller controller, JFrame frameMenuPrincipale) throws SQLException {
//----------------------------------------------FINESTRA--------------------------------------------------------//

        frameMenuFotografie = new JFrame("Profilo Video");
        frameMenuFotografie.setSize(800, 600);
        frameMenuFotografie.setLocationRelativeTo(null);
        frameMenuFotografie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//----------------------------------------------TABELLA FOTOGRAFIE--------------------------------------------------------//

        this.idvideosec = IdVideoSelezionato;

        //Creazione
        String[] colonneTabella = {"IdVideo", "Id_foto", "Durata", "Ordine"};

        ArrayList<Integer> listaIdVideo = new ArrayList<>();
        ArrayList<Integer> listaIdFoto = new ArrayList<>();
        ArrayList<Integer> listaDurata = new ArrayList<>();
        ArrayList<Integer> listaOrdine = new ArrayList<>();

        controller.getListaFrameGUI(listaIdVideo,listaIdFoto,listaDurata,listaOrdine,IdVideoSelezionato);


        Object[][] data = new Object[listaIdVideo.size()][4];
        for (int i = 0; i < listaIdVideo.size(); i++) {
            data[i][0] = listaIdVideo.get(i);
            data[i][1] = listaIdFoto.get(i);
            data[i][2] = listaDurata.get(i);
            data[i][3] = listaOrdine.get(i);
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
        panelSearch.add(new JLabel("Cerca Id_Foto: "));
        panelSearch.add(searchBar);

        frameMenuFotografie.add(panelSearch, BorderLayout.NORTH);
        frameMenuFotografie.setVisible(true);


//----------------------------------------------BOTTONI---------------------------------------------------------------//
        //MENU PRINCIPALE
        JButton bottoneMenuPrincipale = new JButton("Indietro");
        bottoneMenuPrincipale.addActionListener(e -> {
            frameMenuFotografie.dispose();
            frameMenuPrincipale.setVisible(true);
        });


        //BOTTONE ELIMINA FRAME
        JButton bottoneElimina = new JButton("Elimina Frame");
        bottoneElimina.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();

            if (selectedRow != -1 && selectedColumn != -1) {
                // La foto si trova nella prima colonna della tabella
                int fotoSelezionata = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString());
                int videoSelezionato = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());

                boolean ownerCheck;

                try {
                    ownerCheck = controller.controlloProprietarioVideo(idvideosec, User.getInstance().getUsername());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (ownerCheck) {


                int response = JOptionPane.showOptionDialog(frameMenuFotografie, "Sei sicuro di voler eliminare il frame " + fotoSelezionata + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                if (response == JOptionPane.YES_OPTION) {
                    //elimino l'utente con l'username selezionata
                    try {
                        controller.eliminaFrameGUI(videoSelezionato, fotoSelezionata);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione dei dati del frame:\n" + ex.getMessage(), "Errore di Eliminazione", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                    //aggiorno la tabella appena dopo l'eliminazione dell'utente
                    updateTable(controller, colonneTabella);

                } else { JOptionPane.showMessageDialog(null, "Puoi eliminare solo video di cui sei il proprietario!");
                }
                }
            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(frameMenuFotografie, "Seleziona una fotografia per eliminarla.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });


        // BOTTONE INSERISCI FRAME
        JButton bottoneInserisci = new JButton("Inserisci Frame");
        bottoneInserisci.addActionListener(e -> {
            boolean ownerCheck;

            try {
                ownerCheck = controller.controlloProprietarioVideo(idvideosec, User.getInstance().getUsername());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (ownerCheck) {

            InserimentoFrameGUI dialog = new InserimentoFrameGUI(idvideosec, controller, frameMenuFotografie);
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
            } else {
                JOptionPane.showMessageDialog(null, "Non hai i permessi inserire il frame");
            }
        });

        //BOTTONE PROFILO FOTO

        JButton bottoneProfiloFotografia = new JButton("Modifica Frame");
        bottoneProfiloFotografia.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();
            // L'utente ha selezionato una cella
            if (selectedRow != -1 && selectedColumn != -1) {
                // l'username è nella prima colonna della tabella
                int fotoSelezionata = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                int ordineSelezionato = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 3).toString());

                try {
                    boolean ownerCheck = controller.controlloProprietarioVideo(idvideosec, User.getInstance().getUsername());

                    if (ownerCheck) {
                    // Creo un'istanza della finestra di dialogo ProfiloImpiegato
                    ViewModificaFrameGUI profiloFoto = new ViewModificaFrameGUI(fotoSelezionata, ordineSelezionato,controller, frameMenuFotografie);
                    frameMenuFotografie.setVisible(false);
                    // Mostro la finestra di dialogo
                    profiloFoto.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Non hai i permessi per modificare questo video");
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

        ArrayList<Integer> listaIdVideo = new ArrayList<>();
        ArrayList<Integer> listaIdFoto = new ArrayList<>();
        ArrayList<Integer> listaDurata = new ArrayList<>();
        ArrayList<Integer> listaOrdine = new ArrayList<>();

        try {
            controller.getListaFrameGUI(listaIdVideo,listaIdFoto,listaDurata,listaOrdine,idvideosec);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        Object[][] newdata = new Object[listaIdVideo.size()][4];
        for (int i = 0; i < listaIdVideo.size(); i++) {
            newdata[i][0] = listaIdVideo.get(i);
            newdata[i][1] = listaIdFoto.get(i);
            newdata[i][2] = listaDurata.get(i);
            newdata[i][3] = listaOrdine.get(i);
        }

        //CODICE PER AGGIORNARE LA TABELLA CON I NUOVI DATI
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(newdata, colonneTabella);

    }


}
