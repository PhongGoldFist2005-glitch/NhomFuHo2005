package game.entities;

import java.awt.*;

// Vât phẩm nâng cấp sức mạnh rơi ra khi phá gạch cho quả bóng hoặc ván.
public class PowerUp extends GameObject {
    protected int type;
    protected float duration = 10;
    protected float speed = 2;
    protected boolean havePower = false;
    protected long start;
    Ball ball;
    Paddle paddle;

    // Xây dựng vật thể nâng cấp
    public PowerUp(float x, float y, float width, float height, int type, Paddle paddle, Ball ball) {
        super(x, y, width, height);
        this.type = type;
        this.paddle = paddle;
        this.ball = ball;
    }


    // Khiến vật thể bị rơi xuống dần
    public void Falling() {
        y += this.speed;
    }

    // Xây dựng cách tiếp nhận power up
    public void applyEffect(Paddle paddle) {
        boolean collideY = (y + height >= paddle.getY()) && (y <= paddle.getY() + paddle.getHeight());
        boolean collideX = (x + width >= paddle.getX()) && (x <= paddle.getX() + paddle.getWidth());

        if (!havePower && collideX && collideY) {
            setPower(true);
            this.start = System.currentTimeMillis();
            this.y += 100;
        }
    }

    public void removeEffect() {
        if (System.currentTimeMillis() - start >= this.duration * 1000 && havePower == true) {
            setPower(false);
        }
    }

    public void setPower(boolean updatePower) {
        this.havePower = updatePower;
    }
    
    public boolean getPower() {
        return this.havePower;
    }

    @Override
    public void update() {
    };

    @Override
    public void render(Graphics2D g2) {
        // Pass
    };

}