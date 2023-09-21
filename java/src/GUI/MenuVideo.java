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

public class MenuVideo
{

    private final JFrame frameMenuVideo;
    private final JTable table;
    private final JTextField searchBar;

    /**
     * Instantiates a new Menu impiegati gui.
     *
     * @param controller          the controller
     * @param frameMenuPrincipale the frame menu principale
     * */


    public MenuVideo(Controller controller, JFrame frameMenuPrincipale) {
//----------------------------------------------FINESTRA--------------------------------------------------------//

        frameMenuVideo = new JFrame("Finestra Video");
        frameMenuVideo.setSize(800, 600);
        frameMenuVideo.setLocationRelativeTo(null);
        frameMenuVideo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//----------------------------------------------TABELLA Video--------------------------------------------------------//

        //Creazione
        String[] colonneTabella = {"idVideo", "Autore", "Titolo", "NumeroFrames","Durata", "Descrizione"};
        ArrayList<Integer> listaIdVideo = new ArrayList<>();
        ArrayList<String> listaAutore = new ArrayList<>();
        ArrayList<String> listaTitolo = new ArrayList<>();
        ArrayList<Integer> listaNumeroFrames= new ArrayList<>();
        ArrayList<Integer> listaDurata = new ArrayList<>();
        ArrayList<String> listaDescrizione = new ArrayList<>();

        controller.getListaVideoGUI(listaIdVideo,listaAutore,listaTitolo,listaNumeroFrames,listaDurata,listaDescrizione);
        Object[][] data = new Object[listaAutore.size()][6];
        for (int i = 0; i < listaIdVideo.size(); i++) {
            data[i][0] = listaIdVideo.get(i);
            data[i][1] = listaAutore.get(i);
            data[i][2] = listaTitolo.get(i);
            data[i][3] = listaNumeroFrames.get(i);
            data[i][4] = listaDurata.get(i);
            data[i][5] = listaDescrizione.get(i);
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
        frameMenuVideo.add(scrollPane, BorderLayout.CENTER);


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
        panelSearch.add(new JLabel("Cerca per nome video: "));
        panelSearch.add(searchBar);

        frameMenuVideo.add(panelSearch, BorderLayout.NORTH);
        frameMenuVideo.setVisible(true);


//----------------------------------------------BOTTONI---------------------------------------------------------------//
        //MENU PRINCIPALE
        JButton bottoneMenuPrincipale = new JButton("Menù Principale");
        bottoneMenuPrincipale.addActionListener(e -> {
            frameMenuVideo.dispose();
            frameMenuPrincipale.setVisible(true);
        });


        //BOTTONE INSERISCI UTENTE
        JButton bottoneInserisci = new JButton("Inserisci");
        bottoneInserisci.addActionListener(e -> {
            InserimentoVideoGUI dialog = new InserimentoVideoGUI(controller, frameMenuVideo);
            frameMenuVideo.setVisible(false);
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

        //BOTTONE ELIMINA UTENTE
        JButton bottoneElimina = new JButton("Elimina");
        bottoneElimina.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();

            if (selectedRow != -1 && selectedColumn != -1) {
                // L'utente si trova nella prima colonna della tabella
                 String usernameSelezionato = table.getValueAt(table.getSelectedRow(), 0).toString();
                int videoSel = Integer.parseInt(usernameSelezionato);
                int response = JOptionPane.showOptionDialog(frameMenuVideo, "Sei sicuro di voler eliminare il video " + usernameSelezionato + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                if (response == JOptionPane.YES_OPTION) {
                    //elimino l'utente con l'username selezionata
                    try {
                        controller.eliminaVideo(videoSel);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione dei dati del video:\n" + ex.getMessage(), "Errore di Eliminazione", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                    //aggiorno la tabella appena dopo l'eliminazione dell'utente
                    updateTable(controller,colonneTabella);
                }
            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(frameMenuVideo, "Seleziona un video per eliminarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });


        //BOTTONE PROFILO UTENTE

        JButton bottoneProfiloVideo = new JButton("Dettagli Video");
        bottoneProfiloVideo.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();
            // L'utente ha selezionato una cella
            if (selectedRow != -1 && selectedColumn != -1) {
                // l'username è nella prima colonna della tabella
                String usernameSelezionato = table.getValueAt(table.getSelectedRow(), 0).toString();
                try {
                    // Creo un'istanza della finestra di dialogo ProfiloImpiegato
                    ViewUserGUI profiloUtente = new ViewUserGUI(usernameSelezionato, controller, frameMenuVideo);
                    frameMenuVideo.setVisible(false);
                    // Mostro la finestra di dialogo
                    profiloUtente.setVisible(true);
                } catch (java.sql.SQLException ex) {
                    // Gestisci l'eccezione qui, ad esempio mostrando un messaggio di errore
                    ex.printStackTrace(); // Stampa la traccia dell'eccezione
                }
            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(frameMenuVideo, "Seleziona un video per continuare", "Errore", JOptionPane.ERROR_MESSAGE);
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
        frameMenuVideo.add(panelBottoni, BorderLayout.SOUTH);









    }

    private void updateTable(Controller controller,String[] colonneTabella) {

        //LOAD DEI NUOVI DATI
        ArrayList<Integer> listaIdVideo = new ArrayList<>();
        ArrayList<String> listaAutore = new ArrayList<>();
        ArrayList<String> listaTitolo = new ArrayList<>();
        ArrayList<Integer> listaNumeroFrames= new ArrayList<>();
        ArrayList<Integer> listaDurata = new ArrayList<>();
        ArrayList<String> listaDescrizione = new ArrayList<>();

        controller.getListaVideoGUI(listaIdVideo,listaAutore,listaTitolo,listaNumeroFrames,listaDurata,listaDescrizione);
        Object[][] newdata = new Object[listaAutore.size()][6];
        for (int i = 0; i < listaIdVideo.size(); i++) {
            newdata[i][0] = listaIdVideo.get(i);
            newdata[i][1] = listaAutore.get(i);
            newdata[i][2] = listaTitolo.get(i);
            newdata[i][3] = listaNumeroFrames.get(i);
            newdata[i][4] = listaDurata.get(i);
            newdata[i][5] = listaDescrizione.get(i);
        }


        //CODICE PER AGGIORNARE LA TABELLA CON I NUOVI DATI
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(newdata, colonneTabella);
    }






}