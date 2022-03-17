package game;

import javax.swing.*;
import java.awt.*;

/**
 * @author Darek Petersen
 * @version 1.1
 */
public class TicTacToe extends JFrame {
    public TicTacToe() {
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