package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import game.core.GameManager;

public class LaserPaddlePowerUp extends PowerUp {

    protected static final int type = 5; // Gán ID mới cho loại power-up này
    protected static final float width = 20;
    protected static final float height = 20;
    GameManager gameManager;
    public String newBackGroundURL = "C:\\Users\\Admin\\IdeaProjects\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\guard.jpg";

    public LaserPaddlePowerUp(float x, float y, Paddle paddle, GameManager gameManager, Ball ball) {
        super(x, y, width, height, type, paddle, ball);
        this.gameManager = gameManager;
    }

    /**
     * Áp dụng hoặc gỡ bỏ hiệu ứng cho paddle.
     */
    public void upgradePaddle() {
        if (havePower) {
            if (!musicPowerUp.isPlaying()) {
                musicPowerUp.play();
            }
            // Khi có power, cho phép paddle bắn
            this.paddle.setCanShoot(true);
            // Bạn có thể thay đổi màu paddle ở đây nếu muốn
            ball.setColor(Color.decode("#0F172A"), Color.decode("#22D3EE"));
            gameManager.setBackGround(newBackGroundURL);
        } else {
            if (musicPowerUp.isPlaying()) {
                musicPowerUp.stop();
            }
            // Khi hết power, không cho phép paddle bắn nữa
            this.paddle.setCanShoot(false);
            ball.setDefalaultColor();
            gameManager.setBackGround(gameManager.getDefaultBackGround());
        }
    }

    @Override
    public void update() {
        Falling();
        applyEffect(this.paddle);
        removeEffect();
        upgradePaddle(); // Liên tục cập nhật trạng thái của paddle
    }

    @Override
    public void render(Graphics2D g2) {
        // Vẽ PowerUp này là một hình bầu dục màu đỏ
        g2.setColor(Color.RED);
        g2.fillOval((int) x, (int) y, (int) width, (int) height);
    }
}
