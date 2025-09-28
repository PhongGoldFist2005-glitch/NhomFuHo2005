package Arkanoid;

// Lớp cơ sở cho các đối tượng có thể di chuyển kế thừa từ GameObject.
abstract class MovableObject extends GameObject {
    // dx,dy lần lượt là tốc độ di chuyển của vật thể
    protected float dx, dy;

    // Method dùng để biểu diễu thao tác vật lý di chuyển của vật thể
    public void move() {
        x += dx;
        y += dy;
    }
}
