import javax.swing.*;
import java.awt.*;

/**
 * Estendendo la classe JFrame ciò ci permette di utilizzare le sue funzioni direttamente
 * sull'oggetto di questa classe senza doverle dare un nome, utilizzando this.x
 */

public class BasicFrame extends JFrame {

    /**
     * Costruttore per un frame, ovvero una finestra del programma.
     * Qui vengono impostati diverse caratteristiche:
     * - Titolo della finestra
     * - Dimensioni
     * - Colore dello sfondo
     * - Punto in cui si apre la finestra a schermo
     * - Possibilità o meno di ridimensionarla
     * - Cosa succede cliccando sulla X in alto a destra
     */
    public BasicFrame() {
        //Frame è una finestra del programma

        this.setTitle("Login");
        this.setSize(1000, 700);
        this.getContentPane().setBackground(new Color(0x191919));   //Accetta sia HEX che RGB (r, g, b)); 0x191919 bel nero
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setVisible(true);
    }
}