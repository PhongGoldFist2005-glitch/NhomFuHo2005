package Arkanoid;

// Lớp cơ sở cho các đối tượng có thể di chuyển kế thừa từ GameObject.
abstract class MovableObject extends GameObject {
    // dx,dy lần lượt là tốc độ di chuyển của vật thể
    protected float dx, dy;

    public MovableObject(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.dx = 0;
        this.dy = 0;
    }

    // Method dùng để biểu diễu thao tác vật lý di chuyển của vật thể
    public abstract void move();
}
