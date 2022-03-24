package game.panes.leftWindow.tabs;

import client.GreetClient;
import game.TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Darek Petersen
 * @version 0.1
 */
public class Console extends JPanel {

    private JTextField textField;
    private JButton button;


    public Console() {
        add(new JLabel ("Console; coming soon", JLabel.CENTER));

        textField = new JTextField(20);
        button = new JButton("send");
        // Schriftfarbe wird gesetzt
        //textField.setForeground(Color.BLUE);
        // Hintergrundfarbe wird gesetzt
        //textField.setBackground(Color.YELLOW);
        // Textfeld wird unserem Panel hinzugefügt
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TicTacToe.client.sendMessage(textField.getText());
            }
        });

        add(textField);
        add(button);

    }
}
