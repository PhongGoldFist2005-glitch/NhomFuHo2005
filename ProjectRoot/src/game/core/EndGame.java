package game.core;

import java.awt.*;
import javax.swing.*;

public class EndGame {
    private int endGameWidth;
    private int endGameHeight;
    private static final int endButtonWidth = 200;
    private static final int endButtonHeight = 55;
    private GameManager gameManager;

    public EndGame(GameManager gameManager) throws Exception {
        JFrame endFrame = new JFrame("End Game");
        this.gameManager = gameManager;
        this.endGameWidth = (int) this.gameManager.getBoardWidth();
        this.endGameHeight = (int) this.gameManager.getBoardHeight();
        endFrame.setResizable(false);
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // endFrame.setSize(new Dimension(endGameWidth, endGameHeight));
        endFrame.setLocationRelativeTo(null);

        // Background panel
        JPanel endPanel = new JPanel() {
            String imageUrl = "C:\\Users\\Admin\\IdeaProjects\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\MainScreen.jpeg";
            private Image background = new ImageIcon(imageUrl).getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0,endGameWidth,endGameHeight, null);
            }
        };
        
        endFrame.setContentPane(endPanel);
        endPanel.setPreferredSize(new Dimension(endGameWidth, endGameHeight));
        endFrame.pack();
        endFrame.setLayout(null);
        endFrame.setLocationRelativeTo(null);

        // Restart button
        JButton restartButton = new JButton();
        restartButton.setBounds((endGameWidth - endButtonWidth) / 2, (endGameHeight / 2) - 50, endButtonWidth, endButtonHeight);
        restartButton.setFocusable(false);
        ImageIcon restartIcon = new ImageIcon("C:\\Users\\Admin\\IdeaProjects\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\play.png");
        restartButton.setIcon(restartIcon);
        restartButton.addActionListener(e -> {
            try {
                gameManager.restartGame(gameManager.getLevel(), 0);
                endFrame.dispose(); // đóng khung end game
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        endFrame.add(restartButton);

        // Exit button
        JButton exitButton = new JButton();
        exitButton.setBounds((endGameWidth - endButtonWidth) / 2, (endGameHeight / 2) + 50, endButtonWidth, endButtonHeight);
        ImageIcon exitIcon = new ImageIcon("C:\\Users\\Admin\\IdeaProjects\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\exit.png");
        exitButton.setIcon(exitIcon);
        exitButton.addActionListener(e -> System.exit(0));
        endFrame.add(exitButton);

        // endFrame.pack();
        endFrame.setVisible(true);
    }
}
