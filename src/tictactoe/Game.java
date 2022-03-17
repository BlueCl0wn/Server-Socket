package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Darek Petersen
 * @version 1.0
 */
public class Game extends JPanel implements ActionListener {
    private final int fieldWidth;
    private final int lineWidth;

    private final Timer timer;

    private static boolean running;

    Board brett;

    /**
     * main constructor
     */
    public Game() {
        this.fieldWidth = 100;
        this.lineWidth = 5;


        this.brett = new Board(this.fieldWidth, this.lineWidth);

        addKeyListener(new SelectListener());
        setPreferredSize(new Dimension(this.brett.totalWidth, this.brett.totalWidth+this.lineWidth));
        setFocusable(true);
        add(this.brett);

        running = true;

        timer = new Timer(200, this);
        timer.start();

       // startGame();
    }

    /**
     * Resets game for a new round of freshly harvested TicTacToe.
     */
    public static void resetGame() {
        Board.resetBoard();
        running = true;
    }

    /**
     * TODO finish 'startGame()' and its javadoc
     */
    private void startGame() {
    }

    /**
     * main loop action
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            this.checkForWinner();
        }
        repaint();
    }

    /**
     * Check if someone won the round.
     * Reassign field 'running' accordingly.
     */
    private void checkForWinner() {
        running = brett.checkIfStillPlaying();
    }

    /**
     * TODO Fill javadoc
     */
    public static void stopGame() {
        running = false;
    }

    /**
     * TODO fill javadoc
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {

            Toolkit.getDefaultToolkit().sync();
            //brett.draw(g);
        } else {
            Font f = new Font("Calibri", Font.BOLD, 16);
            FontMetrics metrics = getFontMetrics(f);

            g.setColor(Color.lightGray);
            g.setFont(f);
            g.drawString("Game Over!", (this.brett.totalWidth - metrics.stringWidth("Game Over - You died, noob!")),
                    this.brett.totalWidth/2);
        }
    }
}
