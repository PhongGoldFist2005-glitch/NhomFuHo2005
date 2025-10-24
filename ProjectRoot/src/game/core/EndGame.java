package game.core;

import java.awt.*;
import javax.swing.*;

public class EndGame {
    private static final int endGameWidth = 400;
    private static final int endGameHeight = 400;
    private static final int endButtonWidth = 100;
    private static final int endButtonHeight = 30;

    public EndGame(GameManager gameManager) throws Exception {
        JFrame endFrame = new JFrame("End Game");
        endFrame.setResizable(false);
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endFrame.setSize(new Dimension(endGameWidth, endGameHeight));
        endFrame.setLocationRelativeTo(null);

        // Background panel
        JPanel endPanel = new JPanel() {
            String imageUrl = "E:\\OOP\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\MainScreen.jpeg";
            private Image background = new ImageIcon(imageUrl).getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, endGameWidth, endGameHeight, null);
            }
        };
        endFrame.setContentPane(endPanel);
        endFrame.setLayout(null);

        // Restart button
        JButton restartButton = new JButton();
        restartButton.setBounds((endGameWidth - endButtonWidth) / 2, (endGameHeight / 2) - 50, endButtonWidth, endButtonHeight);
        restartButton.setFocusable(false);
        ImageIcon restartIcon = new ImageIcon("E:\\OOP\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\play.png");
        restartButton.setIcon(restartIcon);
        restartButton.addActionListener(e -> {
            try {
                gameManager.restartGame(gameManager.getLevel());
                endFrame.dispose(); // đóng khung end game
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        endFrame.add(restartButton);

        // Exit button
        JButton exitButton = new JButton();
        exitButton.setBounds((endGameWidth - endButtonWidth) / 2, (endGameHeight / 2) + 50, endButtonWidth, endButtonHeight);
        ImageIcon exitIcon = new ImageIcon("E:\\OOP\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\exit.png");
        exitButton.setIcon(exitIcon);
        exitButton.addActionListener(e -> System.exit(0));
        endFrame.add(exitButton);

        endFrame.setVisible(true);
    }
}
