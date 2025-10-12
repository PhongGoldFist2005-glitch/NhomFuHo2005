package game.core;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import game.entities.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.Image;
import java.io.IOException;

public class GameManager extends JPanel implements Runnable {
    // widthPix, heightPix (Dựa trên kích thước của gạch)
    // là kích thước của nhân vật thực tế.
    private final int widthPix = 16;
    private final int heightPix = 10;
    // scale là hệ số để scale kích thước của nhân vật.
    private final int scale = 3;
    // rowCount, colCount là số hàng và số cột tối đa của màn hình game.
    // Yêu cầu đủ kích thước để bỏ hết đống gạch vào trong màn hình.
    private final int rowCount = 20;
    private final int colCount = 18;
    // Chiều rộng chiều cao thực tế của gạch
    int brickWidth = widthPix * scale;
    int brickHeight = heightPix * scale;
    // Kích thước của toàn màn hình
    // Scale
    private int offset = 250;
    public float boardWidth = widthPix * scale * rowCount + 2 * offset;
    public float boardHeight = heightPix * scale * colCount + offset / 2;

    // FPS của trò chơi: (Từng khung hình vẽ trên 1 giây)
    private final int FPS = 60;
    private String backgroundURL = "C:\\Users\\Admin\\IdeaProjects\\Ankanoid\\NhomFuHo2005\\ProjectRoot\\src\\game\\uibackground_fire.png";
    private Image background = new ImageIcon(backgroundURL).getImage();
    // Object theo dõi hoạt động của bàn phím.
    private KeyPress keyBoard = new KeyPress();

    // Khai báo biến map
    private int[][] map;
    // Khai báo biến âm thanh
    private static String musicUrl = "C:\\Users\\Admin\\IdeaProjects\\Ankanoid\\NhomFuHo2005\\ProjectRoot\\src\\assets\\sounds\\gameplay.wav";
    Music gameMusicPlay;

    // Gọi các đối tượng của class ra
    Paddle paddle = new Paddle(keyBoard, this);
    Ball ball = new Ball(keyBoard, this);
    List<NormalBrick> brickList = new ArrayList<>();
    List<StrongBrick> strongBList = new ArrayList<>();

    private Thread gameRuning;

    /**
     * Method để chạy thread (1 luồng mới).
     */
    public void gameThread() {
        gameRuning = new Thread(this);
        gameRuning.start();
    }

    /**
     * Điều khiển âm thanh chơi game.
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void gameMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        gameMusicPlay.play();
    }

    /**
     * Constructor of GameManager.
     * @throws LineUnavailableException
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    public GameManager() throws UnsupportedAudioFileException, IOException, LineUnavailableException, UnsupportedAudioFileException, LineUnavailableException {
        // Load levels trong constructor mac dinh la level 0
        loadLevels(3);
        // Load nhạc sẵn.
        gameMusicPlay = new Music(musicUrl);
        // Quản lý màn hình game
        // Truyền kích thước của cửa sổ game.
        this.setPreferredSize(new Dimension((int) boardWidth,(int) boardHeight));
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
     * Method để load levels từ file JSON
     */
    private void loadLevels(int typeLevel) {
        List<LevelLoader.Level> levels = LevelLoader.loadLevels("C:\\Users\\Admin\\IdeaProjects\\Ankanoid\\NhomFuHo2005\\ProjectRoot\\src\\game\\levels\\level.json");

        LevelLoader.Level level = levels.get(typeLevel);
        map = level.map;

        // In ra để kiểm tra
        float gap = 10;
        float Y_pos = 50;
        System.out.println("Loaded map:");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < map.length; i++) {
            float brickFullWidth = (map[i].length) * 48 + (map[i].length - 1) * gap;
            float X_pos = (boardWidth - brickFullWidth) / 2;
            // System.out.println(boardWidth);
            // System.out.println(X_pos);
            // System.out.println(brickFullWidth);
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 1) {
                    NormalBrick normalBrick = new NormalBrick(X_pos,Y_pos, this);
                    brickList.add(normalBrick);
                }
                else if (map[i][j] == 2) {
                    StrongBrick strongBrick = new StrongBrick(X_pos, Y_pos, this);
                    strongBList.add(strongBrick);
                }
                X_pos += brickWidth + gap;
            }
            Y_pos += brickHeight + gap;
            System.out.println();
        }
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
        ball.checkCollision(paddle);

        Iterator<NormalBrick> normalBrickIterator = brickList.iterator();
        while (normalBrickIterator.hasNext()) {
            Brick brick = normalBrickIterator.next();
            if (brick.isDestroyed()) {
                normalBrickIterator.remove();
            }
            brick.update();
            ball.checkCollision(brick);
        }

        Iterator<StrongBrick> strongBrickIterator = strongBList.iterator();
        while (strongBrickIterator.hasNext()) {
            StrongBrick strongBrick = strongBrickIterator.next();
            if (strongBrick.isDestroyed()) {
                strongBrickIterator.remove();
            }
            strongBrick.update();
            ball.checkCollision(strongBrick);
        }

//        for (NormalBrick brick : brickList) {
//            brick.update();
//            ball.checkCollision(brick);
//        }
//        for(StrongBrick strongBrick : strongBList){
//            strongBrick.update();
//            ball.checkCollision(strongBrick);
//        }
        ball.update();
    }

    /**
     * Vẽ vật thể lên màn hình.
     * Các vật thể sẽ được overwrite lại
     * các hàm này
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(background, 0, 0,(int) this.getBoardWidth(),(int) this.getBoardHeight(), null);
        for (NormalBrick brick : brickList) {
            brick.render(g2);
        }
        for (StrongBrick brick : strongBList) {
            brick.render(g2);
        }
        paddle.render(g2);
        ball.render(g2);
        g2.dispose();
    }

    /**
     * Getter & Setter
     * @return
     */
    public float getBoardWidth() {
        return boardWidth;
    }
    public float getBoardHeight() {
        return boardHeight;
    }
    
    public int[][] getMap() {
        return map;
    }

    public Music getGameMusic() {
        return this.gameMusicPlay;
    }
}

// Ăn power up load background mới.