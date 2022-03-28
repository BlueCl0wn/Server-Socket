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
public class SplitPane extends JSplitPane implements ActionListener {
    // TicTacToe game
    public final Game tictactoe;

    // capsule for tictactoe so that it can't be resized
    private final JPanel rightCapsule;

    // left side
    public final InteractionWindow IW;


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
        rightCapsule.setFocusable(true);
        rightCapsule.add(tictactoe);
        //rightCapsule.add(tictactoe);

        // initiating left side
        this.IW = new InteractionWindow();

        // setting Container as sides
        setLeftComponent(this.IW);
        //setRightComponent(this.tictactoe);
        setRightComponent(this.rightCapsule);

        addKeyListener(new SelectListener(this.tictactoe));
    }

    public void perfomOnMessageReceive(String msg) {
        if (msg.startsWith("GAME")) { // Message is directed to game
            tictactoe.receive(msg);
            IW.performOnMessageReceive(msg);
        } else if (msg.startsWith("IW")) { // message is directed to InteractionWindow
            // forward to InteractionWindow
            IW.performOnMessageReceive(msg);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (tictactoe.running) {
            tictactoe.running = tictactoe.checkIfStillPlaying();
        }
    }

}
