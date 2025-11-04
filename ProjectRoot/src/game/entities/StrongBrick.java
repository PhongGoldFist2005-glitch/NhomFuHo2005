package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import game.core.GameManager;

/**
 * Xây dựng lớp gạch cứng.
 */
public class StrongBrick extends Brick {
    /**
     * 2 máu và loại 2 cho 1 viên gạch cứng.
     */
    protected int defaultHitPoints = 2;
    protected int defaultType = 2;
    GameManager gameManager;

    /**
     * Constructor cho Gạch cứng.
     * @param x
     * @param y
     * @param gameManager
     */
    public StrongBrick(float x, float y, GameManager gameManager) {
        super(x, y, 2, 2);
        this.gameManager = gameManager;
    }

    /**
     * Reset về default khi restart màn chơi.
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
     * Getter & Setter.
     */
    public void setType(int newType) {
        this.type = newType;
    }

    public int getType() {
        return this.type;
    }

    /**
     * Vẽ viên gạch cứng.
     */
    @Override
    public void render(Graphics2D g2) {
        if (this.check == false) {
            if (this.hitPoints == 2) {
                // Gạch còn 2 máu, vẽ màu xanh
                g2.setColor(Color.decode("#33CCFF"));
            } else if (this.hitPoints == 1) {
                // Gạch còn 1 máu, vẽ màu hồng (giống NormalBrick)
                g2.setColor(Color.decode("#FF99CC"));
            }

            g2.fillRect((int) this.getX(),(int) this.getY(),(int) this.width,(int) this.height);
        }
    }
}
