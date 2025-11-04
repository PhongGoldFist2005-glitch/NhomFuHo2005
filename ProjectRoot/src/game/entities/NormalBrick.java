package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import game.core.GameManager;

/**
 * Xây dựng cho lớp gạch thường.
 */
public class NormalBrick extends Brick {
    // 1 Máu và loại 1.
    protected int defaultHitPoints = 1;
    protected int defaultType = 1;
    GameManager gameManager;

    /**
     * Constructor cho gạch thường.
     * @param x
     * @param y
     * @param gameManager
     */
    public NormalBrick(float x, float y, GameManager gameManager) {
        super(x, y, 1, 1);
        this.gameManager = gameManager;
    }

    /**
     * Reset gạch về trạng thái ban đầu.
     * @param x
     * @param y
     */
    public void resetToDefaultBrick(float x, float y) {
        this.setX(x);
        this.setY(y);
        this.width = defaultWidth;
        this.height = defaultHeight;
        this.hitPoints = defaultHitPoints;
        this.type = defaultType;
    }

    /**
     * Vẽ gạch.
     */
    @Override
    public void render(Graphics2D g2) {
        if (this.check == false) {
            g2.setColor(Color.decode("#FF99CC"));
            g2.fillRect((int) this.getX(),(int) this.getY(),(int) this.width,(int) this.height);
        }
    }
}