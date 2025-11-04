package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Lớp cho lớp đạn được bắn ra của Powerup.
 */
public class Bullet extends MovableObject {

    // Kích thước và tốc độ mặc định của đạn.
    private static final float defaultWidth = 8;
    private static final float defaultHeight = 15;
    // Âm vì nó di chuyển lên trên.
    private static final float defaultSpeed = -8;

    /**
     * Constructor của bullet.
     * @param x
     * @param y
     */
    public Bullet(float x, float y) {
        // dx = 0 (không di chuyển ngang), dy = defaultSpeed (di chuyển lên)
        super(x, y, defaultWidth, defaultHeight, 0, defaultSpeed);
    }

    /**
     * Di chuyển viên đạn lên trên.
     */
    @Override
    public void move() {
        // dy là số âm.
        this.setY(this.getY() + dy);
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
        g2.fillRect((int) this.getX(), (int) this.getY(), (int) this.width, (int) this.height);
    }

    /**
     * Kiểm tra xem đạn đã bay ra khỏi màn hình chưa (ở cạnh trên).
     * @return true nếu đạn ra khỏi màn hình, ngược lại false.
     */
    public boolean isOffScreen() {
        // Khi y + chiều cao < 0 (hoàn toàn ra khỏi cạnh trên)
        return (this.getY() + this.height < 0);
    }
}