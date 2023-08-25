package Basi;
import javax.swing.*;
import java.awt.*;

/**
 * Estendendo la classe JFrame ciò ci permette di utilizzare le sue funzioni direttamente
 * sull'oggetto di questa classe senza doverle dare un nome, utilizzando this.x
 */

public class Frames extends JFrame{
    public Frames(){
        //Frame è una finestra del programma

        this.setTitle("TEST");
        this.setSize(1000, 900);
        this.getContentPane().setBackground(new Color(0x191919));   //Accetta sia HEX che RGB (r, g, b)); 0x191919 bel nero
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setVisible(true);
    }
}
