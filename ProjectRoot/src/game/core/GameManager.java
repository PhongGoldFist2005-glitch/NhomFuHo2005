package game.core;

import javax.sound.sampled.*;
import javax.swing.*;
import game.entities.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Xây dựng lớp để quản lý logic game và các tác vụ trong game.
 * SINGLETON: Sử dụng GameManager.getInstance()
 */
public class GameManager extends JPanel implements Runnable {
    // Singleton instance
    private static GameManager instance;

    /**
     * Lấy instance duy nhất của GameManager (thread-safe).
     * Những chỗ khác trong project phải gọi GameManager.getInstance()
     */
    public static synchronized GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // widthPix, heightPix (Dựa trên kích thước của gạch).
    // là kích thước của nhân vật thực tế.
    private final int widthPix = 16;
    private final int heightPix = 10;

    // scale là hệ số để scale kích thước của nhân vật.
    private final int scale = 3;

    // rowCount, colCount là số hàng và số cột tối đa của màn hình game.
    // Yêu cầu đủ kích thước để bỏ hết đống gạch vào trong màn hình.
    private final int rowCount = 20;
    private final int colCount = 18;

    // Chiều rộng chiều cao thực tế của gạch.
    int brickWidth = widthPix * scale;
    int brickHeight = heightPix * scale;

    // Kích thước của toàn màn hình.
    // Scale.
    private int offset = 250;
    // 1460 x 665
    public float boardWidth = widthPix * scale * rowCount + 2 * offset;
    public float boardHeight = heightPix * scale * colCount + offset / 2;

    // FPS của trò chơi: (Từng khung hình vẽ trên 1 giây).
    private final int FPS = 60;

    // Lấy ảnh làm backGround và viền cho game.
    private String backgroundURL = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\base.jpg";
    private Image background = new ImageIcon(backgroundURL).getImage();
    private String gameFrame = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\gameFrame1.jpg";
    private Image gameFrameImage = new ImageIcon(gameFrame).getImage();

    // Object theo dõi hoạt động của bàn phím.
    private KeyPress keyBoard = new KeyPress();

    // Mạng tối đa của người chơi.
    private int soul = 3;
    String thHeartsUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\3hearts.png";
    String twHeartsUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\2hearts.png";
    String onHeartUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\1heart.png";
    Image heartImage;

    // Level hiện tại của người chơi.
    private int myLevel = 0;
    private boolean winAll = false;

    // Điểm số của người chơi.
    private int myScore = 0;
    private int highScore = 0; // Biến mới để lưu điểm cao nhất.
    private static final String HIGH_SCORE_FILE = "highscore.txt";
    Image scoreImage = new ImageIcon("C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\gameboard.png").getImage();

    // Khai báo biến map của gạch trong game.
    private int[][] map;

    // Khai báo biến âm thanh.
    private static String musicUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\sounds\\gameplay.wav";
    private String hitPaddleSoundUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\sounds\\hit_brick.wav";
    private String hitBrickSoundUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\sounds\\hit_brick.wav";
    private String breakBrickSoundUrl = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\sounds\\break_brick.wav";
    Music gameMusicPlay;

    // Gọi các đối tượng của class ra.
    Paddle paddle;
    Ball ball;
    ExpandPaddlePowerUp paddlePower;
    FastBallPowerUp fastBallPowerUp;

    // 2 loại gạch chính gạch 2 máu và gach 1 máu
    List<NormalBrick> brickList = new ArrayList<>();
    List<StrongBrick> strongBList = new ArrayList<>();

    // Power up cho loại power laze.
    List<Bullet> bulletList = new ArrayList<>();
    LaserPaddlePowerUp laserPower;
    private long lastShotTime = 0;
    // 300ms giữa mỗi lần bắn.
    private final long shootCooldown = 300;

    // Tạo biến lưu luồng của game.
    private Thread gameRuning;

    /**
     * Constructor private theo Singleton.
     * NOTE: không ném checked-exceptions ra ngoài, thay vào đó xử lý bên trong
     * để tiện cho lazy-getInstance().
     */
    private GameManager() {
        // Khởi tạo các đối tượng phụ thuộc vào this.
        paddle = new Paddle(keyBoard, this);
        ball = new Ball(keyBoard, paddle, this);

        try {
            // Load levels trong constructor mac dinh la level 0.
            loadLevels(0);
        } catch (Exception e) {
            // Nếu load levels thất bại, in stack và để map null hoặc rỗng.
            e.printStackTrace();
            map = new int[0][0];
        }

        // Load high_score.
        loadHighScore();

        // Load nhạc sẵn (nếu có lỗi thì in và để gameMusicPlay = null).
        try {
            gameMusicPlay = new Music(musicUrl);
        } catch (Exception e) {
            e.printStackTrace();
            gameMusicPlay = null;
        }

        // Quản lý màn hình game.
        this.setPreferredSize(new Dimension((int) boardWidth,(int) boardHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.setLayout(null);

        // Quản lý bàn phím.
        setFocusable(true);
        addKeyListener(keyBoard);

        // Thêm nút bấm âm thanh khi đang chơi.
        musicButton = new JButton();
        musicButton.setBounds(80,40,30,30);
        musicButton.setFocusable(false);
        updatePlayGameMusic();
        musicButton.setHorizontalTextPosition(SwingConstants.CENTER);
        musicButton.setVerticalTextPosition(SwingConstants.CENTER);
        musicButton.addActionListener(e -> {
            if (gameMusicPlay != null) gameMusicPlay.toggle();
            updatePlayGameMusic();
        });
        this.add(musicButton);
        this.setVisible(true);
    }

    /**
     * Method để chạy thread (1 luồng mới).
     */
    public void gameThread() {
        if (gameRuning != null && gameRuning.isAlive()) return;
        gameRuning = new Thread(this);
        gameRuning.start();
    }

    /**
     * Điều khiển âm thanh chơi game.
     */
    public void gameMusic() {
        if (gameMusicPlay != null) {
            try {
                gameMusicPlay.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Biến lưu trạng thái.
    private JButton musicButton;

    /**
     * Tải điểm cao nhất từ file khi game bắt đầu.
     */
    private void loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                highScore = Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            // Nếu file không tồn tại hoặc lỗi, điểm cao nhất là 0
            highScore = 0;
        }
    }

    /**
     * Lưu điểm cao nhất nếu điểm hiện tại cao hơn.
     */
    private void saveHighScore() {
        if (myScore > highScore) {
            highScore = myScore;
            try (PrintWriter writer = new PrintWriter(new FileWriter(HIGH_SCORE_FILE))) {
                writer.println(highScore);
            } catch (IOException e) {
                e.printStackTrace(); // In lỗi nếu không lưu được
            }
        }
    }

    /**
     * Method để load levels từ file JSON.
     */
    private void loadLevels(int typeLevel) {
        List<LevelLoader.Level> levels = LevelLoader.loadLevels("C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\game\\levels\\level.json");

        if (!brickList.isEmpty()) {
            brickList.clear();
        }

        if (!strongBList.isEmpty()) {
            strongBList.clear();
        }
        LevelLoader.Level level = levels.get(typeLevel);
        map = level.map;

        // In ra để kiểm tra.
        float gap = 10;
        float Y_pos = 100;
        System.out.println("Loaded map:");

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        // Set type để nhận dạng một số gạch có power up sau này.
        for (int i = 0; i < map.length; i++) {
            float brickFullWidth = (map[i].length) * 48 + (map[i].length - 1) * gap;
            float X_pos = (boardWidth - brickFullWidth) / 2;
            for (int j = 0; j < map[i].length; j++) {
                // Loại gạch 1 là gạch thường 1 máu.
                if (map[i][j] == 1) {
                    NormalBrick normalBrick = new NormalBrick(X_pos,Y_pos, this);
                    brickList.add(normalBrick);
                }

                // Loại gạch 2 là gạch cứng 2 máu.
                else if (map[i][j] == 2) {
                    StrongBrick strongBrick = new StrongBrick(X_pos, Y_pos, this);
                    strongBList.add(strongBrick);
                }

                // Loại gạch 3 là gạch 2 máu có power up kéo dài paddle.
                else if (map[i][j] == 3) {
                    StrongBrick strongBrick = new StrongBrick(X_pos, Y_pos, this);
                    strongBrick.setType(3);
                    strongBList.add(strongBrick);
                }

                // Loại gạch 4 là gạch có 2 máu có power up tăng tốc độ bóng.
                else if (map[i][j] == 4) {
                    StrongBrick strongBrick = new StrongBrick(X_pos, Y_pos, this);
                    strongBrick.setType(4);
                    strongBList.add(strongBrick);
                }

                // Loại gạch 5 là gạch cho phép bắn đạn.
                else if (map[i][j] == 5) {
                    StrongBrick strongBrick = new StrongBrick(X_pos, Y_pos, this);
                    strongBrick.setType(5);
                    strongBList.add(strongBrick);
                }
                // Cập nhật vị trí của viên gạch tiếp theo.
                X_pos += brickWidth + gap;
            }
            Y_pos += brickHeight + gap;
        }
    }

    /**
     * Game Loop.
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
    /**
     * Hàm cho phép click tắt mở nhạc trong game.
     */
    public void updatePlayGameMusic(){
        boolean playing = (gameMusicPlay != null && gameMusicPlay.isPlaying());
        urlSpeakerImage = (playing)
                ? "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\MusicOn.png"
                : "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\MusicOff.png";
        if (musicButton != null) {
            musicButton.setIcon(new ImageIcon(urlSpeakerImage));
        }
    }

    /**
     * Theo dõi sự thay đổi của người dùng.
     */
    public void update() {
        if (this.soul == 0) {
            saveHighScore();
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

        if (paddle.canShoot() && keyBoard.launch && ball.isLaunched()) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShotTime > shootCooldown) {
                float bulletX1 = paddle.getX() + 10;
                float bulletX2 = paddle.getX() + paddle.getWidth() - 10 - 8;
                float bulletY = paddle.getY();

                bulletList.add(new Bullet(bulletX1, bulletY));
                bulletList.add(new Bullet(bulletX2, bulletY));

                lastShotTime = currentTime;
            }
        }

        Iterator<NormalBrick> normalBrickIterator = brickList.iterator();
        while (normalBrickIterator.hasNext()) {
            Brick brick = normalBrickIterator.next();
            if (brick.isDestroyed()) {
                normalBrickIterator.remove();
                myScore += 100;
            }
            brick.update();
            ball.checkCollision(brick);
        }

        Iterator<StrongBrick> strongBrickIterator = strongBList.iterator();
        while (strongBrickIterator.hasNext()) {
            StrongBrick strongBrick = strongBrickIterator.next();
            if (strongBrick.isDestroyed()) {
                if (strongBrick.getType() == 3 && this.paddlePower == null && this.fastBallPowerUp == null && this.laserPower == null) {
                    this.paddlePower = new ExpandPaddlePowerUp(strongBrick.getX(), strongBrick.getY(), paddle, this, ball);
                }
                if (strongBrick.getType() == 4 && this.fastBallPowerUp == null && this.paddlePower == null && this.laserPower == null) {
                    this.fastBallPowerUp = new FastBallPowerUp(strongBrick.getX(), strongBrick.getY(), paddle, this, ball);
                }
                if (strongBrick.getType() == 5 && this.laserPower == null && this.paddlePower == null && this.fastBallPowerUp == null) {
                    this.laserPower = new LaserPaddlePowerUp(strongBrick.getX(), strongBrick.getY(), paddle, this, ball);
                }
                myScore += 200;
                strongBrickIterator.remove();
            }

            strongBrick.update();
            ball.checkCollision(strongBrick);
        }

        // Xóa sức mạnh nếu hết thời gian hiệu lực.
        if (paddlePower != null) {
            this.paddlePower.update();
            if (!paddlePower.getPower() && this.paddlePower.getY() > getBoardHeight()) {
                this.paddlePower.turnOffMusic();
                this.paddlePower = null;
            }
        }
        if (fastBallPowerUp != null) {
            this.fastBallPowerUp.update();
            if (!fastBallPowerUp.getPower() && this.fastBallPowerUp.getY() > getBoardHeight()) {
                this.fastBallPowerUp.turnOffMusic();
                this.fastBallPowerUp = null;
            }
        }
        if (laserPower != null) {
            this.laserPower.update();
            if (!laserPower.getPower() && this.laserPower.getY() > getBoardHeight()) {
                this.laserPower.turnOffMusic();
                this.laserPower = null;
            }
        }
        ball.update();

        Iterator<Bullet> bulletIterator = bulletList.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update();

            if (bullet.isOffScreen()) {
                bulletIterator.remove();
                continue;
            }

            boolean hitBrick = false;

            Iterator<NormalBrick> normBrickIt = brickList.iterator();
            while (normBrickIt.hasNext()) {
                Brick brick = normBrickIt.next();
                if (bullet.getBounds().intersects(brick.getBounds())) {
                    if (brick.takeHit()) playSoundEffect(getBreakBrickSoundUrl());
                    else playSoundEffect(getHitBrickSoundUrl());
                    hitBrick = true;
                    break;
                }
            }

            if (!hitBrick) {
                Iterator<StrongBrick> strongBrickIt = strongBList.iterator();
                while (strongBrickIt.hasNext()) {
                    StrongBrick brick = strongBrickIt.next();
                    if (bullet.getBounds().intersects(brick.getBounds())) {
                        if (brick.takeHit()) playSoundEffect(getBreakBrickSoundUrl());
                        else playSoundEffect(getHitBrickSoundUrl());
                        hitBrick = true;
                        break;
                    }
                }
            }

            if (hitBrick) {
                bulletIterator.remove();
            }
        }

        if (brickList.isEmpty() && strongBList.isEmpty()) {
            myLevel += 1;
            if (myLevel >= 10) {
                System.out.println("You are the champion !");
                winAll = true;
            } else {
                SwingUtilities.invokeLater(() -> {
                    try {
                        restartGame(myLevel, this.myScore);
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

            soundClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    event.getLine().close();
                }
            });

            soundClip.start();
        } catch (Exception e) {
            System.err.println("Không thể phát hiệu ứng âm thanh: " + e.getMessage());
        }
    }

    /**
     * Vẽ vật thể lên màn hình.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (this.soul == 3) {
            this.heartImage = new ImageIcon(thHeartsUrl).getImage();
        } else if (this.soul == 2) {
            this.heartImage = new ImageIcon(twHeartsUrl).getImage();
        } else if (this.soul == 1){
            this.heartImage = new ImageIcon(onHeartUrl).getImage();
        }

        g.drawImage(gameFrameImage, 0, 0,(int) this.getBoardWidth() ,(int) this.getBoardHeight() , null);

        int backX = 30;
        int backY = 25;
        int widthBack = (int) this.getBoardWidth() - 70;
        int heightBack = (int) this.getBoardHeight() - 45;
        int arc = 200;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Shape clip = new RoundRectangle2D.Float(backX, backY, widthBack, heightBack, arc, arc);
        g2.setClip(clip);
        g.drawImage(background, backX, backY,widthBack ,heightBack, null);

        g.drawImage(scoreImage, (int) this.getBoardWidth() / 2 - 100, 25, 200, 60, null);
        g2.setFont(new Font("Star Jedi", Font.BOLD, 20));
        g2.setColor(Color.BLACK);
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++)
                g2.drawString("Score: " + myScore, (int) getBoardWidth()/2 - 43 + i, 62 + j);
        }
        g2.drawString("Score: " + myScore, (int) getBoardWidth()/2 - 43, 62);

        g2.setColor(new Color(255, 215, 0));
        g2.drawString("Score: " + myScore, (int) getBoardWidth()/2 - 45, 60);

        g.drawImage(heartImage, (int) getBoardWidth() - 230, 30, 100, 30, null);

        if (urlSpeakerImage != null) {
            g.drawImage(new ImageIcon(urlSpeakerImage).getImage(), 80,40,30,30,null);
        }

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

        if (this.laserPower != null) {
            this.laserPower.render(g2);
        }

        for (Bullet bullet : bulletList) {
            bullet.render(g2);
        }

        paddle.render(g2);
        ball.render(g2);
        g2.dispose();
    }

    /**
     * Restart game với 1 luồng mới và các chức năng về mặc định.
     */
    public void restartGame(int currentLevel, int currentScore) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (gameRuning != null && gameRuning.isAlive() && Thread.currentThread() != gameRuning) {
            running = false;
            try {
                gameRuning.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.soul = 3;
        this.myScore = currentScore;

        // Điều chỉnh âm thanh về mặc định.
        if (gameMusicPlay != null) {
            gameMusicPlay.stop();
        }
        try {
            gameMusicPlay = new Music(musicUrl);
            gameMusicPlay.toggle();
        } catch (Exception e) {
            e.printStackTrace();
            gameMusicPlay = null;
        }

        if (fastBallPowerUp != null) {
            fastBallPowerUp.setPower(false);
            fastBallPowerUp.turnOffMusic();
        }
        if (paddlePower != null) {
            paddlePower.setPower(false);
            paddlePower.turnOffMusic();
        }

        if (laserPower != null) {
            laserPower.setPower(false);
            laserPower.turnOffMusic();
        }

        this.setBackGround(getDefaultBackGround());
        paddle.resetToDefault();
        ball.setDefaultBallValue();
        fastBallPowerUp = null;
        paddlePower = null;
        laserPower = null;
        bulletList.clear();
        loadLevels(currentLevel);
        gameThread();
    }

    /**
     * Getter & Setter
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

    /**
     * Lấy đối tượng Music để có thể thao tác.
     * @return
     */
    public Music getGameMusic() {
        return this.gameMusicPlay;
    }

    /**
     * Lấy số mạng hiện có.
     * @return
     */
    public int getSoul() {
        return this.soul;
    }

    /**
     * Trừ mạng.
     */
    public void lostSoul() {
        this.soul -= 1;
    }

    /**
     * Lấy level hiện tại của người chơi.
     * @param level
     */
    public int getLevel() {
        return this.myLevel;
    }
    
    /**
     * Set Level cho người chơi.
     * @param level
     */
    public void setLevel(int level) {
        this.myLevel = level;
    }

    /**
     * Set Background mới cho powerUp.
     * @param Url
     */
    public void setBackGround(String Url) {
        background = new ImageIcon(Url).getImage();
    }

    /**
     * Lấy backGround mặc định.
     * @return
     */
    public String getDefaultBackGround() {
        return this.backgroundURL;
    }

    /**
     * Lấy ra URL của paddle va chạm.
     * @return
     */
    public String getHitPaddleSoundUrl() {
        return hitPaddleSoundUrl;
    }

    /**
     * Lấy ra URL của âm thanh gạch va chạm.
     * @return
     */
    public String getHitBrickSoundUrl() {
        return hitBrickSoundUrl;
    }

    /**
     * Lấy ra URL của âm thanh gạch vỡ.
     * @return
     */
    public String getBreakBrickSoundUrl() {
        return breakBrickSoundUrl;
    }

    /**
     * Lấy điểm số.
     * @return
     */
    public int getMyScore() {
        return this.myScore;
    }

    /**
     * Lấy điểm cao.
     * @return
     */
    public int getHighScore() {
        return this.highScore;
    }
}
