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
 * The type Soggetto gui.
 */
public class ViewSoggettoGUI extends JDialog {

    final JTable tabellaFotografie;


    public ViewSoggettoGUI(String nomeSelezionato, Controller controller, JFrame framePadre) throws SQLException {
        setTitle("Profilo Soggetto");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creiamo le variabili per ricevere dal controller i dati del soggetto
        String NomeSelezionato = controller.getNomeSoggettoViewGUI(nomeSelezionato);
        String CategoriaSelezionata = controller.getCategoriaSoggettoViewGUI(nomeSelezionato);


        // Creiamo il pannello principale
        JPanel panel = new JPanel(new BorderLayout());

        // Creiamo il pannello per i dati
        JPanel datiPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        datiPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 10, 20));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // NOME
        JLabel usernameLabel = new JLabel("Nome:", SwingConstants.CENTER);
        usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField usernameField = new JTextField();
        usernameField.setText(NomeSelezionato);
        datiPanel.add(usernameLabel);
        datiPanel.add(usernameField);

        // CATEGORIA
        JLabel categoriaLabel = new JLabel("Categoria:", SwingConstants.CENTER);
        categoriaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField categoriaField = new JTextField();
        categoriaField.setText(CategoriaSelezionata);
        datiPanel.add(categoriaLabel);
        datiPanel.add(categoriaField);


        // AGGIUNGI DATI AL PANNELLO
        leftPanel.add(datiPanel);
        leftPanel.setPreferredSize(new Dimension(700, 600));
        panel.add(leftPanel, BorderLayout.CENTER);




//-----------------------------------------------TABELLE-----------------------------------------------------------------//
        // Creiamo il pannello destro per la tabella tag_utente
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("ID Foto associate:"));

        // TABELLA FOTO POSSEDUTE a destra
        ArrayList<Integer> listaFotoAssociate = controller.vediFotoAssociate(nomeSelezionato);
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
        





//-----------------------------------------------BOTTONI-----------------------------------------------------------------//



        // Crea il pannello dei bottoni
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton bottoneSalva = new JButton("Salva modifiche");
        JButton bottoneAnnulla = new JButton("Annulla modifiche");
        panelBottoniLeft.add(bottoneSalva);
        panelBottoniLeft.add(bottoneAnnulla);

        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);

        // Aggiungo i pannelli alla finestra principale
        add(panel,BorderLayout.CENTER);

        // BOTTONE SALVA
        bottoneSalva.addActionListener(e -> {
            setVisible(false);
            String categoriaModificata = categoriaField.getText();
            String nomeModificato = usernameField.getText();


            try {

                controller.modificaSoggettoString(nomeSelezionato, categoriaModificata, nomeModificato);
                JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante la modifica dei dati del soggetto:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
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
