package adapter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter<T extends KeyAdapter> extends KeyAdapter {

	private T objeto;
	
	public TAdapter(T objeto) {
		this.objeto = objeto;
	}
	
    @Override
    public void keyReleased(KeyEvent e) {
        this.objeto.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.objeto.keyPressed(e);
    }
}
