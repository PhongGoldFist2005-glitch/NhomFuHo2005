package game.core;

import javax.sound.sampled.*;
import javax.swing.*;

import game.entities.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
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
    
    private String backgroundURL = "C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\background_earth.jpg";
    private Image background = new ImageIcon(backgroundURL).getImage();
    
    // Object theo dõi hoạt động của bàn phím.
    private KeyPress keyBoard = new KeyPress();

    // Mạng tối đa của người chơi.
    private int soul = 3;
    String thHeartsUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\3hearts.png";
    String twHeartsUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\2hearts.png";
    String onHeartUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\1heart.png";
    Image heartImage;
    // Level hiện tại của người chơi.
    private int myLevel = 0;
    private boolean winAll = false;

    // Khai báo biến map
    private int[][] map;
    // Khai báo biến âm thanh
    private static String musicUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\sounds\\gameplay.wav";
    private String hitPaddleSoundUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\sounds\\hit_brick.wav";
    private String hitBrickSoundUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\sounds\\hit_brick.wav";
    private String breakBrickSoundUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\sounds\\break_brick.wav";
    Music gameMusicPlay;

    // Gọi các đối tượng của class ra
    Paddle paddle = new Paddle(keyBoard, this);
    Ball ball = new Ball(keyBoard, paddle, this);
    ExpandPaddlePowerUp paddlePower;
    FastBallPowerUp fastBallPowerUp;
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

    private JButton musicButton;
    /**
     * Constructor of GameManager.
     * @throws LineUnavailableException
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    public GameManager() throws UnsupportedAudioFileException, IOException, LineUnavailableException, UnsupportedAudioFileException, LineUnavailableException {
        // Load levels trong constructor mac dinh la level 0
        loadLevels(0);
        // Load nhạc sẵn.
        gameMusicPlay = new Music(musicUrl);

        // Quản lý màn hình game
        // Truyền kích thước của cửa sổ game.
        this.setPreferredSize(new Dimension((int) boardWidth,(int) boardHeight));
        // Truyền màu cho background của cửa sổ
        this.setBackground(Color.BLACK);
        // Cho phép lưu trữ các buffer của các nhân vật trong game
        this.setDoubleBuffered(true);

        this.setLayout(null);

        // Quản lý bàn phím
        // cho panel nhận phím và đăng ký listener
        setFocusable(true);
        addKeyListener(keyBoard);

        // them nut bat tat am thanh khi dang choi
        musicButton = new JButton();
        musicButton.setBounds(10,10,50,50);
        musicButton.setFocusable(false);
        updatePlayGameMusic();
        musicButton.setHorizontalTextPosition(SwingConstants.CENTER);
        musicButton.setVerticalTextPosition(SwingConstants.CENTER);
        musicButton.addActionListener(e -> gameMusicPlay.toggle());
        this.add(musicButton);
        this.setVisible(true);
    }

    /**
     * Method để load levels từ file JSON
     */
    private void loadLevels(int typeLevel) {
        List<LevelLoader.Level> levels = LevelLoader.loadLevels("C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\game\\levels\\level.json");
        
        if (!brickList.isEmpty()) {
            brickList.clear();
        }

        if (!strongBList.isEmpty()) {
            strongBList.clear();
        }
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
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 1) {
                    NormalBrick normalBrick = new NormalBrick(X_pos,Y_pos, this);
                    brickList.add(normalBrick);
                }
                else if (map[i][j] == 2) {
                    StrongBrick strongBrick = new StrongBrick(X_pos, Y_pos, this);
                    strongBList.add(strongBrick);
                }
                else if (map[i][j] == 3) {
                    StrongBrick strongBrick = new StrongBrick(X_pos, Y_pos, this);
                    strongBrick.setType(3);
                    strongBList.add(strongBrick);
                }
                else if (map[i][j] == 4) {
                    StrongBrick strongBrick = new StrongBrick(X_pos, Y_pos, this);
                    strongBrick.setType(4);
                    strongBList.add(strongBrick);
                }
                X_pos += brickWidth + gap;
            }
            Y_pos += brickHeight + gap;
        }
    }

    /**
     * Game Loop.
     * Tác dụng là xây dựng 1 vòng lặp người chơi sẽ
     * thao tác trong 1 luồng
     * luồng đấy liên tục cập nhật dữ liệu
     * và vẽ các hình ảnh tương ứng lên màn hình.
     */

    private volatile boolean running = false;
    @Override
    public void run() {
        running = true;
        // Chỉnh cho FPS: 60
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // Update
                update();
                if (winAll == true) {
                    break;
                }
                // Repaint
                repaint();
                delta --;
            }
        }
    }

    String urlSpeakerImage;
    public void updatePlayGameMusic(){
        urlSpeakerImage = (gameMusicPlay.isPlaying())
                ? "C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\MusicOn.jpg"
                : "C:\\Users\\admin\\Documents\\GitHub\\NhoFuHo2005Temp\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\MusicOff.jpg";
        musicButton.setIcon(new ImageIcon(urlSpeakerImage));
    }

    /**
     * Theo dõi sự thay đổi của người dùng.
     * tác động lên các class trong game.
     * Các vật thể sẽ được overwrite lại
     * các hàm này.
     */
    public void update() {
        if (this.soul == 0) {
            running = false;
            SwingUtilities.invokeLater(() -> {
                try {
                    if (gameRuning != null && gameRuning.isAlive()) {
                        gameRuning.join();
                    }
                    new EndGame(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return;
        }
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
                if (strongBrick.getType() == 3) {
                    this.paddlePower = new ExpandPaddlePowerUp(strongBrick.getX(), strongBrick.getY(), paddle, this, ball);
                }
                if (strongBrick.getType() == 4) {
                    this.fastBallPowerUp = new FastBallPowerUp(strongBrick.getX(), strongBrick.getY(), paddle, this, ball);
                }
                strongBrickIterator.remove();
            }

            strongBrick.update();
            ball.checkCollision(strongBrick);
        }

        if (paddlePower != null) {
            this.paddlePower.update();
            if (!paddlePower.getPower() && this.paddlePower.getY() > getBoardHeight()) {
                this.paddlePower = null;
            }
        }
        if (fastBallPowerUp != null) {
            this.fastBallPowerUp.update();
            if (!fastBallPowerUp.getPower() && this.fastBallPowerUp.getY() > getBoardHeight()) {
                this.fastBallPowerUp = null;
            }
        }
        ball.update();

        if (brickList.isEmpty() && strongBList.isEmpty()) {
            // → Tất cả gạch đã bị phá hủy
            myLevel += 1;
            if (myLevel >= 10) {
                System.out.println("You are the champion !");
                winAll = true;
            } else {
                SwingUtilities.invokeLater(() -> {
                    try {
                        restartGame(myLevel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        updatePlayGameMusic();
    }

    /**
     * Phương thức mới để phát các hiệu ứng âm thanh (SFX) ngắn, chỉ một lần.
     * @param soundFilePath Đường dẫn đến file .wav
     */
    public void playSoundEffect(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(audioStream);

            // Thêm một listener để tự động đóng clip khi nó phát xong
            // Điều này ngăn rò rỉ bộ nhớ từ hàng ngàn clip đang mở
            soundClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    event.getLine().close();
                }
            });

            soundClip.start(); // Phát một lần
        } catch (Exception e) {
            System.err.println("Không thể phát hiệu ứng âm thanh: " + e.getMessage());
        }
    }


    /**
     * Vẽ vật thể lên màn hình.
     * Các vật thể sẽ được overwrite lại
     * các hàm này
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.soul == 3) {
            this.heartImage = new ImageIcon(thHeartsUrl).getImage();
        } else if (this.soul == 2) {
            this.heartImage = new ImageIcon(twHeartsUrl).getImage();
        } else if (this.soul == 1){
            this.heartImage = new ImageIcon(onHeartUrl).getImage();
        }

        g.drawImage(background, 0, 0,(int) this.getBoardWidth(),(int) this.getBoardHeight(), null);
        
        g.drawImage(heartImage, (int) getBoardWidth() - 120, 20, 100, 30, null);

        g.drawImage(new ImageIcon(urlSpeakerImage).getImage(), 10,10,50,50,null);
        Graphics2D g2 = (Graphics2D) g;
        
        for (NormalBrick brick : brickList) {
            brick.render(g2);
        }
        for (StrongBrick brick : strongBList) {
            brick.render(g2);
        }
        if (this.paddlePower != null) {
            this.paddlePower.render(g2);
        }

        if (this.fastBallPowerUp != null) {
            this.fastBallPowerUp.render(g2);
        }

        paddle.render(g2);
        ball.render(g2);
        g2.dispose();
    }

    public void restartGame(int currentLevel) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (gameRuning != null && gameRuning.isAlive() && Thread.currentThread() != gameRuning) {
            running = false; // tắt cờ để thread thoát
            try {
                gameRuning.join(); // chờ thread kết thúc hẳn
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * Hồi phục mạng gốc.
         */
        this.soul = 3;
        
        /**
         * Điều chỉnh âm thanh về mặc định.
         */
        gameMusicPlay.stop();
        gameMusicPlay = new Music(musicUrl);
        gameMusicPlay.toggle();

        /**
         * Load lại map, paddle, gạch game đang thua.
         */
        if (fastBallPowerUp != null) {
            fastBallPowerUp.setPower(false);
        }
        if (paddlePower != null) {
            paddlePower.setPower(false);
        }

        this.setBackGround(getDefaultBackGround());
        paddle.resetToDefault();
        ball.setDefaultBallValue();
        fastBallPowerUp = null;
        paddlePower = null;
        loadLevels(currentLevel);
        gameThread();
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

    public int getSoul() {
        return this.soul;
    }

    public void lostSoul() {
        this.soul -= 1;
    }

    public int getLevel() {
        return this.myLevel;
    }

    public void setLevel(int level) {
        this.myLevel = level;
    }

    public void setBackGround(String Url) {
        background = new ImageIcon(Url).getImage();
    }

    public String getDefaultBackGround() {
        return this.backgroundURL;
    }
    public String getHitPaddleSoundUrl() { return hitPaddleSoundUrl; }

    public String getHitBrickSoundUrl() { return hitBrickSoundUrl; }

    public String getBreakBrickSoundUrl() { return breakBrickSoundUrl; }
}

// Ăn power up load background mới.