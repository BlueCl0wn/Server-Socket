package game.panes.tictactoe;

import game.panes.tictactoe.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * KeyAdapter for handling all user input and calling of according methods.
 * @author Darek Petersen
 * @version 2.0
 */
public class SelectListener extends KeyAdapter {
    private Game game;

    public SelectListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        //    System.out.println("called SelectListener");

        int key_id = e.getKeyCode();

        if (key_id == KeyEvent.VK_NUMPAD1 && this.game.isFieldFree(1)) {
            this.game.pickField(1); //TODO Need to find a way to that allows the player ID to be set through server communcation
        }
        if (key_id == KeyEvent.VK_NUMPAD2 && this.game.isFieldFree(2)) {
            this.game.pickField(2);
        }
        if (key_id == KeyEvent.VK_NUMPAD3 && this.game.isFieldFree(3)) {
            this.game.pickField(3);
        }
        if (key_id == KeyEvent.VK_NUMPAD4 && this.game.isFieldFree(4)) {
            this.game.pickField(4);
        }
        if (key_id == KeyEvent.VK_NUMPAD5 && this.game.isFieldFree(5)) {
            this.game.pickField(5);
        }
        if (key_id == KeyEvent.VK_NUMPAD6 && this.game.isFieldFree(6)) {
            this.game.pickField(6);
        }
        if (key_id == KeyEvent.VK_NUMPAD7 && this.game.isFieldFree(7)) {
            this.game.pickField(7);
        }
        if (key_id == KeyEvent.VK_NUMPAD8 && this.game.isFieldFree(8)) {
            this.game.pickField(8);
        }
        if (key_id == KeyEvent.VK_NUMPAD9 && this.game.isFieldFree(9)) {
            this.game.pickField(9);
        }
        if (key_id == KeyEvent.VK_E) {
            this.game.stopGame();
        }
        if (key_id == KeyEvent.VK_R) {
            this.game.resetGame();
        }
    }
}
