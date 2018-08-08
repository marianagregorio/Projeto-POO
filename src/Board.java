import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;
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

	private final int NUMBER_TO_WIN = 5;
	private final String WINNING_MESSAGE = "YOU HAVE DEFEATED THE TIME LORDS!!!";
	private final String GAME_OVER_MESSAGE = "YOU'RE A DESGRACE TO THE DALEK RACE";
	private final int DELAY = 10;
	private boolean gameOver = false;
	// private HashMap<Doctor, Dimension> docPositions;
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
		setBackground(Color.BLUE);
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

		if (this.gameOver) {
			this.gameOver(g2d);
			return;
		}
		if (n_daleks_on_earth >= this.NUMBER_TO_WIN) {

			this.win(g2d);
			return;

		}

		g2d.drawImage(saucer.getImage(), saucer.getX(), saucer.getY(), this);

		List<Doctor> doctors = c.getDoctors();
		int i = 0;
		// this.docPositions = new HashMap<>();
		for (Doctor doctor : doctors) {
			if (doctor.getSpeed() != 0) {
				// this.docPositions.put(doctor, new Dimension(doctor.getX(), doctor.getY()));
				g2d.drawImage(doctor.getImage(), doctor.getX(), doctor.getY(), this);
			}
		}

		List<Dalek> daleks = saucer.getDaleks();

		// Listas para evitar ConcurrentException
		List<Doctor> doctorsToRemove = new ArrayList<Doctor>();
		List<Dalek> daleksToRemove = new ArrayList<Dalek>();

		for (Doctor doctor : doctors) {
			for (Dalek dalek : daleks) {
				if (Math.abs(doctor.getY() - dalek.getY()) < dalek.getHeight()
						&& Math.abs(doctor.getX() - dalek.getX()) < dalek.getWidth()) {
					doctorsToRemove.add(doctor);
					daleksToRemove.add(dalek);
					this.explode();
				} else {
					if (doctor.getSpeed() != 0) {
						// this.docPositions.put(doctor, new Dimension(doctor.getX(), doctor.getY()));
						g2d.drawImage(doctor.getImage(), doctor.getX(), doctor.getY(), this);
					} else {
						doctorsToRemove.add(doctor);
					}
					g2d.drawImage(dalek.getImage(), dalek.getX(), dalek.getY(), this);
				}
			}
			if (Math.abs(doctor.getY() - this.saucer.getY()) < this.saucer.getHeight()
					&& Math.abs(doctor.getX() - this.saucer.getX()) < this.saucer.getWidth()) {
				this.gameOver = true;
			}

		}
		doctors.removeAll(doctorsToRemove);
		daleks.removeAll(daleksToRemove);

	}

	// limpa o quadro e exibe a mensagem de vitória
	private void win(Graphics2D g2d) {
		this.showMessage(g2d, this.WINNING_MESSAGE);
	}

	// limpa o quadro e exibe a menagem de fim de jogo
	private void gameOver(Graphics g2d) {
		this.showMessage(g2d, this.GAME_OVER_MESSAGE);
	}

	private void showMessage(Graphics g2d, String msg) {
		AttributedString as1 = new AttributedString(msg);
		as1.addAttribute(TextAttribute.SIZE, 40);
		as1.addAttribute(TextAttribute.FOREGROUND, Color.RED, 0, msg.length());
		as1.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_EXTRABOLD);
		g2d.drawString(as1.getIterator(), 50, 300);
		// this.saucer.clearDaleks();
	}

	// TODO: explosão
	private void explode() {
		System.out.println("EXPLODIU HAHAHHAHAHAHAHAH");
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
			// if (doctor.getSpeed() != 0) {
			doctor.move();
			// }
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