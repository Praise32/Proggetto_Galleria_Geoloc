package MAIN;

import GUI.Login;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

/**
 * The type Main.
 */
public class Main {


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws UnsupportedLookAndFeelException the unsupported look and feel exception
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        //installa e imposta il look and feel dark
        /* Operazioni quali installazioni di librerie e simili
           sono solitamente fatte in un apposito software di installazione del programma, che venendo
           runnato una singola volta installa tutte le componenti necessarie senza farlo ad ogni avvio */


        FlatDarkLaf.installLafInfo();
        UIManager.setLookAndFeel(new FlatDarkLaf());
        new Login();


    }

}
