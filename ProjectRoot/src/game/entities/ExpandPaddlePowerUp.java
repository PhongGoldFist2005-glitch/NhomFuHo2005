package game.entities;

public class ExpandPaddlePowerUp extends PowerUp {
    protected int type = 0;
    protected float duration = 6; // Có thể set làm hằng số

    // Xây dựng vật thể nâng cấp
    public ExpandPaddlePowerUp(float x, float y, float width, float height, int type, float duration) {
        super(x, y, width, height, type, duration);
    }
    // override 3 cái cơ bản & override cho cái nhận sức mạnh
}