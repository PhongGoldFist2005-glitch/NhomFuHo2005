package game.entities;

import game.core.GameManager;
import game.core.KeyPress;

import java.awt.*;
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
        super((gameManager.getBoardWidth() - defaultRadius) / 2, gameManager.getBoardHeight() - 30, defaultRadius, defaultRadius, defaultSpeed, defaultSpeed);
        this.keyH = keyH;
        this.paddle = paddle;
        this.gameManager = gameManager;
        this.defaultX = (this.gameManager.getBoardWidth() - defaultRadius) / 2;
        this.defaultY = this.gameManager.getBoardHeight() - 30;
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
        int radius = (int) defaultRadius;
        int drawX = (int) x;
        int drawY = (int) y;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Đổ bóng mờ phía dưới
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillOval(drawX + 4, drawY + 4, radius, radius);

        // Bóng chính với gradient
        GradientPaint gradient = new GradientPaint(
                drawX, drawY, color_1,
                drawX + radius, drawY + radius, color_2
        );
        g2.setPaint(gradient);
        g2.fillOval(drawX, drawY, radius, radius);

        // Viền ngoài sáng
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawOval(drawX, drawY, radius, radius);
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
            if (this.x <= 0 || this.x + this.width >= gameManager.getBoardWidth()) {// giu cho bong khong di ra ngoai pham vi ben phai va ben trai cua so
                if(this.x <= 0){
                    this.x = 0;
                }
                else {
                    this.x = gameManager.getBoardWidth() - this.width;
                }
                dx = -dx;
            }
            if (this.y <= 0) {// giu cho bong khong di ra ngoai pham vi ben tren cua so
                this.y = 0;
                dy = -dy;
            }
            if (this.y >= gameManager.getBoardHeight()) {
                // Khi mất mạng, reset lại quả bóng giưax paddle
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
        //Bay len sang trai hoac sang phai ngau nhien
        dy = -defaultSpeed;
        Random random = new Random();
        if(random.nextBoolean()){
            dx = defaultSpeed;
        }
        else {
            dx = -defaultSpeed;
        }

    }

    // Xử lý quả bóng khi va chạm
    public void bounceOff(GameObject other) {
        // pass
        Rectangle ballBounds = this.getBounds();
        Rectangle otherBounds = other.getBounds();
        // Hinh chu nhat giao nhau giua 2 hinh hinh tren
        Rectangle intersection = ballBounds.intersection(otherBounds);
        if (intersection.width > intersection.height) {// va cham tren hoac duoi
            this.dy = -dy;
            if (this.y < other.y) {// Truc y huong xuong, va cham tren
                // chinh lai vi tri qua bong de tranh bi ket vao vat
                this.y = other.y - this.height;
            } else {// va cham duoi
                this.y = other.y + other.height;
            }
        } else {// va cham trai hoac phai
            this.dx = -dx;
            if (this.x < other.x) {// va cham ben trai vat the
                this.x = other.x - this.width;
            } else {// va cham ben phai vat the
                this.x = other.x + other.width;
            }
        }
        if (other instanceof Brick) {
            // Gọi takeHit() và lưu kết quả (true nếu gạch bị phá hủy)
            boolean isDestroyed = ((Brick) other).takeHit();

            if (isDestroyed) {
                // Phát âm thanh gạch vỡ
                gameManager.playSoundEffect(gameManager.getBreakBrickSoundUrl());
            } else {
                // Phát âm thanh va chạm gạch (ví dụ: đập vào StrongBrick)
                gameManager.playSoundEffect(gameManager.getHitBrickSoundUrl());
            }

        } else if (other instanceof Paddle) {
            // Phát âm thanh va chạm với thanh đỡ
            gameManager.playSoundEffect(gameManager.getHitPaddleSoundUrl());
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