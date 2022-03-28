package game.panes.leftWindow.tabs;

import game.TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Darek Petersen
 * @version 0.1
 */
public class Console extends JPanel {
    // input
    private JPanel input;

    private final JTextField textField;
    private final JButton button;


    // answers
    private JScrollPane scrollPane;

    private ArrayList<String> answers;


    public Console() {

        // input
        textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(300, 30));
        button = new JButton("send");
        button.addActionListener(e -> TicTacToe.client.sendMessage(textField.getText()));

        add(textField);
        add(button);


    }

    public void receive(String msg) {
        System.out.println(msg); // TODO add method that changes JTextField in app
        answers.add(msg);

        scrollPane.add(new JLabel(msg));
    }
}
