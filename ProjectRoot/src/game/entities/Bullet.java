package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

// Lớp cho viên đạn bắn ra từ Paddle
public class Bullet extends MovableObject {

    // Kích thước và tốc độ mặc định của đạn
    private static final float defaultWidth = 8;
    private static final float defaultHeight = 15;
    private static final float defaultSpeed = -8; // Âm vì nó di chuyển lên trên

    public Bullet(float x, float y) {
        // dx = 0 (không di chuyển ngang), dy = defaultSpeed (di chuyển lên)
        super(x, y, defaultWidth, defaultHeight, 0, defaultSpeed);
    }

    /**
     * Di chuyển viên đạn lên trên.
     */
    @Override
    public void move() {
        this.y += this.dy; // dy là số âm
    }

    /**
     * Cập nhật trạng thái đạn (chỉ là di chuyển).
     */
    @Override
    public void update() {
        move();
    }

    /**
     * Vẽ viên đạn (một hình chữ nhật màu vàng).
     */
    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillRect((int) this.x, (int) this.y, (int) this.width, (int) this.height);
    }

    /**
     * Kiểm tra xem đạn đã bay ra khỏi màn hình chưa (ở cạnh trên).
     * @return true nếu đạn ra khỏi màn hình, ngược lại false.
     */
    public boolean isOffScreen() {
        // Khi y + chiều cao < 0 (hoàn toàn ra khỏi cạnh trên)
        return (this.y + this.height < 0);
    }
}