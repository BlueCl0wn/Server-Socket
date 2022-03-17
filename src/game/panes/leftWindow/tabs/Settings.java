package game.panes.leftWindow.tabs;

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

    // TODO get done
}
