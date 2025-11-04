package game.entities;

import java.awt.*;

import game.core.Music;

/**
 * Vât phẩm nâng cấp sức mạnh rơi ra khi phá gạch cho quả bóng hoặc ván.
 */
public class PowerUp extends GameObject {
    // Power up có loại
    protected int type;
    // Thời gian tồn tại
    protected float duration = 10;
    // Tốc độ rơi.
    protected float speed = 2;
    // Trạng thái có sức mạnh hay không.
    protected boolean havePower = false;
    // Kích thước của Power Up.
    protected static final float width = 20;
    protected static final float height = 20;
    // Thời điểm tính để bắt đầu.
    protected long start;
    // Các vật để được upgrade.
    protected Ball ball;
    protected Paddle paddle;

    private static final String musicPower = "C:\\Users\\admin\\Documents\\GitHub\\NhomFuHo2005\\ProjectRoot\\src\\assets\\sounds\\powerup.wav";
    Music musicPowerUp;
    protected Image powerUpImage;

    /**
     * Constructor cho Power Up.
     * @param x
     * @param y
     * @param type
     * @param paddle
     * @param ball
     */
    public PowerUp(float x, float y, int type, Paddle paddle, Ball ball) {
        super(x, y, width, height);
            try {
                musicPowerUp = new Music(musicPower);
            } catch (Exception e) {
                e.printStackTrace();
            }
        this.type = type;
        this.paddle = paddle;
        this.ball = ball;
    }

    /**
     * Khiến vật thể bị rơi xuống dần.
     */
    public void Falling() {
        this.setY(this.getY() + this.speed);
    }

    /**
     * Xây dựng cách tiếp nhận power up.
     */
    public void applyEffect(Paddle paddle) {
        // Nếu vào được ván thì nhận sức mạnh và tính lùi đến lúc hết sức mạng.
        boolean collideY = (getY() + height >= paddle.getY())
            && (getY() <= paddle.getY() + paddle.getHeight());
        boolean collideX = (getX() + width >= paddle.getX())
            && (getX() <= paddle.getX() + paddle.getWidth());

        if (!havePower && collideX && collideY) {
            setPower(true);
            this.start = System.currentTimeMillis();
            this.setY(this.getY() + 100);
        }
    }

    /**
     * Xóa hiệu ứng sức mạnh khi vượt quá thời gian.
     */
    public void removeEffect() {
        if (System.currentTimeMillis() - start >= this.duration * 1000 
            && havePower == true) {
            setPower(false);
        }
    }

    /**
     * Getter & Setter.
     */
    public void setPower(boolean updatePower) {
        this.havePower = updatePower;
    }
    
    public boolean getPower() {
        return this.havePower;
    }

    public void turnOffMusic() {
        musicPowerUp.stop();
    }
    
    @Override
    public void update() {
        // Mỗi power up lại có cách update khác nhau.
        Falling();
        applyEffect(this.paddle);
        removeEffect();
    };

    @Override
    public void render(Graphics2D g2) {
        // Mỗi power up lại có hiệu ứng khác nhau.
        g2.setColor(Color.lightGray);
        g2.fillOval((int) getX(), (int) getY(), (int) width, (int) height);
    };
}