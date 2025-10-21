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
    }

    public void setDefaultBallValue() {
        this.x = defaultX;
        this.y = defaultY;
        this.width = defaultRadius;
        this.height = defaultRadius;
        this.dx = defaultSpeed;
        this.dy = defaultSpeed;
        this.currentPowerUp = defaultPowerUp;
        this.isLaunch = false;
    }

    public Ball(float x, float y, float radius, float speedX, float speedY) {
        super(x, y, radius * 2, radius * 2, speedX, speedY); // dùng width, height = đường kính
        // updateVelocity();
    }

    @Override
    public void update() {
        // TODO: Cập nhật trạng thái của bóng
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
                drawX, drawY, new Color(255, 255, 255),
                drawX + radius, drawY + radius, new Color(50, 150, 255)
        );
        g2.setPaint(gradient);
        g2.fillOval(drawX, drawY, radius, radius);

        // Viền ngoài sáng
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawOval(drawX, drawY, radius, radius);
    }

//    @Override
//	public void render() {
//		// TODO: Vẽ bóng lên màn hình
//		// Ví dụ: System.out.println("Vẽ bóng tại (" + x + ", " + y + ")");
//	}

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
            ((Brick) other).takeHit();

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
}