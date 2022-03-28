package game;

// import-statements
import game.panes.tictactoe.Game;
import game.panes.leftWindow.InteractionWindow;
import game.panes.tictactoe.SelectListener;

import javax.swing.*;
import java.awt.*;

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
    public InteractionWindow IW;


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
        this.IW = new InteractionWindow();

        // setting Container as sides
        setLeftComponent(this.IW);
        setRightComponent(this.tictactoe);

        addKeyListener(new SelectListener(this.tictactoe));
    }

    public void perfomOnMessageReceive(String msg) {
        if (msg.startsWith("GAME")) { // Message is directed to game

            // Create Substrings for alter usage
            int f = Integer.parseInt(msg.substring(5, 6));
            int player = Integer.parseInt(msg.substring(7, 8));
            // TODO update in a way that allows multiple commands for 'Game'

            // actually pick field
            this.tictactoe.opponentPickedField(f, player);
        } else if (msg.startsWith("IW")) { // message is directed to InteractionWindow
            // forward to InteractionWindow
            IW.performOnMessageReceive(msg);
        }
    }
}
