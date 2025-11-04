package game.entities;

import java.awt.Graphics2D;

/**
 * Xây dựng class Brick.
 */
public class Brick extends GameObject {
    // Thông tin chứa máu của viên gạch và loại gạch.
    protected int hitPoints;
    protected int type;
    protected static final float defaultWidth = 48;
    protected static final float defaultHeight = 24;
    // Kiểm tra gạch đã bị phá hủy hay chưa.
    protected boolean check = false;
    
    /**
     * Constructor của gạch.
     * @param x
     * @param y
     * @param width
     * @param height
     * @param hitPoints
     * @param type
     */
    public Brick(float x, float y, int hitPoints, int type) {
        super(x, y, defaultWidth, defaultHeight);
        this.hitPoints = hitPoints;
        this.type = type;
    }

    /**
     * Kiểm tra xem viên gạch đã bị tấn công bao nhiêu lần để trừ máu.
     * @return
     */
    public boolean takeHit() {
        this.hitPoints -= 1;
        // Trả về true nếu cú đánh này phá hủy gạch.
        return isDestroyed();
    }

    /**
     * Kiểm tra xem viên gạch đã bị tiêu diệt hay chưa.
     */
    public boolean isDestroyed() {
        if (this.hitPoints == 0) {
            return true;
        }
        return false;
    }

    /**
     * Update viên gạch.
     */
    @Override
    public void update() {
        this.check = this.isDestroyed();
    }

    @Override
    public void render(Graphics2D g2) {
        // Đối với từng loại gạch sẽ có cách hiện thị khác nhau.
    }
}