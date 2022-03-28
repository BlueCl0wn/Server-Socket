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

    public SplitPane SP;

    public TicTacToe() {
        this.SP = new SplitPane();

        client = new GreetClient("localhost", 64015, 0, this.SP);
        client.start();

        // add(new Game());
        add(this.SP);
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