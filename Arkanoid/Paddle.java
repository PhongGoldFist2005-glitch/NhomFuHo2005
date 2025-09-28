package Arkanoid;

// class cho thanh đỡ bóng kế thừa từ MovableObject.
public class Paddle extends MovableObject {
	// Tốc độ của thanh đỡ
	protected float speed;
	// Tham số thể hiện nâng cấp cho thanh đỡ
	protected float currentPowerUp;

	Paddle(float x, float y, float high, float width, float speed, float currentPowerUp) {
		this.x = x;
		this.y = y;
		this.high = high;
		this.width = width;
		this.speed = speed;
		this.currentPowerUp = currentPowerUp;
	}
	
	@Override
	public void update() {
		// TODO: Cập nhật vị trí hoặc trạng thái của thanh đỡ
		move();
	}

	@Override
	public void render() {
		// TODO: Vẽ thanh đỡ lên màn hình
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
