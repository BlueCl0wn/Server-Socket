package game.panes.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

/**
 * @author Darek Petersen
 * @version 2.0
 */
public class Game extends JPanel implements ActionListener {
    // Information about the board for easy acces.
    private final int fieldWidth;
    private final int lineWidth;
    public final int totalWidth;

    // The play board saved as a 2 dimensional array of Fields
    public static Field[][] board;

    // Current player stored with an according ID
    public static int currentPlayer;

    // All possible combinations by which one can win...
    public Field[] row1; // ---
    public Field[] row2; // ---
    public Field[] row3; // ---
    public Field[] row4; // |
    public Field[] row5; // |
    public Field[] row6; // |
    public Field[] row7; // \
    public Field[] row8; // /
    // saved in one array for easier access.
    public Field[][] rows;

    // Timer
    private final Timer timer;

    // indicates if game is over or still running
    private boolean running;

    /**
     * main constructor
     */
    public Game() {
        this.fieldWidth = 100;
        this.lineWidth = 5;
        this.totalWidth = 4 * lineWidth + 3 * fieldWidth;

        addKeyListener(new SelectListener(this));
        setPreferredSize(new Dimension(this.totalWidth, this.totalWidth));
        setFocusable(true);

        running = true;

        timer = new Timer(200, this);
        timer.start();

        // initiate current player
        currentPlayer = 1;

        setBackground(Color.BLACK);
        // this.getContentPane().setLayout(null);


        // Create board with all nine Fields
        board = new Field[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x = (i+1) * (this.lineWidth + this.fieldWidth);
                int y = (j+1) * (this.lineWidth + this.fieldWidth);

                // Assign coordinates to field
                board[i][j] = new Field(x, y, this.fieldWidth);

                // Add field to JPanel
                add(board[i][j]);
            }
        }

        // Assign possible winning situations
        row1 = new Field[] {board[0][0], board[0][1], board[0][2]}; // ---
        row2 = new Field[] {board[1][0], board[1][1], board[1][2]}; // ---
        row3 = new Field[] {board[2][0], board[2][1], board[2][2]}; // ---
        row4 = new Field[] {board[0][0], board[1][0], board[2][0]}; // |
        row5 = new Field[] {board[0][1], board[1][1], board[2][1]}; // |
        row6 = new Field[] {board[0][2], board[1][2], board[2][2]}; // |
        row7 = new Field[] {board[0][0], board[1][1], board[2][2]}; // /
        row8 = new Field[] {board[0][2], board[1][1], board[2][0]}; // \
        rows = new Field[][] {row1, row2, row3, row4, row5, row6, row7, row8};

       // startGame();
    }

    /**
     * Return field with ID f.
     * @param f field ID
     * @return The field itself
     */
    public Field getField(int f) {
        System.out.println(f);

        // Row will be set to 2 minus the rounded amount of times 3 fits into f.
        // (f-1) because otherwise 3/3 would end up being 1 even though it has to equal 0 for the program to work.
        int row = 2 - ((f-1) / 3);
        System.out.println("row: " + row);

        // If f%3 == 0 (true for f = {3,6,9}), column will be set to 2.
        // Otherwise, column = (f%3)
        int column = ((f%3 == 0)) ? 2 : (f % 3) - 1;
        System.out.println("column: " + column);

        // Return field.
        return this.board[row][column];
    }

    /**
     * Checks if a specific field has not been occupied.
     * @param f Field number
     * @return Boolean showing whether the field is empty (true) or occupied (false).
     */
    public boolean isFieldFree(int f) {
        // Makes sure f has  correct magnitude.
        if (f < 1 || f > 9) {
            System.out.println("This Field does not exist.");
        }

        // Returns return of that fields 'isFree()' methode.
        return getField(f).isFree();
    }

    /**
     * Assign a field to a player
     * @param f field number
     */
    public void pickField(int f) {
        if (this.running) {
            getField(f).pick(currentPlayer);
            currentPlayer = (currentPlayer == 1) ? 2 : 1;
        }
    }

    /**
     * Reset whole board to empty.
     */
    public void resetBoard() {
        for (Field[] row : board) {
            for (Field field : row) {
                field.reset();
            }
        }
    }

    /**
     * Checks if someone won the round.
     * @return Boolean whether there was found a winner (false) or not (true).
     */
    public boolean checkIfStillPlaying() {
        for (Field[] row : this.rows) {
            if (row[0].status != 0 && row[0].status == row[1].status && row[1].status == row[2].status) {
                for (Field f : row) {
                    f.setBackground(Color.RED);
                }
                return false;
            }
        }
        return true;
    }

    /**
     * Resets game for a new round of freshly harvested TicTacToe.
     */
    public void resetGame() {
        this.resetBoard();
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
    public void checkForWinner() {
        if (running) {
            this.running = this.checkIfStillPlaying();
        }
    }

    /**
     * TODO Fill javadoc
     */
    public void stopGame() {
        this.running = false;
    }

    /**
     * TODO fill javadoc
     * @param g graphic
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
            g.drawString("Game Over!", (this.totalWidth - metrics.stringWidth("Game Over - You died, noob!")),
                    this.totalWidth/2);
        }
    }
}
