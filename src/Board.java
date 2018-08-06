import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import adapter.TAdapter;
import personagens.Dalek;
import personagens.DalekSaucer;
import personagens.Doctor;

public class Board extends JPanel implements ActionListener {

	private final int ICRAFT_X = 40;
	private final int ICRAFT_Y = 60;
	private final int DELAY = 10;
	private Timer timer;
	ConsumidoraDoctors c;
	private DalekSaucer saucer;
	private static int n_daleks_on_earth = 0;

	public Board() {

		initBoard();
	}

	private void initBoard() {

		this.saucer = new DalekSaucer();

		BlockingQueue<Doctor> doctors = new ArrayBlockingQueue<>(10);

		ProdutoraDoctors p = new ProdutoraDoctors(1, doctors);
		c = new ConsumidoraDoctors(doctors);
		p.start();
		c.start();

		addKeyListener(new TAdapter<DalekSaucer>(this.saucer));
		setFocusable(true);
		setBackground(Color.DARK_GRAY);
		setDoubleBuffered(true);

		this.timer = new Timer(DELAY, this);
		this.timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);

		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		if (n_daleks_on_earth >= 5) {

			AttributedString as1 = new AttributedString("YOU WIN");
			as1.addAttribute(TextAttribute.SIZE, 40);
			as1.addAttribute(TextAttribute.FOREGROUND, Color.RED, 0, 7);
			g2d.drawString(as1.getIterator(), 550, 200);
			this.saucer.clearDaleks();

		} else {

			g2d.drawImage(saucer.getImage(), saucer.getX(), saucer.getY(), this);

			List<Dalek> daleks = saucer.getDaleks();

			for (Dalek dalek : daleks) {

				g2d.drawImage(dalek.getImage(), dalek.getX(), dalek.getY(), this);
			}

			List<Doctor> doctors = c.getDoctors();

			for (Doctor doctor : doctors) {
				if (doctor.getSpeed() != 0)
					// TODO: descobrir o problema com a imagem do Doctor
					g2d.drawImage(this.saucer.getImage(), doctor.getX(), doctor.getY(), this);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		updateDaleks();
		updateDoctors();
		updateSaucer();

		repaint();
	}

	private void updateDoctors() {
		List<Doctor> doctors = this.c.getDoctors();
		for (Doctor doctor : doctors) {
			if (doctor.getSpeed() != 0) {
//				doctors.remove(doctor);
//			} else {
				doctor.move();
			}
		}
	}

	private void updateDaleks() {

		List<Dalek> daleks = this.saucer.getDaleks();

		for (int i = 0; i < daleks.size(); i++) {

			Dalek dalek = daleks.get(i);

			// if (dalek.isVisible()) {

			n_daleks_on_earth += dalek.move();
			// } else {

			// daleks.remove(i);
			// }
		}
	}

	private void updateSaucer() {

		this.saucer.move();
	}
}