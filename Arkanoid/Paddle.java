package Arkanoid;

// class cho thanh đỡ bóng kế thừa từ MovableObject.
public class Paddle extends MovableObject {
	// Tốc độ của thanh đỡ
	protected float speed;
	// Tham số thể hiện nâng cấp cho thanh đỡ
	protected float currentPowerUp;

	public Paddle(float x, float y, float high, float width, float speed, float currentPowerUp) {
		super(x, y, high, width);
		this.speed = speed;
		this.currentPowerUp = currentPowerUp;
	}
	
	@Override
	public void update() {
		// TODO: Cập nhật trạng thái của thanh đỡ
	}

	@Override
	public void render() {
		// TODO: Vẽ thanh đỡ lên màn hình
	}

	@Override
	public void move() {
		// TODO: di chuyển thanh đỡ trên màn hình
	}
	
	// Phương thức cho thanh di chuyển sang bên trái
	public void moveLeft() {
		// pass
	}

	// Phương thức cho thanh di chuyển sang bên phải
	public void moveRight() {
		// pass
	}

	// Phương thức nhằm nâng cấp thanh trượt
	public void applyPowerUp() {
		// pass
	}
 }
