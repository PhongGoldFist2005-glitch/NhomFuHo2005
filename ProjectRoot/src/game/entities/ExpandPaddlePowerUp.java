package game.entities;

public class ExpandPaddlePowerUp extends PowerUp {
    protected int type = 0;
    protected float duration; // Có thể set làm hằng số
    
    // Xây dựng vật thể nâng cấp
    public GameObject(float x, float y, float width, float height) {
        super(x, y, width, height, this.type, this.duration);
    }
    // override 3 cái cơ bản & override cho cái nhận sức mạnh
}
