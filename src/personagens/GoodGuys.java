package personagens;

import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class GoodGuys {
	private int x;
	private int y;
	private int w;
	private int h;
	private int speed;
	private Image image;

	// força os personagens deste tipo a se moverem para a esquerda
	protected void setSpeed(int speed) {
		this.speed = - Math.abs(speed);
	}
	public int getSpeed() {
		return this.speed;
	}
	
	public GoodGuys(int x, int y, int speed, String nameImg) {
		this.x = x;
		this.y = y;
		this.setSpeed(speed);
		this.loadImage(nameImg);
	}

	private void loadImage(String relativePath) {

		ImageIcon ii = new ImageIcon("src/resources/" + relativePath + ".png");
		image = ii.getImage();

		w = image.getWidth(null);
		h = image.getHeight(null);
	}
	
	public void move() {

		x += this.speed;

		if (x == 0 && this.speed != 0) {
			this.speed = 0;
		}
	}

	public int getX() {

		return x;
	}

	public int getY() {

		return y;
	}

	public int getWidth() {

		return w;
	}

	public int getHeight() {

		return h;
	}

	public Image getImage() {

		return image;
	}
}
