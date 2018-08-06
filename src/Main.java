import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main extends JFrame {
    
	private static final long serialVersionUID = 1L;

	public Main() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setSize(1250, 600);
    	setBackground(new Color(0, 0, 130));

        setTitle("EXTERMINATE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }    
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
            
        });
    }
}
