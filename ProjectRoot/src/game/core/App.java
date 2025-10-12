package game.core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Tạo Frame để chạy các tác vụ.
 */
public class App {
    public static void main(String[] args) throws Exception {
        JFrame menuFrame = new JFrame("Menu");
        menuFrame.setResizable(false);
        menuFrame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        // Lấy kích thước của game chơi gán vào panel.
        GameManager gameManager = new GameManager();
        float gameWidth = gameManager.getBoardWidth();
        float gameHeight = gameManager.getBoardHeight();

        menuFrame.setSize(new Dimension((int) gameWidth, (int) gameHeight));
        menuFrame.setLocationRelativeTo(null);

        // Xây dựng background cho jframe.
        JPanel menuPanel = new JPanel() {
            String imageUrl = "C:\\Users\\Admin\\IdeaProjects\\Ankanoid\\NhomFuHo2005\\ProjectRoot\\src\\game\\ui\\MainScreen.jpeg";
            private Image background = new ImageIcon(imageUrl).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, (int) gameWidth,(int) gameHeight, null);
            }
        };
        // Gán background vào frame.
        menuFrame.setContentPane(menuPanel);
        menuFrame.setLayout(null);
    }
}
