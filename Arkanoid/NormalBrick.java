package Arkanoid;

// Xây dựng lớp gạch thường.
public class NormalBrick extends Brick {
    int hitPoints = 1;
    int type = 0;

    // Gạch thường chỉ có 1 máu và loại 0
    public NormalBrick() {
        super(this.hitPoints, this.type);
    }
}
