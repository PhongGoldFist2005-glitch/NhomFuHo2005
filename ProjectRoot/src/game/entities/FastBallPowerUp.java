package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import game.core.GameManager;

public class FastBallPowerUp extends PowerUp {
    protected static final int type = 4;
    protected static final float width = 20;
    protected static final float height = 20;
    protected float newSpeed;
    GameManager gameManager;
    public String newBackGroundURL = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\images\\background_water.png";

    public FastBallPowerUp(float x, float y, Paddle paddle, GameManager gameManager, Ball ball){
        super(x, y, width, height, type, paddle, ball);
        this.gameManager = gameManager;
        this.newSpeed = ball.getDx() + 0;
    }

    public void upgradeBall() {
        if (havePower) {
            float speed = ball.getDefaultSpeed() * 2f; // ví dụ tăng 50%
            ball.setDx(Math.signum(ball.getDx()) * speed);
            ball.setDy(Math.signum(ball.getDy()) * speed);
            ball.setColor(Color.CYAN, Color.BLUE);
            gameManager.setBackGround(newBackGroundURL);
        } else {
            ball.setDx(Math.signum(ball.getDx()) * ball.getDefaultSpeed());
            ball.setDy(Math.signum(ball.getDy()) * ball.getDefaultSpeed());
            ball.setDefalaultColor();
            gameManager.setBackGround(gameManager.getDefaultBackGround());
        }
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
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
        g2.setColor(Color.BLUE);
        g2.fillOval((int) x, (int) y, (int) width,(int) height);
    };

}