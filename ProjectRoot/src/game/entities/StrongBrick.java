package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import game.core.GameManager;

// Xây dựng lớp gạch có máu khỏe hơn
public class StrongBrick extends Brick {
    protected int defaultHitPoints = 2;
    protected int defaultType = 2;
    protected float defaultWidth = 48;
    protected float defaultHeight = 24;
    GameManager gameManager;
    protected boolean check = false;

    // Gạch thường chỉ có 1 máu và loại 0
    public StrongBrick(float x, float y, GameManager gameManager) {
        super(x, y, 48, 24, 2, 2);
        this.gameManager = gameManager;
    }

    public void resetToDefaultBrick(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = defaultWidth;
        this.height = defaultHeight;
        this.hitPoints = defaultHitPoints;
        this.type = defaultType;
    }

    public void setType(int newType) {
        this.type = newType;
    }

    public int getType() {
        return this.type;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    @Override
    public void update() {
        // TODO: Cập nhật trạng thái của gạch
        this.check = this.isDestroyed();
    }

    @Override
    public void render(Graphics2D g2) {
        // TODO: Vẽ gạch lên màn hình
        if (this.check == false) {
            g2.setColor(Color.decode("#33CCFF"));
            g2.fillRect((int) this.x,(int) this.y,(int) this.width,(int) this.height);
        }
    }
}
