package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import game.core.GameManager;

// Xây dựng lớp gạch thường.
public class NormalBrick extends Brick {
    protected int defaultHitPoints = 1;
    protected int defaultType = 1;
    protected float defaultWidth = 48;
    protected float defaultHeight = 24;
    GameManager gameManager;
    protected boolean check = false;

    // Gạch thường chỉ có 1 máu và loại 0
    public NormalBrick(float x, float y, GameManager gameManager) {
        super(x, y, 48, 24, 1, 1);
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

    @Override
    public void update() {
        // TODO: Cập nhật trạng thái của gạch
        this.check = this.isDestroyed();
    }

    @Override
    public void render(Graphics2D g2) {
        // TODO: Vẽ gạch lên màn hình
        if (this.check == false) {
            g2.setColor(Color.decode("#FF99CC"));
            g2.fillRect((int) this.x,(int) this.y,(int) this.width,(int) this.height);
        }
    }
}