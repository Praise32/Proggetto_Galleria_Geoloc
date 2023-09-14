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
 * The type Menu principale gui.
 */
public class MenuPrincipaleGUI {
    /**
     * The Controller.
     */

    /**
     * Instantiates a new Menu principale gui.
     *
     * @param controller the controller
     * @param frameLogin the frame login
     */
    public MenuPrincipaleGUI(Controller controller, JFrame frameLogin) {

        frameLogin = new JFrame("Menu Principale");
        frameLogin.setSize(800, 600);
        frameLogin.setLocationRelativeTo(null);
        frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crea la JLabel di benvenuto
        JLabel labelBenvenuto = new JLabel("Benvenuto nel Geolocalized Photograph Database!");
        labelBenvenuto.setFont(new Font("Arial",Font.PLAIN,18));



        // Creo i bottoni
        JButton bottoneUtenti = new JButton("Utenti");
        JButton bottoneFotografie = new JButton("Fotografie");
        JButton bottoneCollezioni = new JButton("Collezioni");
        JButton bottoneVideo = new JButton("Video");
        JButton bottoneLuoghi = new JButton("Classifica dei Luoghi");


        // Imposto il font dei bottoni
        bottoneUtenti.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneFotografie.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneCollezioni.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneVideo.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneLuoghi.setFont(new Font("Arial", Font.PLAIN, 16));

        // Imposto il layout del frame come BoxLayout
        frameLogin.setLayout(new BoxLayout(frameLogin.getContentPane(), BoxLayout.Y_AXIS));

        // Imposto l'allineamento orizzontale dei componenti al centro
        labelBenvenuto.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneUtenti.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneFotografie.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneCollezioni.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneVideo.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneLuoghi.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Aggiungi la JLabel e i bottoni al frame
        frameLogin.add(Box.createVerticalStrut(50));
        frameLogin.add(labelBenvenuto);
        frameLogin.add(Box.createVerticalStrut(50));
        frameLogin.add(bottoneUtenti);
        frameLogin.add(Box.createVerticalStrut(10));
        frameLogin.add(bottoneFotografie);
        frameLogin.add(Box.createVerticalStrut(10));
        frameLogin.add(bottoneCollezioni);
        frameLogin.add(Box.createVerticalStrut(10));
        frameLogin.add(bottoneVideo);
        frameLogin.add(Box.createVerticalStrut(10));
        frameLogin.add(bottoneLuoghi);

        Dimension buttonSize = new Dimension(200, 50);
        bottoneUtenti.setMaximumSize(buttonSize);
        bottoneFotografie.setMaximumSize(buttonSize);
        bottoneCollezioni.setMaximumSize(buttonSize);
        bottoneVideo.setMaximumSize(buttonSize);
        bottoneLuoghi.setMaximumSize(buttonSize);

        JFrame finalFrameLogin = frameLogin;
        bottoneUtenti.addActionListener(e -> {
            // chiudo la finestra corrente
            finalFrameLogin.setVisible(false);
     // apro la finestra MenuUtenti
            MenuUtentiGUI MenuUtenti = new MenuUtentiGUI(controller, finalFrameLogin);

     });
        frameLogin.setVisible(true);


         bottoneFotografie.addActionListener(e -> {
             // chiudo la finestra corrente
             finalFrameLogin.setVisible(false);
             // apro la finestra MenuFotografie
             MenuFotografieGUI MenuFotografie = new MenuFotografieGUI(controller, finalFrameLogin);

         });

         bottoneCollezioni.addActionListener(e -> {
         // chiudo la finestra corrente
             finalFrameLogin.setVisible(false);
         // apro la finestra MenuCollezioni
         MenuCollezioniGUI MenuCollezioni = new MenuCollezioniGUI(controller,finalFrameLogin);

         });





    }

}
/**




 // Imposta le dimensioni del frame, la posizione al centro e rendilo visibile
 frameLogin.setSize(800, 600);
 frameLogin.setLocationRelativeTo(null);
 frameLogin.setVisible(true);*/
