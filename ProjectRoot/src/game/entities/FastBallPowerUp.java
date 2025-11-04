package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import game.core.GameManager;

import javax.swing.*;

/**
 * Class power up cho bóng.
 */
public class FastBallPowerUp extends PowerUp {
    protected static final int type = 4;
    GameManager gameManager;
    public String newBackGroundURL = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\cool.jpg";

    /**
     * Constructor cho bóng nhanh.
     * @param x
     * @param y
     * @param paddle
     * @param gameManager
     * @param ball
     */
    public FastBallPowerUp(float x, float y, Paddle paddle, GameManager gameManager, Ball ball){
        super(x, y, type, paddle, ball);
        this.gameManager = gameManager;

        // Thêm hình ảnh hiện thị cho power up.
        try {
            String imagePath = "/assets/images/fastball_powerup.png";
            this.powerUpImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh FastBallPowerUp: " + e.getMessage());
        }
    }

    /**
     * Tăng sức mạnh làm bóng nhanh hơn.
     */
    public void upgradeBall() {
        if (havePower) {
            if (!musicPowerUp.isPlaying()) {
                musicPowerUp.play();
            }

            // Tăng tốc độ bóng thêm 25%.
            float speed = ball.getDefaultSpeed() * 1.25f;
            ball.setDx(Math.signum(ball.getDx()) * speed);
            ball.setDy(Math.signum(ball.getDy()) * speed);
            ball.setColor(Color.CYAN, Color.BLUE);
            gameManager.setBackGround(newBackGroundURL);
            
        } else {
            
            // Hết power up tắt nhạc và reset về trạng thái ban đầu.
            if (musicPowerUp.isPlaying()) {
                musicPowerUp.stop();
            }
            
            ball.setDx(Math.signum(ball.getDx()) * ball.getDefaultSpeed());
            ball.setDy(Math.signum(ball.getDy()) * ball.getDefaultSpeed());
            ball.setDefalaultColor();
            gameManager.setBackGround(gameManager.getDefaultBackGround());
        }
    }

    @Override
    public void update() {
        Falling();
        applyEffect(this.paddle);
        removeEffect();
        upgradeBall();
    };

    @Override
    public void render(Graphics2D g2) {
        if (powerUpImage != null) {
            g2.drawImage(powerUpImage, (int) getX(), (int) getY(), (int) width, (int) height, null);
        } else {
            g2.setColor(Color.BLUE);
            g2.fillOval((int) getX(), (int) getY(), (int) width, (int) height);
        }
    };
}