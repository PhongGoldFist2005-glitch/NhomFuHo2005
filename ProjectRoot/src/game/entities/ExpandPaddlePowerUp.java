package game.entities;

import java.awt.*;

import game.core.GameManager;

import javax.swing.*;

/**
 * Lớp power up tăng kích thước ván.
 */
public class ExpandPaddlePowerUp extends PowerUp {
    // Nó ẩn trong gạch loại 3, gạch loại 3 là gạch loại 2 nhưng có thêm vật thể power up.
    protected static final int type = 3;
    // Xây dựng vật thể nâng cấp
    protected static final float width = 20;
    protected static final float height = 20;
    // Biến cập nhật trạng thái.
    protected float upgradeWidth;
    public String newBackGroundURL = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\hot.jpg";
    GameManager gameManager;
    
    /**
     * Constructor tăng kích thước ván.
     * @param x
     * @param y
     * @param paddle
     * @param gameManager
     * @param ball
     */
    public ExpandPaddlePowerUp(float x, float y, Paddle paddle, GameManager gameManager, Ball ball) {
        super(x, y, type, paddle,ball);
        this.upgradeWidth = paddle.getDefaultWidth() + 100;
        this.gameManager = gameManager;
        
        // Hiện thị hình ảnh power up.
        try {
            String imagePath = "/assets/images/expand_powerup.png";
            this.powerUpImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh ExpandPaddlePowerUp: " + e.getMessage());
        }
    }

    /**
     * Method tăng kích thước cho ván.
     */
    public void upgradePaddle() {
        // Nếu còn sức mạnh.
        // Chạy nhạc và thay đổi kích thước ván và màu bóng
        // Background.
        if (havePower == true) {
            if (!musicPowerUp.isPlaying()) {
                musicPowerUp.play();
            }

            this.paddle.setWidth(upgradeWidth);
            gameManager.setBackGround(newBackGroundURL);
            ball.setColor(Color.orange, Color.RED);
        }
        else {
            if (musicPowerUp.isPlaying()) {
                musicPowerUp.stop();
            }
            this.paddle.setWidth(this.paddle.getDefaultWidth());
            gameManager.setBackGround(gameManager.getDefaultBackGround());
            ball.setDefalaultColor();
        }
    }

    @Override
    public void update() {
        Falling();
        applyEffect(this.paddle);
        removeEffect();
        upgradePaddle();
    };

    @Override
    public void render(Graphics2D g2) {
        if (powerUpImage != null) {
            g2.drawImage(powerUpImage, (int) getX(), (int) getY(), (int) width, (int) height, null);
        } else {
            // Vẽ dự phòng nếu không có ảnh
            g2.setColor(Color.lightGray);
            g2.fillOval((int) getX(), (int) getY(), (int) width, (int) height);
        }
    };
}