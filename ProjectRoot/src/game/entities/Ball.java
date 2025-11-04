package game.entities;

import game.core.GameManager;
import game.core.KeyPress;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 * Xây dựng lớp bóng.
 */
public class Ball extends MovableObject {
    protected int currentPowerUp;

    GameManager gameManager;
    KeyPress keyH;

    // Bóng đã bay ra hay chưa
    protected boolean isLaunch;
    // Trạng thái default để sau này con reset.
    protected float defaultX;
    protected float defaultY;
    protected static final float defaultRadius = 20;
    protected static final float defaultSpeed = 6;
    protected static final int defaultPowerUp = 1;
    protected Paddle paddle;
    // Màu mặc định của bóng.
    protected static final Color defaultColor_1 = new Color(255, 255, 255);
    protected static final Color defaultColor_2 = new Color(50, 150, 255);
    // Màu của bóng
    protected Color color_1;
    protected Color color_2;

    /**
     * Ball constructor.
     * @param keyH
     * @param paddle
     * @param gameManager
     */
    public Ball(KeyPress keyH, Paddle paddle, GameManager gameManager) {
        // - 20 la default radius
        // Điều chỉnh vị trí của bóng tại vị trí phù hợp hiển thị
        super((gameManager.getBoardWidth() - defaultRadius) / 2, gameManager.getBoardHeight() - 80,
                defaultRadius, defaultRadius, defaultSpeed, defaultSpeed);
        this.keyH = keyH;
        this.paddle = paddle;
        this.gameManager = gameManager;
        // Set các giá trị lưu mặc định.
        this.defaultX = (this.gameManager.getBoardWidth() - defaultRadius) / 2;
        this.defaultY = this.gameManager.getBoardHeight() - 70;
        this.currentPowerUp = 1;
        this.isLaunch = false;
        this.color_1 = defaultColor_1;
        this.color_2 = defaultColor_2;
    }

    /**
     * Trở về trạng thái mặc định của bóng.
     */
    public void setDefaultBallValue() {
        this.setX(defaultX);
        this.setY(defaultY);
        this.width = defaultRadius;
        this.height = defaultRadius;
        this.dx = defaultSpeed;
        this.dy = defaultSpeed;
        this.currentPowerUp = defaultPowerUp;
        this.color_1 = defaultColor_1;
        this.color_2 = defaultColor_2;
        this.isLaunch = false;
    }

    /**
     * Cập nhật trạng thái của bóng.
     */
    @Override
    public void update() {
        move();
    }

    /**
     * Hiện thị đồ họa của quả bóng.
     */
    @Override
    public void render(Graphics2D g2) {
        int radius = Math.round(defaultRadius);
        int drawX = Math.round(this.getX());
        int drawY = Math.round(this.getY());

        // Nâng cao chất lượng hình ảnh.
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        // Dùng Ellipse để vẽ với tọa độ float.
        Ellipse2D.Float shadow = new Ellipse2D.Float(drawX + 4, drawY + 4, radius, radius);
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fill(shadow);

        // Bóng chính.
        Ellipse2D.Float ball = new Ellipse2D.Float(drawX, drawY, radius, radius);
        GradientPaint gradient = new GradientPaint(
                drawX, drawY, color_1,
                drawX + radius, drawY + radius, color_2
        );
        g2.setPaint(gradient);
        g2.fill(ball);

        // Viền.
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(ball);
    }

    /**
     * Cập nhật liên tục vị trí của quả bóng.
     */
    @Override
    public void move() {
        // Cập nhật vị trí của bóng
        if (!isLaunch) {
            if (this.keyH.launch) {
                startLaunch();
            } else {
                // Vị trí bóng chưa bắn dính vô giữa paddle.
                this.setX(paddle.getX() + (paddle.width) / 2 - (this.width) / 2);
                this.setY(paddle.getY() - this.height);
            }
        } else {
            
            // Giữ cho bóng không nằm ngoài màn hình.
            if (this.getX() <= 30 || this.getX() + this.width >= gameManager.getBoardWidth() - 40) {
                if(this.getX() <= 30){
                    this.setX(30);
                }
                else {
                    this.setX(gameManager.getBoardWidth() - this.width - 40);
                }
                dx = -dx;
            }

            // Giữ cho bóng không đi trên cửa sổ game.
            if (this.getY() <= 25) {
                this.setY(25);
                dy = -dy;
            }

            // Khi mất mạng, reset lại quả bóng giưa paddle.
            if (this.getY() >= gameManager.getBoardHeight()) {
                this.setX(paddle.getX() + (paddle.width) / 2 - (this.width) / 2);
                this.setY(paddle.getY() - this.height);
                isLaunch = false;
                gameManager.lostSoul();
                return;
            }
            this.setY(this.getY() + dy);
            this.setX(this.getX() + dx);
        }

    }

    /**
     * Tăng tính bất ngờ bóng có thể bắn 3 hướng
     * trái phải thẳng.
     */
    public void startLaunch() {
        isLaunch = true;
        // Bay lên thẳng, sang trái hoặc phải ngẫu nhiên
        dy = -defaultSpeed;
        Random random = new Random();
        if (random.nextInt(1, 4) == 1) {
            dx = defaultSpeed;
        } else if (random.nextInt(1, 4) == 2) {
            dx = -defaultSpeed;
        } else if (random.nextInt(1, 4) == 3) {
            dx = 0;
        }

    }


    /**
     * Kiểm tra xem bóng đã được phóng hay chưa.
     * @return true nếu bóng đang di chuyển tự do, false nếu còn dính vào paddle.
     */
    public boolean isLaunched() {
        return this.isLaunch;
    }
    
    // Xử lý quả bóng khi va chạm.
    public void bounceOff(GameObject other) {
        Rectangle ballBounds = this.getBounds();
        Rectangle otherBounds = other.getBounds();

        // Hình chữ nhật giao nhau giữa 2 hình trên.
        Rectangle intersection = ballBounds.intersection(otherBounds);

        // Xử lý va chạm của bóng với từng thực thể.
        if (other instanceof Paddle) {
            float paddleCenter = other.getX() + other.width / 2f;
            float ballCenter = this.getX() + this.width / 2f;

            // Xác định vị trí va chạm tương đối, giới hạn trong [-1;1].
            // Nếu nằm ngoài [-1;1], không xảy ra va chạm nên không cần xử lý va chạm.
            float hitPosition = (ballCenter - paddleCenter) / (other.width / 2f);
            hitPosition = Math.max(-1f, Math.min(1f, hitPosition));

            // Tính góc bật dựa trên vị trí va chạm.
            // Ứng với mỗi vị trí va chạm tương đối, bóng sẽ bật lại một góc cố định.
            double radians = Math.toRadians((hitPosition * 60));

            // Cập nhật vận tốc.
            this.dx = (float) (defaultSpeed * Math.sin(radians));
            this.dy = (float) (-defaultSpeed * Math.cos(radians));

            // Đặt bóng trên paddle để tránh bị kẹt.
            this.setY(other.getY() - this.height);
            // Phát âm thanh va chạm với thanh đỡ.
            gameManager.playSoundEffect(gameManager.getHitPaddleSoundUrl());
            return;
        }

        // Xử lý bóng va chạm với gạch.
        if (intersection.width > intersection.height) {
            // Va chạm trên hoặc dưới.
            this.dy = -dy;
            // Trục y hướng xuống, va chạm trên.
            if (this.getY() < other.getY()) {
                // Chỉnh lại vị trí quả bóng để tránh kẹt vào vật thể
                this.setY(other.getY() - this.height);
            } else {
                // va chạm dưới.
                this.setY(other.getY() + other.height);
            }

        } else {
            // Va chạm trái hoặc phải
            this.dx = -dx;
            if (this.getX() < other.getX()) {
                // Va chạm bên trái vật thể.
                this.setX(other.getX() - this.width);
            } else {
                // Va chạm bên phải vật thể.
                this.setX(other.getX() + other.width);
            }
        }

        // Tạo âm thanh va chạm và phá hủy khi va chạm với gạch.
        if (other instanceof Brick) {
            boolean isDestroyed = ((Brick) other).takeHit();
            if (isDestroyed)
                gameManager.playSoundEffect(gameManager.getBreakBrickSoundUrl());
            else {
                gameManager.playSoundEffect(gameManager.getHitBrickSoundUrl());
            }
        }
    }

    // Xử lý khi bóng va chạm với 1 object khác.
    public void checkCollision(GameObject other) {
        Rectangle ballBounds = this.getBounds();
        Rectangle otherBounds = other.getBounds();
        // Kiem tra 2 hình có cắt nhau không.
        if (ballBounds.intersects(otherBounds)) {
            bounceOff(other);
        }
    }

    /**
     * Getter và Setter.
     * @return
     */
    public float getDx() {
        return this.dx;
    }

    public float getDy() {
        return this.dy;
    }

    public void setDx(float newDx) {
        this.dx = newDx;
    }

    public void setDy(float newDy) {
        this.dy = newDy;
    }

    public float getDefaultSpeed() {
        float speedUp = defaultSpeed;
        return speedUp;
    }

    public void setColor(Color color1, Color color2) {
        this.color_1 = color1;
        this.color_2 = color2;
    }

    public void setDefalaultColor() {
        this.color_1 = defaultColor_1;
        this.color_2 = defaultColor_2;
    }
}