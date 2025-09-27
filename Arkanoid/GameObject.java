package Arkanoid;
// Lớp trừu tượng chung cho mọi đối tượng trong game
abstract class GameObject {
    protected int x, y;
    protected int width, height;

    public abstract void update();
    public abstract void render();
}

