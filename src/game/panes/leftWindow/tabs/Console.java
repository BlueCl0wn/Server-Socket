package game.panes.leftWindow.tabs;

import game.TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Darek Petersen
 * @version 0.1
 */
public class Console extends JPanel {

    private final JTextField textField;


    // answers
    private JScrollPane scrollPane;
    private final JLabel label;
    private final JLabel labelAlt;
    private String msgAlt;



    public Console() {
        setLayout(new BoxLayout(this, javax.swing.BoxLayout.Y_AXIS ));

        // input
        textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(300, 30));
        JButton button = new JButton("send");
        button.addActionListener(e -> TicTacToe.client.sendMessage(textField.getText()));

        // input
        JPanel input = new JPanel();

        input.add(textField);
        input.add(button);
        add(input);

        label = new JLabel("");
        labelAlt = new JLabel("");
        msgAlt = "";

        add(label);
        add(new JPanel());
        add(labelAlt);
        add(new JPanel());
        add(new JPanel());
        add(new JPanel());

    }

    public void receive(String msg) {
        label.setText(msg);
        labelAlt.setText(msgAlt);
        msgAlt = msg;

        //scrollPane.add(new JLabel(msg));
    }
}
