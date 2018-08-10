import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ComandosBoard extends KeyAdapter{
	
	Board board;
	
	public ComandosBoard(Board board) {
		this.board = board;
	}

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_F2) {
            board.reload();
        }
    }
}
