package personagens;

import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Doctor {
	private int dx;
	private int dy;
	private int x = 40;
	private int y = 60;
	private int w;
	private int h;
	private int speed = -2;
	private Image image;
	private boolean visible = false;

	public Doctor() {

		loadImage();
	}

	
	public int getSpeed() {
		return this.speed;
	}
	public Doctor(int i, int nextInt) {
		this.x = i;
		this.y = nextInt;
		this.visible = true;
	}

	private void loadImage() {

		ImageIcon ii = new ImageIcon("src/resources/dalek.png");
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

	public void setDx(int dx) {
		this.dx = dx;
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
