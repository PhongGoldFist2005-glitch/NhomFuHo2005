package game.entities;

// Xây dựng lớp ball.
public class Ball extends MovableObject {
	// tham số chưa tốc độ, hướng của quả bóng
	protected float speed;
	protected float directionX; // -1: sang trái, 1: sang phải
	protected float directionY; // -1: lên, 1: xuống

	public Ball(float x, float y, float radius, float speed) {
        super(x, y, radius * 2, radius * 2); // dùng width, height = đường kính
        this.speed = speed;
        this.directionX = 1; // mặc định đi sang phải
        this.directionY = -1; // mặc định đi lên
        // updateVelocity();
    }
	
	@Override
	public void update() {
		// TODO: Cập nhật trạng thái của bóng
	}

	@Override
	public void render() {
		// TODO: Vẽ bóng lên màn hình
		// Ví dụ: System.out.println("Vẽ bóng tại (" + x + ", " + y + ")");
	}

	@Override
	public void move() {
		// Cập nhật vị trí của bóng
	}

	// Xử lý quả bóng khi va chạm
	public void bounceOff(GameObject other) {
		// pass
	}

	// Xử lý khi bóng va chạm với 1 object khác
	public void checkCollision(GameObject other) {
		// pass
	}
}
