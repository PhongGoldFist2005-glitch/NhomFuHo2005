package game.entities;

import java.awt.Graphics2D;

// Lớp cơ sở cho các đối tượng có thể di chuyển kế thừa từ GameObject.
abstract class MovableObject extends GameObject {
    // dx,dy lần lượt là tốc độ di chuyển của vật thể
    protected float dx, dy;

    public MovableObject(float x, float y, float width, float height, float dx, float dy) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }

    // Method dùng để biểu diễu thao tác vật lý di chuyển của vật thể
    public abstract void move();

    // Method dùng để update các thông tin trong gameplay
    public abstract void update();

    // Method dùng để hiện thị gameplay.
    public abstract void render(Graphics2D g2);
}
