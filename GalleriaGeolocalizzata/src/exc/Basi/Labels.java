package Basi;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Labels {

    public static void main(String[] args) {
        JLabel label = new JLabel();   //Scrivere tra le parentesi equivale a un label.settext
        label.setText("JAVA");
        label.setFont(new Font("MV Boli", Font.PLAIN, 30)); //Tipo di font
        label.setForeground(Color.white);   //Colore testo
        label.setBackground(Color.green);
        label.setOpaque(true);

        label.setIcon(new ImageIcon("src/GUI/Icon.png"));
        //Posizione del testo rispetto all'icona
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);

        //Bordo
        Border border = BorderFactory.createLineBorder(Color.green , 10);
        label.setBorder(border);

        //Posizione e dimensione
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        //label.setBounds(0,0,250,250);   //Richiede frame.layout(null) per essere impostato manualmente

        JFrame frame = new JFrame();
        frame.setVisible(true);
        //frame.setLayout(null);
        frame.add(label);
        frame.pack();   //Sposta e ridimensiona i componenti in modo da tenerli tutti mentre si ridimensiona la finestra

    }
}