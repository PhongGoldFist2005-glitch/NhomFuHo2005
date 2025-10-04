package game.core;

import javax.swing.JPanel;

import game.entities.Paddle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameManager extends JPanel implements Runnable {
    // widthPix, heightPix (Dựa trên kích thước của gạch)
    // là kích thước của nhân vật thực tế.
    private final int widthPix = 16;
    private final int heightPix = 14;
    // scale là hệ số để scale kích thước của nhân vật.
    private final int scale = 3;
    // rowCount, colCount là số hàng và số cột tối đa của màn hình game.
    // Yêu cầu đủ kích thước để bỏ hết đống gạch vào trong màn hình.
    private final int rowCount =  20;
    private final int colCount = 18;
    // Kích thước của toàn màn hình
    private int boardWidth = widthPix * scale * rowCount;
    private int boardHeight = heightPix * scale * colCount;

    // FPS của trò chơi: (Từng khung hình vẽ trên 1 giây)
    private final int FPS = 60;
    // Object theo dõi hoạt động của bàn phím.
    private KeyPress keyBoard = new KeyPress();
    // Có thể thay đổi cho từng vật thể
    // Vị trí của vật thể trong game (Từng vật thể lại khác nhau)
    int positionX = 100;
    int positionY = 100;
    int velocity = 4;

    // Gọi các đối tượng của class ra
    Paddle paddle = new Paddle(keyBoard, this);

    private Thread gameRuning;

    /**
     * Method để chạy thread (1 luồng mới).
     */
    public void gameThread() {
        gameRuning = new Thread(this);
        gameRuning.start();
    }

    /**
     * Constructor of GameManager.
     */
    public GameManager() {
        // Quản lý màn hình game
        // Truyền kích thước của cửa sổ game.
        this.setPreferredSize(new Dimension(boardWidth, boardHeight));
        // Truyền màu cho background của cửa sổ
        this.setBackground(Color.BLACK);
        // Cho phép lưu trữ các buffer của các nhân vật trong game
        this.setDoubleBuffered(true);

        // Quản lý bàn phím
        // cho panel nhận phím và đăng ký listener
        setFocusable(true);
        addKeyListener(keyBoard);
    }

    /**
     * Game Loop.
     * Tác dụng là xây dựng 1 vòng lặp người chơi sẽ
     * thao tác trong 1 luồng
     * luồng đấy liên tục cập nhật dữ liệu
     * và vẽ các hình ảnh tương ứng lên màn hình.
     */
    @Override
    public void run() {
        // Chỉnh cho FPS: 60
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameRuning != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // Update
                update();
                // Repaint
                repaint();
                delta --;
            }
        }
    }

    /**
     * Theo dõi sự thay đổi của người dùng.
     * tác động lên các class trong game.
     * Các vật thể sẽ được overwrite lại
     * các hàm này.
     */
    public void update() {
        paddle.update();
    }

    /**
     * Vẽ vật thể lên màn hình.
     * Các vật thể sẽ được overwrite lại
     * các hàm này
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        paddle.render(g2);
        g2.dispose();
    }

    /**
     * Getter & Setter
     * @return
     */
    public int getBoardWidth() {
        return boardWidth;
    }
    public int getBoardHeight() {
        return boardHeight;
    }
}
