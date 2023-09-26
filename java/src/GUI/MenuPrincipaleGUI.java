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
        JButton bottoneLuoghi = new JButton("Luoghi");
        JButton bottoneSoggetti = new JButton("Soggetti");
        JButton bottoneClassifica = new JButton("Classifica Luoghi");

        // Imposto il font dei bottoni
        bottoneUtenti.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneFotografie.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneCollezioni.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneVideo.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneLuoghi.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneSoggetti.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneClassifica.setFont(new Font("Arial", Font.PLAIN, 16));

        // Imposto il layout del frame come BoxLayout
        frameLogin.setLayout(new BoxLayout(frameLogin.getContentPane(), BoxLayout.Y_AXIS));

        // Imposto l'allineamento orizzontale dei componenti al centro
        labelBenvenuto.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneUtenti.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneFotografie.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneCollezioni.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneVideo.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneLuoghi.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneSoggetti.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneClassifica.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        frameLogin.add(Box.createVerticalStrut(10));
        frameLogin.add(bottoneSoggetti);
        frameLogin.add(Box.createVerticalStrut(10));
        frameLogin.add(bottoneClassifica);

        Dimension buttonSize = new Dimension(200, 50);
        bottoneUtenti.setMaximumSize(buttonSize);
        bottoneFotografie.setMaximumSize(buttonSize);
        bottoneCollezioni.setMaximumSize(buttonSize);
        bottoneVideo.setMaximumSize(buttonSize);
        bottoneLuoghi.setMaximumSize(buttonSize);
        bottoneSoggetti.setMaximumSize(buttonSize);
        bottoneClassifica.setMaximumSize(buttonSize);

        JFrame finalFrameLogin = frameLogin;
        bottoneUtenti.addActionListener(e -> {
            // chiudo la finestra corrente
            finalFrameLogin.setVisible(false);
            // apro la finestra MenuUtenti
            MenuUtenti MenuUtenti = new MenuUtenti(controller, finalFrameLogin);

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

        bottoneVideo.addActionListener(e -> {
            // chiudo la finestra corrente
            finalFrameLogin.setVisible(false);
            // apro la finestra MenuCollezioni
            MenuVideo MenuVideo = new MenuVideo(controller,finalFrameLogin);

        });

        bottoneLuoghi.addActionListener(e -> {
            // chiudo la finestra corrente
            finalFrameLogin.setVisible(false);
            // apro la finestra MenuLuogo
            MenuLuogo ClassificaLuogo = new MenuLuogo(controller,finalFrameLogin);

        });

        bottoneSoggetti.addActionListener(e -> {
            // chiudo la finestra corrente
            finalFrameLogin.setVisible(false);
            // apro la finestra MenuSoggetti
            MenuSoggetto menuSoggetto = new MenuSoggetto(controller,finalFrameLogin);

        });


        bottoneClassifica.addActionListener(e -> {
            // chiudo la finestra corrente
            finalFrameLogin.setVisible(false);
            // apro la finestra MenuSoggetti
            ClassificaLuoghi menuclassificaLuoghi = new ClassificaLuoghi(controller,finalFrameLogin);

        });





    }

}
/**




 // Imposta le dimensioni del frame, la posizione al centro e rendilo visibile
 frameLogin.setSize(800, 600);
 frameLogin.setLocationRelativeTo(null);
 frameLogin.setVisible(true);*/
