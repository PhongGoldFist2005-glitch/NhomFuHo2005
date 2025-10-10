package game.entities;

import java.awt.Graphics2D;

// Xây dựng class Brick.
public class Brick extends GameObject {
    // Thông tin chứa máu của viên gạch và loại gạch
    protected int hitPoints;
    protected int type;

    public Brick(float x, float y, float width, float height, int hitPoints, int type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
    }

    // Kiểm tra xem viên gạch đã bị tấn công bao nhiêu lần để trừ máu
    public void takeHit() {
        // pass
        this.hitPoints -= 1;
    }

    // Kiểm tra xem viên gạch đã bị tiêu diệt hay chưa
    public boolean isDestroyed() {
        if (this.hitPoints == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        // TODO: Cập nhật trạng thái của viên gạch nếu cần
    }

    @Override
    public void render(Graphics2D g2) {
        // TODO Auto-generated method stub
    }
}