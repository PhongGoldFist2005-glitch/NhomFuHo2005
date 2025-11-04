package game.entities;

import game.core.GameManager;
import game.core.KeyPress;

import javax.swing.*;
import java.awt.*;

/**
 * class cho thanh đỡ bóng kế thừa từ MovableObject.
 */
public class Paddle extends MovableObject {
    // Tham số thể hiện nâng cấp cho thanh đỡ.
    protected int currentPowerUp;

    KeyPress keyH;
    GameManager gameManager;

    // Các giá trị mặc định ban đầu.
    protected float defaultX;
    protected float defaultY;
    protected static final float defaultWidth = 100;
    protected static final float defaultHeight = 10;
    protected static final float defaultSpeed = 10;
    protected int defaultPowerUp = 1;
    private boolean canShoot = false;
    private Image paddleImage;

    /**
     * Constructor cho Paddle.
     * @param keyH
     * @param gameManager
     */
    public Paddle(KeyPress keyH, GameManager gameManager) {
        // Truyền giá trị mặc định ban đầu cho super.
        // Để hiện thị chiều cao của thanh trượt
        super((gameManager.getBoardWidth() - defaultWidth) / 2, gameManager.getBoardHeight() - defaultHeight - 40, defaultWidth, defaultHeight, defaultSpeed, 0);
        this.keyH = keyH;
        this.gameManager = gameManager;
        this.defaultX = (gameManager.getBoardWidth() - defaultWidth) / 2;
        this.defaultY = gameManager.getBoardHeight() - defaultHeight - 40;
        this.currentPowerUp = defaultPowerUp;
        // Check cho power up bắn đạn.
        this.canShoot = false;
        // Hiện thị hình ảnh cho ván.
        try {
            String imagePath = "/assets/images/paddle.png";

            this.paddleImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh paddle: " + e.getMessage());
            this.paddleImage = null; // Đặt là null nếu không tải được
        }
    }

    // Sau này nếu bạn muốn reset về mặc định:
    public void resetToDefault() {
        this.setX(defaultX);
        this.setY(defaultY);
        this.width = defaultWidth;
        this.height = defaultHeight;
        this.dx = defaultSpeed;
        this.dy = 0;
        this.currentPowerUp = defaultPowerUp;
    }

    /**
     * Update.
     */
    @Override
    public void update() {
        move();
    }

    /**
     * Vẽ thanh đỡ lên màn hình.
     */
    @Override
    public void render(Graphics2D g2) {
        if (paddleImage != null) {
            // Vẽ ảnh nếu đã tải thành công
            g2.drawImage(paddleImage, (int) this.getX(), (int) this.getY(), (int) this.width, (int) this.height, null);
        } else {
            // Vẽ hình chữ nhật màu làm phương án dự phòng nếu không có ảnh
            g2.setColor(Color.decode("#CC00FF"));
            g2.fillRect((int) this.getX(), (int) this.getY(), (int) this.width, (int) this.height);
        }
    }

	/**
	 * Class chịu trách nhiệm cho quản lý di chuyển.
	 * của vật.
	 */
	@Override
	public void move() {
        if (moveLeft()) {
            if (this.getX() - dx < 30) {
                this.setX(30);
            } else {
                this.setX(this.getX() - dx);
            }
        }

        if (moveRight()) {
			if (this.getX() + this.width + dx > gameManager.getBoardWidth() - 40) {
				this.setX(gameManager.getBoardWidth() - this.width - 40);
			} else {
				this.setX(this.getX() + dx);
			}
        }
    }

    /**
     * Getter và setter.
     * @return
     */
    // Phương thức cho thanh di chuyển sang bên trái.
    public boolean moveLeft() {
        return this.keyH.moveLeft == true;
    }

    // Phương thức cho thanh di chuyển sang bên phải.
    public boolean moveRight() {
        return this.keyH.moveRight == true;
    }

    // Phương thức nhằm nâng cấp thanh trượt.
    public void applyPowerUp() {
        // pass
    }

    public float getWidth() {
        return this.width;
    }

    public float getDefaultWidth() {
        float originWidth = defaultWidth;
        return originWidth;
    }

    public float getHeight() {
        return this.height;
    }

    public void setWidth(float newWidth) {
        this.width = newWidth;
    }

    /**
     * Setter để bật/tắt khả năng bắn.
     * @param canShoot true để cho phép bắn, false để vô hiệu hóa.
     */
    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    /**
     * Getter để kiểm tra xem paddle có thể bắn không.
     * @return true nếu paddle có thể bắn.
     */
    public boolean canShoot() {
        return this.canShoot;
    }
}