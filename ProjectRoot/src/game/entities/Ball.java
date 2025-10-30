package game.entities;

import game.core.GameManager;
import game.core.KeyPress;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

// Xây dựng lớp ball.
public class Ball extends MovableObject {
    protected int currentPowerUp;

    GameManager gameManager;
    KeyPress keyH;

    protected boolean isLaunch;
    protected float defaultX;
    protected float defaultY;
    protected static final float defaultRadius = 20;
    protected static final float defaultSpeed = 4;
    protected static final int defaultPowerUp = 1;
    protected Paddle paddle;
    protected static final Color defaultColor_1 = new Color(255, 255, 255);
    protected static final Color defaultColor_2 = new Color(50, 150, 255);
    protected Color color_1;
    protected Color color_2;

    public Ball(KeyPress keyH, Paddle paddle, GameManager gameManager) {
        // - 20 la default radius
        super((gameManager.getBoardWidth() - defaultRadius) / 2, gameManager.getBoardHeight() - 80, defaultRadius, defaultRadius, defaultSpeed, defaultSpeed);
        this.keyH = keyH;
        this.paddle = paddle;
        this.gameManager = gameManager;
        this.defaultX = (this.gameManager.getBoardWidth() - defaultRadius) / 2;
        this.defaultY = this.gameManager.getBoardHeight() - 70;
        this.currentPowerUp = 1;
        this.isLaunch = false;
        this.color_1 = defaultColor_1;
        this.color_2 = defaultColor_2;
    }

    public void setDefaultBallValue() {
        this.x = defaultX;
        this.y = defaultY;
        this.width = defaultRadius;
        this.height = defaultRadius;
        this.dx = defaultSpeed;
        this.dy = defaultSpeed;
        this.currentPowerUp = defaultPowerUp;
        this.color_1 = defaultColor_1;
        this.color_2 = defaultColor_2;
        this.isLaunch = false;
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(Graphics2D g2) {
        int radius = Math.round(defaultRadius);
        int drawX = Math.round(x);
        int drawY = Math.round(y);

        // nâng cao chất lượng hình ảnh
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        // dùng Ellipse để vẽ với tọa độ float
        Ellipse2D.Float shadow = new Ellipse2D.Float(drawX + 4, drawY + 4, radius, radius);
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fill(shadow);

        // Bóng chính
        Ellipse2D.Float ball = new Ellipse2D.Float(drawX, drawY, radius, radius);
        GradientPaint gradient = new GradientPaint(
                drawX, drawY, color_1,
                drawX + radius, drawY + radius, color_2
        );
        g2.setPaint(gradient);
        g2.fill(ball);

        // Viền
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(ball);
    }

    @Override
    public void move() {
        // Cập nhật vị trí của bóng
        if (!isLaunch) {
            if (this.keyH.launch) {
                startLaunch();
            } else {
                // bong dinh vo paddle
                this.x = paddle.getX() + (paddle.width) / 2 - (this.width) / 2;
                this.y = paddle.getY() - this.height;
            }
        } else {
            // Note
            // giu cho bong khong di ra ngoai pham vi ben phai va ben trai cua so
            if (this.x <= 30 || this.x + this.width >= gameManager.getBoardWidth() - 40) {
                if(this.x <= 30){
                    this.x = 30;
                }
                else {
                    this.x = gameManager.getBoardWidth() - this.width - 40;
                }
                dx = -dx;
            } // Note
            if (this.y <= 25) {// giu cho bong khong di ra ngoai pham vi ben tren cua so
                this.y = 25;
                dy = -dy;
            }
            if (this.y >= gameManager.getBoardHeight()) {
                // Khi mất mạng, reset lại quả bóng giưa paddle
                this.x = paddle.getX() + (paddle.width) / 2 - (this.width) / 2;
                this.y = paddle.getY() - this.height;
                isLaunch = false;
                gameManager.lostSoul();
                return;
            }
            this.y += dy;
            this.x += dx;
        }

    }

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

    //TP them tu day
    /**
     * Kiểm tra xem bóng đã được phóng hay chưa.
     * @return true nếu bóng đang di chuyển tự do, false nếu còn dính vào paddle.
     */
    public boolean isLaunched() {
        return this.isLaunch;
    }
    
    // Xử lý quả bóng khi va chạm
    public void bounceOff(GameObject other) {
        // pass
        Rectangle ballBounds = this.getBounds();
        Rectangle otherBounds = other.getBounds();

        // Hình chữ nhật giao nhau giữa 2 hình trên
        Rectangle intersection = ballBounds.intersection(otherBounds);

        // Xử lý va chạm của bóng với từng thực thể
        if (other instanceof Paddle) {
            float paddleCenter = other.x + other.width / 2f;
            float ballCenter = this.x + this.width / 2f;

            // Xác định vị trí va chạm tương đối, giới hạn trong [-1;1]
            // Nếu nằm ngoài [-1;1], không xảy ra va chạm nên không cần xử lý va chạm
            float hitPosition = (ballCenter - paddleCenter) / (other.width / 2f);
            hitPosition = Math.max(-1f, Math.min(1f, hitPosition));

            // Tính góc bật dựa trên vị trí va chạm
            // Ứng với mỗi vị trí va chạm tương đối, bóng sẽ bật lại một góc cố định
            double radians = Math.toRadians((hitPosition * 60));

            // Cập nhật vận tốc
            this.dx = (float) (defaultSpeed * Math.sin(radians));
            this.dy = (float) (-defaultSpeed * Math.cos(radians));

            // Đặt bóng trên paddle để tránh bị kẹt
            this.y = other.y - this.height;
            // Phát âm thanh va chạm với thanh đỡ
            gameManager.playSoundEffect(gameManager.getHitPaddleSoundUrl());
            return;
        }

        // Xử lý bóng va chamj với gạch
        if (intersection.width > intersection.height) {
            // Va chạm trên hoặc dưới

            this.dy = -dy;
            if (this.y < other.y) {// Trục y hướng xuống, va chạm trên
                // Chỉnh lại vị trí quả bóng để tránh kẹt vào vật thể
                this.y = other.y - this.height;
            } else {// va chạm dưới
                this.y = other.y + other.height;
            }
        } else {
            // Va chạm trái hoặc phải

            this.dx = -dx;
            if (this.x < other.x) {
                // Va chạm bên trái vật thể
                this.x = other.x - this.width;
            } else {
                // Va chạm bên phải vật thể
                this.x = other.x + other.width;
            }
        }

        // Tạo âm thanh va chạm và phá hủy khi va chạm với gạch
        if (other instanceof Brick) {
            boolean isDestroyed = ((Brick) other).takeHit();
            if (isDestroyed)
                gameManager.playSoundEffect(gameManager.getBreakBrickSoundUrl());
            else
                gameManager.playSoundEffect(gameManager.getHitBrickSoundUrl());
        }
    }

    // Xử lý khi bóng va chạm với 1 object khác
    public void checkCollision(GameObject other) {
        // pass
        Rectangle ballBounds = this.getBounds();
        Rectangle otherBounds = other.getBounds();
        if (ballBounds.intersects(otherBounds)) {// Kiem tra 2 hinh co cat nhau khong
            bounceOff(other);
        }
    }

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