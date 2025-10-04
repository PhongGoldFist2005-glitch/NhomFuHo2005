package game.core;

import javax.swing.JFrame;

/**
 * Tạo Frame để chạy các tác vụ.
 */
public class App {
    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("Brick Breaker");
        frame.setResizable(false);
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        
        // Thêm game manager vào để cấu hình phần frame
        GameManager gameManager = new GameManager();
        frame.add(gameManager);
        frame.pack();

        frame.setLocationRelativeTo(null);
        gameManager.gameThread();
        frame.setVisible(true);
    }
}
