package game.panes.leftWindow.tabs;

import game.TicTacToe;

import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;


/**
 * @author Darek Petersen
 * @version 0.1
 */
public class Settings extends JPanel {
    public Settings() {
        Icon icon = MetalIconFactory.getTreeControlIcon(false);
        add(new JLabel ("Settings; coming soon", icon, JLabel.CENTER));
    }

    public void receive(String msg) {

    }

    /**
     * send msg/infos to Room
     * @param msg message
     */
    private void send(String msg) {
        TicTacToe.client.sendMessage("IW SETTINGS " + msg);
    }

    // TODO get done
}
