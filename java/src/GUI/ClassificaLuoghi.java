package GUI;

import CONTROLLER.Controller;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.sql.SQLException;

public class ClassificaLuoghi {

    private final JFrame frameMenuClassificaLuoghi;
    private final JTable table;



    public ClassificaLuoghi(Controller controller, JFrame frameMenuPrincipale) {
        //----------------------------------------------FINESTRA--------------------------------------------------------//

        frameMenuClassificaLuoghi = new JFrame("Finestra Classifica Luoghi");
        frameMenuClassificaLuoghi.setSize(800, 600);
        frameMenuClassificaLuoghi.setLocationRelativeTo(null);
        frameMenuClassificaLuoghi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creazione della tabella con le colonne specificate
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Latitudine");
        tableModel.addColumn("Longitudine");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Descrizione");
        table = new JTable(tableModel);

        // COLORI TABELLA
        table.setShowGrid(true);
        table.setGridColor(Color.WHITE);
        table.setBackground(Color.DARK_GRAY);
        table.setForeground(Color.WHITE);
        table.getTableHeader().setBackground(Color.BLACK);
        table.getTableHeader().setForeground(Color.WHITE);

        // barra di scorrimento
        JScrollPane scrollPane = new JScrollPane(table);
        frameMenuClassificaLuoghi.add(scrollPane, BorderLayout.CENTER);
        frameMenuClassificaLuoghi.setVisible(true);
        // Chiamata al metodo del controller per popolare la tabella
        try {
            controller.getListaLuogoClassificaGUI(tableModel);
        } catch (SQLException e) {
            e.printStackTrace(); // Puoi gestire l'eccezione qui o mostrare un messaggio di errore
        }




        //----------------------------------------------BOTTONI---------------------------------------------------------------//
        //MENU PRINCIPALE
        JButton bottoneMenuPrincipale = new JButton("Menù Principale");
        bottoneMenuPrincipale.addActionListener(e -> {
            frameMenuClassificaLuoghi.dispose();
            frameMenuPrincipale.setVisible(true);
        });

        // Aggiungiamo i pulsanti alla finestra
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBottoniLeft.add(bottoneMenuPrincipale);
        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        frameMenuClassificaLuoghi.add(panelBottoni, BorderLayout.SOUTH);


    }
}
