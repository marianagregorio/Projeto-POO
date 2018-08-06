package personagens;

import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class DalekSaucer extends KeyAdapter{
	 private int dx;
	    private int dy;
	    private int x = 40;
	    private int y = 60;
	    private int w;
	    private int h;
	    private Image image;
	    private List<Dalek> daleks;

	    public DalekSaucer() {
	    	this.daleks = new ArrayList<>();
	        loadImage();
	    }

	    private void loadImage() {
	        
	        ImageIcon ii = new ImageIcon("src/resources/dalek.png");
	        image = ii.getImage(); 
	        
	        w = image.getWidth(null);
	        h = image.getHeight(null);
	    }

	    public void move() {
	        x += dx;
	        y += dy;
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

	    public void keyPressed(KeyEvent e) {

	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_SPACE) {
	            fire();
	        }

	        if (key == KeyEvent.VK_LEFT) {
	            dx = -2;
	        }

	        if (key == KeyEvent.VK_RIGHT) {
	            dx = 2;
	        }

	        if (key == KeyEvent.VK_UP) {
	            dy = -2;
	        }

	        if (key == KeyEvent.VK_DOWN) {
	            dy = 2;
	        }
	    }

	    public void fire() {
	        daleks.add(new Dalek(this.x + this.w, this.y + this.h / 2));
	    }
	    
	    public List<Dalek> getDaleks() {
	    	return this.daleks;
	    }

	    public void keyReleased(KeyEvent e) {
	        
	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_LEFT) {
	            dx = 0;
	        }

	        if (key == KeyEvent.VK_RIGHT) {
	            dx = 0;
	        }

	        if (key == KeyEvent.VK_UP) {
	            dy = 0;
	        }

	        if (key == KeyEvent.VK_DOWN) {
	            dy = 0;
	        }
	    }

		public void clearDaleks() {
			this.daleks.clear();
			
		}

}
