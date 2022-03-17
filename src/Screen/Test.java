package Screen;

//Import-Anweisung für unseren JFrame
import javax.swing.*;
import java.awt.*;

public class Test {
    public static void main(String[] args) {

        JDialog meinD = new JDialog();
        meinD.setTitle("Jpanel Beispeil");

        meinD.setSize(500, 500);

// Hier erzeugen wir unsere JPanels
        JPanel game = new JPanel();
        JPanel chat = new JPanel();
        JPanel connection = new JPanel();
        JPanel settings = new JPanel();

        // Hier setzen wir die Hintergrundfarben für die JPanels
        game.setBackground(Color.RED);
        game.add(new JLabel("game"));

        chat.setBackground(Color.BLUE);
        chat.add(new JLabel("Here you can chat with your room member"));

        connection.setBackground(Color.GREEN);
        connection.add(new JLabel("connection status"));

        settings.setBackground(Color.YELLOW);
        settings.add(new JLabel("settings"));
        //JOptionPane.showInputDialog("Dies ist ein Input Dialog");

        JTabbedPane tabpane = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);

        // Hier werden die JPanels als Registerkarten hinzugefügt
        tabpane.addTab("chat", chat);
        tabpane.addTab("connection", connection);
        tabpane.addTab("settings", settings);

        JSplitPane split = new JSplitPane(1, true);
        split.setLeftComponent(tabpane);
        split.setRightComponent(game);



        meinD.add(split);

        // Wir lassen unseren Frame anzeigen
        meinD.setVisible(true);
    }
}