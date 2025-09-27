package Arkanoid;

public class Ball extends MovableObject {

	@Override
	public void update() {
		// TODO: Cập nhật vị trí hoặc trạng thái của bóng
		move();
	}

	@Override
	public void render() {
		// TODO: Vẽ bóng lên màn hình
		// Ví dụ: System.out.println("Vẽ bóng tại (" + x + ", " + y + ")");
	}
}
