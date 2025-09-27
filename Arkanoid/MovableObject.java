package Arkanoid;
// Lớp cơ sở cho các đối tượng có thể di chuyển.
abstract class MovableObject extends GameObject {
    protected int dx, dy;

    public void move() {
        x += dx;
        y += dy;
    }
}
