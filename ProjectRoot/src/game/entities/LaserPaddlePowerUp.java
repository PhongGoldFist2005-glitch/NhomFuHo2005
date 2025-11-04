package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import game.core.GameManager;

import javax.swing.*;

/**
 * Xây dựng lớp powerUp bắn laze.
 */
public class LaserPaddlePowerUp extends PowerUp {
    // Loại 5.
    protected static final int type = 5;

    GameManager gameManager;
    public String newBackGroundURL = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\guard.jpg";

    /**
     * Constructor cho lớp bắn laze.
     * @param x
     * @param y
     * @param paddle
     * @param gameManager
     * @param ball
     */
    public LaserPaddlePowerUp(float x, float y, Paddle paddle, GameManager gameManager, Ball ball) {
        super(x, y, type, paddle, ball);
        this.gameManager = gameManager;
        
        // Thêm hình ảnh cho power up bắn laze.
        try {
            String imagePath = "/assets/images/laser_powerup.png";
            this.powerUpImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh LaserPaddlePowerUp: " + e.getMessage());
        }
    }

    /**
     * Áp dụng hoặc gỡ bỏ hiệu ứng cho paddle.
     */
    public void upgradePaddle() {
        if (havePower) {
            if (!musicPowerUp.isPlaying()) {
                musicPowerUp.play();
            }
            
            // Khi có power, cho phép paddle bắn.
            this.paddle.setCanShoot(true);
            ball.setColor(Color.decode("#0F172A"), Color.decode("#22D3EE"));
            gameManager.setBackGround(newBackGroundURL);
        } else {
            
            if (musicPowerUp.isPlaying()) {
                musicPowerUp.stop();
            }
            
            // Khi hết power, không cho phép paddle bắn nữa.
            this.paddle.setCanShoot(false);
            ball.setDefalaultColor();
            gameManager.setBackGround(gameManager.getDefaultBackGround());
        }
    }

    /**
     * // Liên tục cập nhật trạng thái của paddle.
     */
    @Override
    public void update() {
        Falling();
        applyEffect(this.paddle);
        removeEffect();
        upgradePaddle();
    }

    /**
     * Hiện thị power up.
     */
    @Override
    public void render(Graphics2D g2) {
        if (powerUpImage != null) {
            g2.drawImage(powerUpImage, (int) getX(), (int) getY(), (int) width, (int) height, null);
        } else {
            g2.setColor(Color.RED);
            g2.fillOval((int) getX(), (int) getY(), (int) width, (int) height);
        }
    }
}
