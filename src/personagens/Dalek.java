package personagens;
import java.awt.Image;

import javax.swing.ImageIcon;

import personagens.generic.MovingStuff;

public class Dalek implements MovingStuff {

	    private int x = 40;
	    private int y = 60;
	    private int w;
	    private int h;
	    private Image image;

	    private final int EARTH_X = 1100;
	    private int speed = 2;

	    public int move() {
	        
	        x += this.speed;
	        
	        if (x == EARTH_X && this.speed != 0) {
	            this.speed = 0;
	            return 1;
	        }
	        return 0;
	    }
	    public Dalek(int x, int y) {
	        this.x = x;
	        this.y = y;
	        this.loadImage("dalek");
	    }

	    private void loadImage(String relativePath) {
	        
	        ImageIcon ii = new ImageIcon("src/resources/" + relativePath + ".png");
	        this.image = ii.getImage(); 
	        
	        this.w = this.image.getWidth(null);
	        this.h = this.image.getHeight(null);
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
