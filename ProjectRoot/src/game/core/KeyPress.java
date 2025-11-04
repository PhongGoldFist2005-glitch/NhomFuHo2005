package game.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Action để lắng nghe bàn phím.
 */
public class KeyPress implements KeyListener {

    public boolean moveUp;
    public boolean moveDown;
    public boolean moveLeft;
    public boolean moveRight;
    public boolean launch;

    /**
     * Hành động khi nhấc keypass ra.
     * Do game không có thao tác nhấc phím.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Pass
    }

    /**
     * Hành động gửi đi khi nhấn phím xuống.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Mỗi nút bấm lại có 1 code khác nhau.
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            moveUp = true;
        }

        if (keyCode == KeyEvent.VK_S) {
            moveDown = true;
        }

        if (keyCode == KeyEvent.VK_A) {
            moveLeft = true;
        }

        if (keyCode == KeyEvent.VK_D) {
            moveRight = true;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            launch = true;
        }
    }

    /**
     * Được gửi khi bạn nhả phím đó trước đó ra.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Mỗi nút bấm lại có 1 code khác nhau.
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            moveUp = false;
        }

        if (keyCode == KeyEvent.VK_S) {
            moveDown = false;
        }

        if (keyCode == KeyEvent.VK_A) {
            moveLeft = false;
        }

        if (keyCode == KeyEvent.VK_D) {
            moveRight = false;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            launch = false;
        }
    }
}