package game.panes.tictactoe;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing a single Field on the TicTacToe board.
 * @author Darek Petersen
 * @version 1.0
 */
public class Field extends JPanel{
    public final int width;

    private Color color;

    // 0 == Null ; 1 == cross ; 2 ==  circle
    public int status;

    /**
     * main constructor
     * @param width width and height of field
     */
    public Field(int width) {
        this.width = width;
        this.color = Color.WHITE;

        setPreferredSize(new Dimension(width, width));
        setFocusable(true);
        setBackground(this.color);
    }

    /**
     * actually used constructor
     */
    public Field(int fieldWidth, Color color) {
        this(fieldWidth);
        this.color = color;

    }

    /**
     * Switch status according to player if field is clicked / selected
     * @param player id
     */
    public void pick(int player) {
        if (player == 1) {
            this.status = 1;

            //     System.out.println("picked field for player: " + player);

        } else if (player == 2) {
            this.status = 2;

            //    System.out.println("picked field for player: " + player);

        } else {
            System.out.println("This player-id should not exist.");
        }
    }

    /**
     * Is this field empty (true) or occupied (false).
     * @return boolean
     */
    public boolean isFree() {
        return status == 0;
    }

    /**
     * Resets field to empty.
     */
    public void reset() {
        this.status = 0;
        setBackground(this.color);
    }

    /**
     * Draw Field and whatever else is supposed to be drawn
     * @param g Graphics object to be drawn on
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        // Important side-note: Each JPanel counts its own coordinates!
        if (status == 0) {
            assert true;
        } else if (status == 1) { // draw cross
            // Draw a cross.
            g.drawLine(10, 10, this.width - 10, this.width - 10);
            g.drawLine(this.width - 10, 10, 10, this.width - 10);
        } else if (status == 2) {
            // Draw a circle.
            g.drawOval(10, 10, this.width - 20, this.width - 20);
        } else {
            //
            //System.out.println("A Field cannot have this status. Something is wrong.");
            System.out.println("A Field cannot have the status ${status}. Something is wrong.");
        }
    }
}
