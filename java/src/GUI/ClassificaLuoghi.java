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
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

/**
 * The type Classifica luoghi.
 */
public class ClassificaLuoghi extends JFrame {

    private final JFrame frameclassifica;
    private final JTable tabellaclassifica;

    /**
     * Instantiates a new Classifica luoghi.
     */
    public ClassificaLuoghi( JFrame frameMenuPrincipale) {

        frameclassifica = new JFrame("Classifica dei 3 luoghi più immortalati");
        frameclassifica.setSize(800, 600);
        frameclassifica.setLocationRelativeTo(null);
        frameclassifica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);





        //----------------------------------------------TABELLA CLASSIFICA--------------------------------------------------------//



        // Creiamo il modello di tabella
        DefaultTableModel modelloTabella = new DefaultTableModel();

        modelloTabella.setColumnIdentifiers(new Object[]{"column1", "column2", "column3", "column4", "column5"});
        // Creiamo la tabella
        tabellaclassifica = new JTable(modelloTabella);
        // Creiamo il TableRowSorter con il tipo di modello di tabella corretto
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelloTabella);
        sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));

        // Impostiamo il TableRowSorter sulla tabella
        tabellaclassifica.setRowSorter(sorter);
        tabellaclassifica.setDefaultEditor(Object.class, null);
        tabellaclassifica.setDefaultEditor(Object.class, null);
        tabellaclassifica.getTableHeader().setReorderingAllowed(false);
        tabellaclassifica.setShowGrid(true);

        //COLORI TABELLA
        tabellaclassifica.setGridColor(Color.WHITE);
        tabellaclassifica.setBackground(Color.DARK_GRAY);
        tabellaclassifica.setForeground(Color.WHITE);
        tabellaclassifica.getTableHeader().setBackground(Color.BLACK);
        tabellaclassifica.getTableHeader().setForeground(Color.WHITE);

        //barra di scorrimento
        JScrollPane scrollPane = new JScrollPane(tabellaclassifica);
        frameclassifica.add(scrollPane, BorderLayout.CENTER);

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbprogetto", "postgres", "4713");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT latitudine, longitudine, nome, descrizione, COUNT(id_foto) AS NumeroFotografie\n" +
                    "FROM luogo NATURAL LEFT JOIN fotografia\n" +
                    "GROUP BY latitudine, longitudine, nome, descrizione\n" +
                    "ORDER BY NumeroFotografie DESC, nome ASC\n" +
                    "LIMIT 3;");

            while (rs.next()) {
                String column1 = rs.getString("Latitudine");
                String column2 = rs.getString("Longitudine");
                String column3 = rs.getString("nome");
                String column4 = rs.getString("descrizione");
                String column5 = rs.getString("NumeroFotografie");



                // Add data to table
                modelloTabella.addRow(new Object[]{column1, column2, column3,column4,column5});
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }

}