package game.panes.leftWindow.tabs;

import game.TicTacToe;

import javax.swing.*;

/**
 * @author Darek Petersen
 * @version 0.1
 */
public class Chat extends JPanel {
    public Chat() {

        add(new JLabel ("Chat; coming soon", JLabel.CENTER));
    }

    public void receive(String msg) {

    }

    /**
     * send msg/infos to Room
     * @param msg message
     */
    private void send(String msg) {
        TicTacToe.client.sendMessage("IW CHAT " + msg);
    }
    // TODO get done
}
