package game;

// import-statements
import game.panes.tictactoe.Game;
import game.panes.leftWindow.InteractionWindow;
import game.panes.tictactoe.SelectListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Darek Petersen
 * @version 0.2
 */
public class SplitPane extends JSplitPane {
    // TicTacToe game
    public Game tictactoe;

    // capsule for tictactoe so that it can't be resized
    public JPanel rightCapsule;

    // left side
    public InteractionWindow left;


    /**
     * main constructor
     */
    public SplitPane() {
        super();

        setPreferredSize(new Dimension(1000, 400));

        setFocusable(true);

        // initiating and encapsulating 'tictactoe'.
        this.rightCapsule = new JPanel();
        this.tictactoe = new Game();
        this.rightCapsule.setPreferredSize(new Dimension(this.tictactoe.totalWidth,this.tictactoe.totalWidth));
        //rightCapsule.add(tictactoe);

        // initiating left side
        this.left = new InteractionWindow();

        // setting Container as sides
        setLeftComponent(this.left);
        setRightComponent(this.tictactoe);

        addKeyListener(new SelectListener(this.tictactoe));
    }
}
