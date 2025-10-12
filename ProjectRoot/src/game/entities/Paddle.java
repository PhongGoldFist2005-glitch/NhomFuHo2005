package game.entities;

import game.core.GameManager;
import game.core.KeyPress;

import java.awt.Color;
import java.awt.Graphics2D;

// class cho thanh đỡ bóng kế thừa từ MovableObject.
public class Paddle extends MovableObject {
    // Tham số thể hiện nâng cấp cho thanh đỡ
    protected int currentPowerUp;

    KeyPress keyH;
    GameManager gameManager;

    // Các giá trị mặc định ban đầu
    protected float defaultX;
    protected float defaultY;
    protected float defaultWidth = 80;
    protected float defaultHeight = 10;
    protected float defaultSpeed = 4;
    protected int defaultPowerUp = 1;

    public float getDefaultWidth(){
        return defaultWidth;
    }

    public float getDefaultHeight() {
        return defaultHeight;
    }

    public Paddle(KeyPress keyH, GameManager gameManager) {

        // Truyền giá trị mặc định ban đầu cho super
//        float startWidth = gameManager.getBoardWidth() / 2;
//        float startHeight = gameManager.getBoardHeight();
        // Để hiện thị chiều cao của thanh trượt
        // lấy chiều cao toàn màn hình - chiều cao của vật thể
        super(gameManager.boardWidth / 2, gameManager.boardHeight - 15, 80, 10, 10, 0);
        this.keyH = keyH;
        this.gameManager = gameManager;

        this.defaultX = gameManager.getBoardWidth() / 2;
        this.defaultY = gameManager.getBoardHeight() - 15;
        this.currentPowerUp = defaultPowerUp;
    }

    // Sau này nếu bạn muốn reset về mặc định:
    public void resetToDefault() {
        this.x = defaultX;
        this.y = defaultY;
        this.width = defaultWidth;
        this.height = defaultHeight;
        this.dx = defaultSpeed;
        this.dy = defaultSpeed;
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
        g2.setColor(Color.decode("#CC00FF"));
        g2.fillRect((int) this.x, (int) this.y, (int) this.width, (int) this.height);
    }

	/**
	 * Class chịu trách nhiệm cho quản lý di chuyển
	 * của vật.
	 */
	@Override
	public void move() {
        if (moveLeft()) {
            if (this.x - dx < 0) {
                this.x = 0;
            } else {
                this.x -= dx;
            }
        }

        if (moveRight()) {
			if (this.x + this.width + dx > gameManager.getBoardWidth()) {
				this.x = gameManager.getBoardWidth() - this.width;
			} else {
				this.x += dx;
			}
        }
    }

    // Phương thức cho thanh di chuyển sang bên trái
    public boolean moveLeft() {
        return this.keyH.moveLeft == true;
    }

    // Phương thức cho thanh di chuyển sang bên phải
    public boolean moveRight() {
        return this.keyH.moveRight == true;
    }

    // Phương thức nhằm nâng cấp thanh trượt
    public void applyPowerUp() {
        // pass
    }
}