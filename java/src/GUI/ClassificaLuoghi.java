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
    public ClassificaLuoghi(Controller controller, JFrame frameMenuPrincipale) {

        frameclassifica = new JFrame("Finestra Utenti");
        frameclassifica.setSize(800, 600);
        frameclassifica.setLocationRelativeTo(null);
        frameclassifica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set table model
        table.setModel(tableModel);

        // Set the column identifiers
        tableModel.setColumnIdentifiers(new Object[]{"column1", "column2", "column3", "column4", "column5"});

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });

        this.setLayout(new BorderLayout());
        this.add(loadButton, BorderLayout.PAGE_START);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadData() {
        // Clear existing data
        tableModel.setRowCount(0);

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
                tableModel.addRow(new Object[]{column1, column2, column3});
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}