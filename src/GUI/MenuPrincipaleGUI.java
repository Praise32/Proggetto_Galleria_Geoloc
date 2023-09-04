package GUI;

import CONTROLLER.Controller;
import javax.swing.*;
import java.awt.*;

/**
 * The type Menu principale gui.
 */
public class MenuPrincipaleGUI {
    /**
     * The Controller.
     */

    private final JFrame frame;

    /**
     * Instantiates a new Menu principale gui.
     */
    public MenuPrincipaleGUI() {
        frame = new JFrame("Menu Principale");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creo la JLabel di benvenuto
        JLabel labelBenvenuto = new JLabel("Benvenuto nel GEOLOCALIZED PHOTOGRAPH DATABASE!");
        labelBenvenuto.setFont(new Font("Arial", Font.PLAIN, 18));

        // Creo i bottoni
        JButton bottoneUtenti = new JButton("Utenti");
        JButton bottoneFoto = new JButton("Fotografie");
        JButton bottoneCollezioni = new JButton("Collezioni");
        JButton bottoneVideo = new JButton("Video");
        JButton bottoneSoggetti = new JButton("Soggetti");


        //font bottoni
        bottoneUtenti.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneFoto.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneCollezioni.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneVideo.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneSoggetti.setFont(new Font("Arial", Font.PLAIN, 16));



        //layout del frame come BoxLayout
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Imposta l'allineamento orizzontale dei componenti al centro
        labelBenvenuto.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneUtenti.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneCollezioni.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneVideo.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneSoggetti.setAlignmentX(Component.CENTER_ALIGNMENT);




        // Aggiungi la JLabel e i bottoni al frame
        frame.add(Box.createVerticalStrut(50));
        frame.add(labelBenvenuto);
        frame.add(Box.createVerticalStrut(50));
        frame.add(bottoneUtenti);
        frame.add(Box.createVerticalStrut(10));
        frame.add(bottoneCollezioni);
        frame.add(Box.createVerticalStrut(10));
        frame.add(bottoneFoto);
        frame.add(Box.createVerticalStrut(10));
        frame.add(bottoneVideo);
        frame.add(Box.createVerticalStrut(10));
        frame.add(bottoneSoggetti);



        Dimension buttonSize = new Dimension(200, 50);
        bottoneUtenti.setMaximumSize(buttonSize);
        bottoneFoto.setMaximumSize(buttonSize);
        bottoneCollezioni.setMaximumSize(buttonSize);
        bottoneVideo.setMaximumSize(buttonSize);
        bottoneSoggetti.setMaximumSize(buttonSize);


        bottoneUtenti.addActionListener(e -> {
            // chiudo la finestra corrente
            frame.setVisible(false);
            // apro la finestra MenuUtenti
            MenuUtentiGUI menuUtenti =new MenuUtentiGUI(controller,frame);

        });

        bottoneCollezioni.addActionListener(e -> {
            // chiudo la finestra corrente
            frame.setVisible(false);
            // apro la finestra MenuLaboratori
            MenuCollezioniGUI menuCollezioni = new MenuCollezioniGUI(controller,frame);

        });


        bottoneFoto.addActionListener(e -> {
            // chiudo la finestra corrente
            frame.setVisible(false);
            // apro la finestra MenuImpiegati
            MenuFotoGUI menuFoto = new menuFotoGUI(controller,frame);

        });


        bottoneVideo.addActionListener(e -> {
            // chiudo la finestra corrente
            frame.setVisible(false);
            // apro la finestra MenuImpiegati
            MenuVideoGUI menuVideo = new MenuVideoGUI(controller,frame);

        });


        bottoneSoggetti.addActionListener(e -> {
            // chiudo la finestra corrente
            frame.setVisible(false);
            // apro la finestra MenuImpiegati
            MenuSoggettiGUI menuSoggetti = new MenuSoggettiGUI(controller,frame);

        });



        // Imposta le dimensioni del frame, la posizione al centro e rendilo visibile
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }




}
