package game.entities;

import java.awt.Graphics2D;

// Lớp trừu tượng chung cho mọi đối tượng trong game.
abstract class GameObject {
    // Định nghĩa tọa đồ trong màn hình chơi
    protected float x, y;
    // Giới hạn chiều rộng, chiều cao của vật thể trong giao diện game
    protected float width;
    protected float height;

    public GameObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Method dùng để update các thông tin trong gameplay
    public abstract void update();

    // Method dùng để hiện thị gameplay.
    public abstract void render(Graphics2D g2);
}

