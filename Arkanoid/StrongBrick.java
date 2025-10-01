package Arkanoid;

// Xây dựng lớp gạch có máu khỏe hơn
public class StrongBrick extends Brick {
    protected int hitPoints = 2;
    protected int type = 1;

    public StrongBrick() {
        super(this.hitPoints, this.type);
    }
}
