package personagens.generic;

import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class GoodGuys {
	private int x;
	private int y;
	private int w;
	private int h;
	private int speed;
	private Image image;
	private int strength;

	// força os personagens deste tipo a se moverem para a esquerda
	protected void setSpeed(int speed) {
		this.speed = -Math.abs(speed);
	}

	public int getSpeed() {
		return this.speed;
	}

	public GoodGuys(int x, int y, int speed, String nameImg, int strength) {
		this.x = x;
		this.y = y;
		this.setStrength(strength);
		this.setSpeed(speed);
		this.loadImage(nameImg);
	}

	public GoodGuys(int x, int y, int speed, String nameImg) {
		this(x, y, speed, nameImg, 1);
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

	public int getStrength() {
		return this.strength;
	}

	private void setStrength(int strength) {
		if (strength > 0)
			this.strength = strength;
		else
			this.strength = 0;
	}

	public void weaken() {
		this.weaken(1);
	}

	public void weaken(int n) {
		this.setStrength(this.strength -= Math.abs(n));
	}
}
