package Screen;

//Import-Anweisung für unseren JFrame
import javax.swing.*;
// Import-Anweisung für unser JLabel
import javax.swing.JLabel;

public class Test {
    public static void main(String[] args) {
        /* Erzeugung eines neuen Frames mit dem
           Titel "Beispiel JFrame " */
        JFrame meinFrame = new JFrame("Beispiel JFrame");
        /* Wir setzen die Breite und die Höhe
           unseres Fensters auf 200 Pixel */
        meinFrame.setSize(200, 200);
        String t = JOptionPane.showInputDialog("Dies ist ein Input Dialog");
        System.out.println(t);
        meinFrame.add(new JLabel(t));
        // Wir lassen unseren Frame anzeigen
        meinFrame.setVisible(true);
    }
}