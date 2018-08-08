package personagens;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Dalek {

	    private int x = 40;
	    private int y = 60;
	    private int w;
	    private int h;
	    private Image image;

	    private final int EARTH_X = 1100;
	    private int speed = 2;

	    public int move() {
	        
	        x += speed;
	        
	        if (x == EARTH_X && this.speed != 0) {
	            this.speed = 0;
	            return 1;
	        }
	        return 0;
	    }
	    public Dalek(int x, int y) {
	        this.x = x;
	        this.y = y;
	        this.loadImage();
	    }

	    private void loadImage() {
	        
	        ImageIcon ii = new ImageIcon("src/resources/dalek.png");
	        image = ii.getImage(); 
	        
	        w = image.getWidth(null);
	        h = image.getHeight(null);
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
