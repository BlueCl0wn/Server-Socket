package game.panes.tictactoe;

import game.TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    public int playerID;

    // All possible combinations by which one can win...
    public final Field[] row1; // ---
    public final Field[] row2; // ---
    public final Field[] row3; // ---
    public final Field[] row4; // |
    public final Field[] row5; // |
    public final Field[] row6; // |
    public final Field[] row7; // \
    public final Field[] row8; // /
    // saved in one array for easier access.
    public final Field[][] rows;

    // Timer
    private final Timer timer;

    // indicates if game is over or still running
    public boolean running;

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

        addMouseListener(new MouseAdapter() {// provides empty implementation of all
            // MouseListener`s methods, allowing us to
            // override only those which interests us
            @Override //I override only one method for presentation
            public void mousePressed(MouseEvent e) {
                requestFocus();
                System.out.println(e.getX() + "," + e.getY());
            }
        });


        setBackground(Color.BLACK);
        // this.getContentPane().setLayout(null);


        addKeyListener(new SelectListener(this));

        // Create board with all nine Fields
        board = new Field[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                // Thought this would be needed for positioning
                int x = (i+1) * (this.lineWidth + this.fieldWidth);
                int y = (j+1) * (this.lineWidth + this.fieldWidth);

                // Assign coordinates to field
                board[i][j] = new Field(this.fieldWidth);
                    // board[i][j] = new Field(x, y, this.fieldWidth);

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
    }

    /**
     * Return field with ID f.
     * @param f field ID
     * @return The field itself
     */
    public Field getField(int f) {
        //       System.out.println(f);

        // Row will be set to 2 minus the rounded amount of times 3 fits into f.
        // (f-1) because otherwise 3/3 would end up being 1 even though it has to equal 0 for the program to work.
        int row = 2 - ((f-1) / 3);
        //   System.out.println("row: " + row);

        // If f%3 == 0 (true for f = {3,6,9}), column will be set to 2.
        // Otherwise, column = (f%3)
        int column = ((f%3 == 0)) ? 2 : (f % 3) - 1;
        //   System.out.println("column: " + column);

        // Return field.
        return board[row][column];
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
     * start game
     * inform server room about it
     */
    public void startGame() {
        this.running = true;
        resetBoard();
       // playerID = // TODO finish 'startGame()'

    }

    /**
     * Assign a field to a player
     * @param f field number
     */
    public void pickField(int f) {
        //    System.out.println("called 'game.pickField'");

        if (this.running) {
            getField(f).pick(currentPlayer);
            TicTacToe.client.sendMessage("GAME PICK " + currentPlayer + " " + f);
            currentPlayer = (currentPlayer == 1) ? 2 : 1;
        }
    }

    /**
     * opponent picked a field and sent infos through room
     * @param f picked field
     * @param player playerID of opponent
     */
    public void opponentPickedField(int f, int player) {
        if (this.running) {
            if (currentPlayer == player) {
                getField(f).pick(currentPlayer);
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
            } else {
                System.out.println("Error: The player IDs ar not the same.");
            }
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
                return false; // someone won
            }
        }
        return true; // no one won
    }

    /**
     * Resets game for a new round of freshly harvested TicTacToe.
     */
    public void resetGame() {
        this.resetBoard();
        running = true;
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
     * Stop game by setting 'running' to false
     */
    public void stopGame() {
        this.running = false;
    }

    /**
     * Draw method for the Game
     * @param g graphic
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {

            Toolkit.getDefaultToolkit().sync();
            //brett.draw(g);
        } else {
        }
    }

    public void receive(String msg) {
        if (msg.startsWith("GAME PICK")) { // Field has been picked

            //extract field number and player id
            int player = Integer.parseInt(msg.substring(10, 11));
            int f = Integer.parseInt(msg.substring(12, 13));

            //pick field
            opponentPickedField(f, player);

        } else if (msg.startsWith("GAME RESET")) { // Game has been reset
            resetGame();
        }

        // Basically 'actionPerformed()'
        if (running) {
            this.checkForWinner();
        }
        repaint();

    }

    // TODO introduce receive message which handles messages from server Room
}
