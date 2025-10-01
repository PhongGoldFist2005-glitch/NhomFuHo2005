package Arkanoid;

// Xây dựng class Brick
public class Brick extends GameObject {
	// Thông tin chứa máu của viên gạch và loại gạch
	private int hitPoints;
	protected int type;

	// Kiểm tra xem viên gạch đã bị tấn công bao nhiêu lần để trừ máu 
	public void takeHit() {
		// pass
	}

	// Kiểm tra xem viên gạch đã bị tiêu diệt hay chưa
	public boolean isDestroyed() {
		// pass
	}
	
	@Override
	public void update() {
		// TODO: Cập nhật trạng thái của viên gạch nếu cần
	}

	@Override
	public void render() {
		// TODO: Vẽ viên gạch lên màn hình
	}
}
