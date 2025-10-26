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
            String imageUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\MainScreen.jpeg";
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

        String musicFile = "C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\sounds\\background.wav";
        Music musicHall = new Music(musicFile);
        musicHall.play();

        // Nút start game.
        JButton startButton = new JButton("Start Game");
        startButton.setBounds((int) (gameWidth / 2) - 100,(int) (gameHeight / 2) - 100, 200, 55);
        startButton.setFocusable(false);
        ImageIcon startIcon = new ImageIcon("C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\play.png");
        startButton.setText("");
        startButton.setIcon(startIcon);
        startButton.setHorizontalTextPosition(SwingConstants.CENTER);
        startButton.setVerticalTextPosition(SwingConstants.CENTER);
        startButton.addActionListener(e -> startGame(menuFrame, gameManager, musicHall));
        menuFrame.add(startButton, BorderLayout.CENTER);

        // Nút tắt mở nhạc.
        JButton musicButton = new JButton("Turn On Music");
        musicButton.setBounds((int) (gameWidth / 2) - 100,(int) (gameHeight / 2), 200, 55);
        musicButton.setFocusable(false);
        ImageIcon musicIcon = new ImageIcon("C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\audio.png");
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
        ImageIcon exitIcon = new ImageIcon("C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\exit.png");
        exitButton.setText("");
        exitButton.setIcon(exitIcon);
        exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        exitButton.setVerticalTextPosition(SwingConstants.CENTER);
        exitButton.addActionListener(e -> exit());
        menuFrame.add(exitButton);

        menuFrame.setVisible(true);
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
    private static void startGame(JFrame menuFrame, GameManager gameManager, Music musicHall) {
        musicHall.stop();
        Music gameMusic = gameManager.getGameMusic();
        gameMusic.play();
        JFrame frame = new JFrame("Brick Breaker");
        frame.setResizable(false);
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        // Thêm game manager vào để cấu hình phần frame
        frame.add(gameManager);
        frame.pack();

        frame.setLocationRelativeTo(null);
        gameManager.gameThread();
        menuFrame.setVisible(false);
        frame.setVisible(true);
    }
}

// Bug resstart padddle ko ve vi tri ban dau
// Power Up còn lại
// https://in.pinterest.com/pin/501236633558057870/
// https://pngtree.com/canvas/?pid=548180&source_from=user_template&token=7093bf2a1a3b39027accca8bf8a64916.1760110166&action=text&lang=ru