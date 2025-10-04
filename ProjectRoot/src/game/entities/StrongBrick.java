package game.entities;

// Xây dựng lớp gạch có máu khỏe hơn
public class StrongBrick extends Brick {
    protected int hitPoints = 2;
    protected int type = 1;

    public StrongBrick() {
        super(this.hitPoints, this.type);
    }

    @Override
	public void update() {
		// TODO: Cập nhật trạng thái của gạch
	}

	@Override
	public void render() {
		// TODO: Vẽ gạch lên màn hình
	}
}
