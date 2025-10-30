package game.entities;

import java.awt.*;

import game.core.GameManager;


public class ExpandPaddlePowerUp extends PowerUp {
    // Nó ẩn trong gạch loại 3, gạch loại 3 là gạch loại 2 nhưng có thêm vật thể power up.
    protected static final int type = 3;
    // Xây dựng vật thể nâng cấp
    protected static final float width = 20;
    protected static final float height = 20;
    protected float upgradeWidth;
    public String newBackGroundURL = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\hot.jpg";
    GameManager gameManager;
    

    public ExpandPaddlePowerUp(float x, float y, Paddle paddle, GameManager gameManager, Ball ball) {
        super(x, y, width, height, type, paddle,ball);
        this.upgradeWidth = paddle.getDefaultWidth() + 100;
        this.gameManager = gameManager;
    }

    public void upgradePaddle() {
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
        g2.setColor(Color.lightGray);
        g2.fillOval((int) x, (int) y, (int) width,(int) height);
    };
}