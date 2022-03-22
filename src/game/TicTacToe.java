package game;

import client.GreetClient;

import javax.swing.*;
import java.awt.*;

/**
 * @author Darek Petersen
 * @version 1.1
 */
public class TicTacToe extends JFrame {
    public static GreetClient client;

    public TicTacToe() {
        client = new GreetClient("localhost", 51621, 0);

        // add(new Game());
        add(new SplitPane());
        setResizable(true);
        pack();

        setTitle("TicTacToe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame tictac = new TicTacToe();
                tictac.setVisible(true);
            }
        });
    }
}