package Arkanoid;

// Lớp trừu tượng chung cho mọi đối tượng trong game.
abstract class GameObject {
    // Định nghĩa tọa đồ trong màn hình chơi
    protected int x, y;
    // Giới hạn chiều rộng, chiều cao trong giao diện game
    protected int width, height;

    // Method dùng để update các thông tin trong gameplay
    public abstract void update();

    // Method dùng để hiện thị gameplay.
    public abstract void render();
}

