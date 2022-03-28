package game.panes.leftWindow;

// import-statements
import game.panes.leftWindow.tabs.*;

import javax.swing.*;
import java.awt.*;

/**
 * @author Darek Petersen
 * @version 0.1
 */
public class InteractionWindow extends JTabbedPane {
    // chat
    private final Chat tabChat;


    // console
    private final Console tabConsole;


    public InteractionWindow() {
        super(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
        setPreferredSize(new Dimension(600, 500));

        this.tabChat = new Chat();
        this.tabConsole = new Console();

        addTab("Console", this.tabConsole);
        addTab("Chat", this.tabChat);
    }

    public void performOnMessageReceive(String msg) {
        tabConsole.receive(msg);

        String subMsg = msg.substring(3);

        if (subMsg.startsWith("CHAT")) { // Hier wollte ich eigentlich noch einen Chat für Player-to-Player Kommunikation einfuegen.
            this.tabChat.receive(subMsg.substring(5));
        }

        /*
        Hier können mit weiteren 'else-if' und entsprechenden Anpassungen weitere Tabs auf der linken Seite hinzugefügt werden.
        Ein Beispiel wäre ein Tab, um erst nach dem Ausführen des Programms mit einem Server verbinden zu können.
         */
    }

}
