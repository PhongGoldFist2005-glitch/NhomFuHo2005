package Arkanoid;

// Vât phẩm nâng cấp sức mạnh của quả bóng
public class PowerUp extends GameObject {
    protected int type;
    protected float duration;

    // Xây dựng vật thể nâng cấp
    public GameObject(float x, float y, float width, float height, int type, float duration) {
        super(x, y, width, height);
        this.type = type;
        this.duration = duration;
    }

    // Xây dựng cách tiếp nhận power up
    public void applyEffect(Paddle paddle) {
        // pass
    }

    public void removeEffect(Paddle paddle) {
        // pass
    }

    @Override
    public void update() {
        // pass
    }

    @Override
    public void render() {
        // pass
    }

    @Override
    public void move() {
        // pass
    }
}
