package game.core;

import java.awt.*;
import javax.swing.*;

/**
 * Xây dựng lớp kết thúc game.
 */
public class EndGame {
    
    // Xây dựng kích thước endGame.
    private int endGameWidth;
    private int endGameHeight;

    // Kích thước của nút bấm.
    private static final int endButtonWidth = 200;
    private static final int endButtonHeight = 55;
    private GameManager gameManager;

    /**
     * EndGame constructor.
     * @param gameManager
     * @throws Exception
     */
    public EndGame(GameManager gameManager) throws Exception {
        JFrame endFrame = new JFrame("End Game");
        this.gameManager = gameManager;
        this.endGameWidth = (int) this.gameManager.getBoardWidth();
        this.endGameHeight = (int) this.gameManager.getBoardHeight();
        endFrame.setResizable(false);
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endFrame.setLocationRelativeTo(null);

        // Background panel.
        JPanel endPanel = new JPanel() {
            String imageUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\MainScreen.jpeg";
            private Image background = new ImageIcon(imageUrl).getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0,endGameWidth,endGameHeight, null);
            }
        };

        // Đưa panel của endGame vào endFrame.
        endFrame.setContentPane(endPanel);
        endPanel.setPreferredSize(new Dimension(endGameWidth, endGameHeight));
        endFrame.pack();
        endFrame.setLayout(null);
        endFrame.setLocationRelativeTo(null);
        
        // Thêm phan hiện điểm số.
        int finalScore = gameManager.getMyScore();
        int highScore = gameManager.getHighScore();

        // Label hiển thị điểm cuối cùng.
        JLabel scoreLabel = new JLabel("Score: " + finalScore);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 36));
        // Đặt màu chữ là Trắng.
        scoreLabel.setForeground(Color.WHITE);
        // Đặt vị trí.
        scoreLabel.setBounds(0, (endGameHeight / 2) - 80, endGameWidth, 50);
        // Căn giữa chữ.
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        endPanel.add(scoreLabel);

        // Label hiển thị điểm cao nhất.
        JLabel highScoreLabel = new JLabel("High Score: " + highScore);
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        // Đặt màu chữ là Vàng.
        highScoreLabel.setForeground(Color.YELLOW); 
        // Đặt vị trí.
        highScoreLabel.setBounds(0, (endGameHeight / 2) - 120, endGameWidth, 40);
        // Căn giữa chữ.
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        endPanel.add(highScoreLabel);


        //Có điều chỉnh lại khoảng cách giữa các nút.
        // Restart button.
        JButton restartButton = new JButton();
        restartButton.setBounds((endGameWidth - endButtonWidth) / 2, (endGameHeight / 2), endButtonWidth, endButtonHeight);
        restartButton.setFocusable(false);
        ImageIcon restartIcon = new ImageIcon("C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\play.png");
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

        // Exit button.
        JButton exitButton = new JButton();
        exitButton.setBounds((endGameWidth - endButtonWidth) / 2, (endGameHeight / 2) + 100, endButtonWidth, endButtonHeight);
        ImageIcon exitIcon = new ImageIcon("C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\exit.png");
        exitButton.setIcon(exitIcon);
        exitButton.addActionListener(e -> System.exit(0));
        endFrame.add(exitButton);

        endFrame.setVisible(true);
    }
}
