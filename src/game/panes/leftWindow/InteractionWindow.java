package game.panes.leftWindow;

// import-statements
import game.panes.leftWindow.tabs.*;
import javax.swing.JTabbedPane;
import java.awt.*;

/**
 * @author Darek Petersen
 * @version 0.1
 */
public class InteractionWindow extends JTabbedPane {
    // chat
    private Chat tabChat;

    // settings
    private Settings tabSettings;

    // room
    private Room tabRoom;

    // console
    private Console tabConsole;

    public InteractionWindow() {
        super(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
        setPreferredSize(new Dimension(600, 500));

        this.tabChat = new Chat();
        this.tabSettings = new Settings();
        this.tabRoom = new Room();
        this.tabConsole = new Console();


        addTab("Chat", this.tabChat);
        addTab("Console", this.tabConsole);
        addTab("Settings", this.tabSettings);
        addTab("Room", this.tabRoom);
    }

    public void performOnMessageReceive(String msg) {
        tabConsole.receive(msg);

        String subMsg = msg.substring(3);

        if (subMsg.startsWith("CHAT")) {
            this.tabChat.receive(subMsg.substring(5));
        } else if (subMsg.startsWith("SETTINGS")) {
            this.tabSettings.receive(subMsg.substring(9));

        } else if (subMsg.startsWith("ROOM"))
            this.tabRoom.receive(subMsg.substring(5));

    }

}
