package game.entities;

import java.awt.*;

/**
 * Lớp trừu tượng chung cho mọi đối tượng trong game.
*/
abstract class GameObject {
    // Định nghĩa tọa đồ trong màn hình chơi.
    private float x, y;
    // Giới hạn chiều rộng, chiều cao của vật thể trong giao diện game.
    protected float width;
    protected float height;

    /**
     * Constructor cơ bản cho 1 đối tượng trong game.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public GameObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Lấy thông tin của đối tượng.
     * @return
     */
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }
    /**
     * Getter & Setter.
     */
    public float getX() {
        return this.x;
    }
    public float getY() {
        return this.y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    // Method dùng để update các thông tin trong gameplay
    public abstract void update();

    // Method dùng để hiện thị gameplay.
    public abstract void render(Graphics2D g2);
}