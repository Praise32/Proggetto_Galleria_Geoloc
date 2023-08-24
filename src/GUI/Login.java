import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JButton button1;
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login (){
        setContentPane(loginPanel);
        setTitle("LOGIN"); //Titolo della finestra
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Cosa succede quando la finestra è chiusa
        setSize(1000, 750); //Dimensione iniziale della finestra
        setLocationRelativeTo(null);     //Sceglie dove far apparire la finestra, null = centro schermo
        setResizable(false);            //Impedisce il ridimensionamento della finestra
        setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        new Login();
    }
}
