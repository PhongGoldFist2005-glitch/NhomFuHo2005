package game.core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.util.List;
import java.awt.Color;
import java.awt.GridLayout;


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Tạo Frame để chạy các tác vụ.
 */
public class App {
    /**
     * Hàm main chạy chương trình game.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        JFrame menuFrame = new JFrame("Menu");
        menuFrame.setResizable(false);
        menuFrame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        // Lấy kích thước của game chơi gán vào panel.
        GameManager gameManager = GameManager.getInstance();
        float gameWidth = gameManager.getBoardWidth();
        float gameHeight = gameManager.getBoardHeight();

        // Sét kích thước của màn game.
        menuFrame.setSize(new Dimension((int) gameWidth, (int) gameHeight));
        menuFrame.setLocationRelativeTo(null);

        // Xây dựng background cho jframe.
        JPanel menuPanel = new JPanel() {
            String imageUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\MainScreen.jpeg";
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

        // Xây dựng nhạc cho sảnh chờ game.
        String musicFile = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\sounds\\background.wav";
        Music musicHall = new Music(musicFile);
        musicHall.play();

        // Nút start game.
        JButton startButton = new JButton("Start Game");
        startButton.setBounds((int) (gameWidth / 2) - 100,(int) (gameHeight / 2) - 100, 200, 55);
        startButton.setFocusable(false);
        ImageIcon startIcon = new ImageIcon("C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\play.png");
        startButton.setText("");
        startButton.setIcon(startIcon);
        startButton.setHorizontalTextPosition(SwingConstants.CENTER);
        startButton.setVerticalTextPosition(SwingConstants.CENTER);
        startButton.addActionListener(e -> openLevelSelectorDialog(menuFrame, gameManager, musicHall));
        menuFrame.add(startButton, BorderLayout.CENTER);

        // Nút tắt mở nhạc.
        JButton musicButton = new JButton("Turn On Music");
        musicButton.setBounds((int) (gameWidth / 2) - 100,(int) (gameHeight / 2), 200, 55);
        musicButton.setFocusable(false);
        ImageIcon musicIcon = new ImageIcon("C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\audio.png");
        musicButton.setText("");
        musicButton.setIcon(musicIcon);
        musicButton.setHorizontalTextPosition(SwingConstants.CENTER);
        musicButton.setVerticalTextPosition(SwingConstants.CENTER);
        musicButton.addActionListener(e -> musicOn(musicHall));
        menuFrame.add(musicButton);

        // Nút thoát game.
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds((int) (gameWidth / 2) - 100,(int) (gameHeight / 2) + 100, 200, 55);
        exitButton.setFocusable(false);
        ImageIcon exitIcon = new ImageIcon("C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\exit.png");
        exitButton.setText("");
        exitButton.setIcon(exitIcon);
        exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        exitButton.setVerticalTextPosition(SwingConstants.CENTER);
        exitButton.addActionListener(e -> exit());
        menuFrame.add(exitButton);

        menuFrame.setVisible(true);
    }

    /**
     * Xây dựng frame để mở chọn level.
     * @param owner
     * @param gameManager
     * @param musicHall
     */
    private static void openLevelSelectorDialog(JFrame owner, GameManager gameManager, Music musicHall) {
        try {
            JDialog dialog = new JDialog(owner, "Chọn màn", JDialog.ModalityType.APPLICATION_MODAL);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            String menuBackgroundUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\MenuBackground.png";
            Image menuBackground = new ImageIcon(menuBackgroundUrl).getImage();

            // Tạo background cho chọn màn.
            JPanel panel = new JPanel(){
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(menuBackground, 0, 0, getWidth(), getHeight(), this);
                }
            };

            panel.setLayout(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

            // Bố trí các nút chọn màn theo dạng lưới.
            JPanel buttonPanel = new JPanel(new GridLayout(0, 4, 8, 8));
            
            // quan trọng: không che nền.
            buttonPanel.setOpaque(false);

            // Các level có.
            String levelJsonPath = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\game\\levels\\level.json";
            List<LevelLoader.Level> levels;
            try {
                levels = LevelLoader.loadLevels(levelJsonPath);
            } catch (Exception ex) {
                levels = java.util.Collections.emptyList();
            }

            int total = Math.max(1, levels.size());
            for (int i = 0; i < total; i++) {
                final int idx = i;
                JButton b = new JButton("Màn " + (i + 1));

                // Nút trong suốt.
                b.setContentAreaFilled(false);
                b.setOpaque(false);
                b.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // viền dày 2px
                b.setMargin(new Insets(0, 4, 0, 4)); // viền sát chữ
                b.setForeground(Color.WHITE);
                b.setFont(new Font("MedievalSharp", Font.BOLD, 20));

                b.addActionListener(ev -> {
                    dialog.dispose();
                    startGame(owner, gameManager, musicHall, idx);
                });
                buttonPanel.add(b);
            }


            JButton cancel = new JButton("Hủy");
            cancel.addActionListener(ev -> dialog.dispose());

            cancel.setContentAreaFilled(false);
            cancel.setOpaque(false);
            cancel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // viền dày 2px
            cancel.setMargin(new Insets(4, 10, 4, 10)); // viền sát chữ
            cancel.setForeground(Color.WHITE);
            cancel.setFont(new Font("MedievalSharp", Font.BOLD, 20));

            buttonPanel.add(cancel);

            // Thêm panel nút vào giữa.
            panel.add(buttonPanel, BorderLayout.CENTER);

            dialog.setContentPane(panel);
            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(owner);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method xảy ra khi thoát game.
     */
    private static void exit() {
        System.exit(0);
    }

    /**
     * Method xảy ra khi mở nhạc.
     */
    private static void musicOn(Music musicHall) {
        musicHall.toggle();
        System.out.println("Music on");
    }

    /**
     * Method để chơi game.
     */
    private static void startGame(JFrame menuFrame, GameManager gameManager, Music musicHall, int levelIndex) {
        musicHall.stop();
        Music gameMusic = gameManager.getGameMusic();
        gameMusic.play();
        JFrame frame = new JFrame("Brick Breaker");
        frame.setResizable(false);
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        // Thêm game manager vào để cấu hình phần frame.
        frame.add(gameManager);
        frame.pack();

        frame.setLocationRelativeTo(null);
        gameManager.gameThread();
        menuFrame.setVisible(false);
        frame.setVisible(true);

        // Load level được chọn.
        try {
            // Load lại game từ màn được chọn.
            gameManager.setLevel(levelIndex);
            gameManager.restartGame(levelIndex, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

