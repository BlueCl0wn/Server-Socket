package tictactoe;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * KeyAdapter for handling all user input and calling of according methods.
 * @author Darek Petersen
 * @version 1.0
 */
public class SelectListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        System.out.println("keyPressed has been called");
        int key_id = e.getKeyCode();

        if (key_id == KeyEvent.VK_NUMPAD1 && Board.isFieldFree(1)) {
            Board.pickField(1); //TODO Need to find a way to that allows the player ID to be set through server communcation
        }
        if (key_id == KeyEvent.VK_NUMPAD2 && Board.isFieldFree(2)) {
            Board.pickField(2);
        }
        if (key_id == KeyEvent.VK_NUMPAD3 && Board.isFieldFree(3)) {
            Board.pickField(3);
        }
        if (key_id == KeyEvent.VK_NUMPAD4 && Board.isFieldFree(4)) {
            Board.pickField(4);
        }
        if (key_id == KeyEvent.VK_NUMPAD5 && Board.isFieldFree(5)) {
            Board.pickField(5);
        }
        if (key_id == KeyEvent.VK_NUMPAD6 && Board.isFieldFree(6)) {
            Board.pickField(6);
        }
        if (key_id == KeyEvent.VK_NUMPAD7 && Board.isFieldFree(7)) {
            Board.pickField(7);
        }
        if (key_id == KeyEvent.VK_NUMPAD8 && Board.isFieldFree(8)) {
            Board.pickField(8);
        }
        if (key_id == KeyEvent.VK_NUMPAD9 && Board.isFieldFree(9)) {
            Board.pickField(9);
        }
        if (key_id == KeyEvent.VK_E) {
            Game.stopGame();
        }
        if (key_id == KeyEvent.VK_R) {
            Game.resetGame();
        }
        System.out.println("keyPressed is done");
    }
}
