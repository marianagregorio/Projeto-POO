package personagens.generic;

import java.awt.Image;

public interface MovingStuff {

	public int getX();
	
	public int getY();
	
	public int getWidth();
	
	public int getHeight();
		
	public Image getImage();
	
	public int move();
}
