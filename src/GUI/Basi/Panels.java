package Basi;

import javax.swing.*;
import java.awt.*;

public class Panels{
    /**
     * JPanels = Componente che ha funzione di container per altri componenti, suddividendo il frame in aree
     */
    public static void main(String[] args) {

        JLabel label = new JLabel();
        label.setText("HIIIII");
        label.setForeground(Color.white);

        JPanel panel1 = new JPanel();

        JPanel panel2 = new JPanel();

        JPanel panel3 = new JPanel();



        panel1.setBackground(Color.red); //Imposta il colore del panel
        panel2.setBackground(Color.blue); //Imposta il colore del panel
        panel3.setBackground(Color.green); //Imposta il colore del panel

        //Dato che non abbiamo un layout manager per il momento, inseriamo manualmente i limiti
        panel1.setBounds(0, 0, 300, 300);
        panel2.setBounds(300, 0, 300, 300);
        panel2.setLayout(new BorderLayout());
        panel3.setBounds(0, 300, 600, 300);


        panel2.add(label);

        JFrame frame = new Frames();
        frame.setLayout(null);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);

    }
}
