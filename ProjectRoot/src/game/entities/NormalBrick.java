package game.entities;

// Xây dựng lớp gạch thường.
public class NormalBrick extends Brick {
    int hitPoints = 1;
    int type = 0;

    // Gạch thường chỉ có 1 máu và loại 0
    public NormalBrick() {
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
