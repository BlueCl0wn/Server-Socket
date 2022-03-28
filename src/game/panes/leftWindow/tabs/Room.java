package game.panes.leftWindow.tabs;

import game.TicTacToe;

import javax.swing.*;

/**
 * @author Darek Petersen
 * @version 0.1
 */
public class Room extends JPanel {
    public Room() {

        add(new JLabel ("Room; coming soon", JLabel.CENTER));
    }

    public void receive(String msg) {

    }

    /**
     * send msg/infos to Room
     * @param msg message
     */
    private void send(String msg) {
        TicTacToe.client.sendMessage("IW ROOM " + msg);
    }

    // TODO get done
}
