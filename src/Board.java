import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import adapter.TAdapter;
import personagens.Dalek;
import personagens.DalekSaucer;
import personagens.GoodGuys;
import type.GoodGuysEnum;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

	private final int NUMBER_TO_WIN = 5;
	private final String WINNING_MESSAGE = "YOU HAVE DEFEATED THE TIME LORDS!!!";
	private final String GAME_OVER_MESSAGE = "YOU'RE A DESGRACE TO THE DALEK RACE";
	private final int DELAY = 10;
	private boolean gameOver = false;
	private Timer timer;
	ConsumidoraDoctors c;
	private DalekSaucer saucer;
	private static int n_daleks_on_earth = 0;
	private int[][] explosoes = new int[5][2];
	private int iExplosao = 0;

	public Board() {

		initBoard();
	}

	// inicia aleatoriamente os personagens que defendem a Terra
	private void initProdutoras() {

		BlockingQueue<GoodGuys> doctors = new ArrayBlockingQueue<>(10);

		ProdutoraGoodGuys pEleven = new ProdutoraGoodGuys(GoodGuysEnum.ELEVEN, doctors);
		ProdutoraGoodGuys pK9 = new ProdutoraGoodGuys(GoodGuysEnum.K9, doctors);
		ProdutoraGoodGuys pTen = new ProdutoraGoodGuys(GoodGuysEnum.TEN, doctors);
		ProdutoraGoodGuys pTardis = new ProdutoraGoodGuys(GoodGuysEnum.TARDIS, doctors);
		c = new ConsumidoraDoctors(doctors);
		pEleven.start();
		pK9.start();
		pTen.start();
		pTardis.start();
		c.start();
	}

	// inicia a visualização
	private void initBoard() {

		this.saucer = new DalekSaucer();

		this.initProdutoras();

		addKeyListener(new TAdapter<DalekSaucer>(this.saucer));
		setFocusable(true);
		setBackground(new Color(0, 0, 150));
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

		this.showExplosoes(g2d);

		g2d.drawImage(new ImageIcon("src/resources/earth.png").getImage(), 1100, (int) getSize().getHeight() / 2, this);

		g2d.drawImage(saucer.getImage(), saucer.getX(), saucer.getY(), this);

		List<GoodGuys> doctors = c.getDoctors();

		for (GoodGuys doctor : doctors) {
			if (doctor.getSpeed() != 0) {
				g2d.drawImage(doctor.getImage(), doctor.getX(), doctor.getY(), this);
			}
		}

		List<Dalek> daleks = saucer.getDaleks();

		// Listas para evitar ConcurrentException
		List<GoodGuys> doctorsToRemove = new ArrayList<GoodGuys>();
		List<Dalek> daleksToRemove = new ArrayList<Dalek>();

		for (GoodGuys doctor : doctors) {
			for (Dalek dalek : daleks) {
				if (Math.abs(doctor.getY() - dalek.getY()) < dalek.getHeight()
						&& Math.abs(doctor.getX() - dalek.getX()) < dalek.getWidth()) {
					doctorsToRemove.add(doctor);
					daleksToRemove.add(dalek);
					this.explode(doctor.getX(), doctor.getY());
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
		g2d.drawString(as1.getIterator(), 100, 300);
	}

	// adiciona a última explosão na lista
	private void explode(int x, int y) {
		if (iExplosao == 5)
			iExplosao = 0;
		this.explosoes[iExplosao][0] = x;
		this.explosoes[iExplosao][1] = y;
		iExplosao++;
	}

	// exibe as últimas cinco explosões na tela
	private void showExplosoes(Graphics2D g2d) {
		Random ran = new Random();
		for (int[] dim : explosoes) {
			if (dim[0] != 0 || dim[1] != 0)
				g2d.drawImage(new ImageIcon("src/resources/fogo" + ran.nextInt(2) + ".png").getImage(), dim[0], dim[1],
						this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		updateDaleks();
		updateDoctors();
		updateSaucer();

		repaint();
	}

	// atualiza a posição dos bonzinhos
	private void updateDoctors() {
		List<GoodGuys> doctors = this.c.getDoctors();
		for (GoodGuys doctor : doctors) {
			doctor.move();
		}
	}

	// atualiza a posição dos daleks e quantos já chegaram à Terra
	private void updateDaleks() {

		List<Dalek> daleks = this.saucer.getDaleks();

		for (int i = 0; i < daleks.size(); i++) {

			Dalek dalek = daleks.get(i);

			n_daleks_on_earth += dalek.move();

		}
	}

	// atualiza a posição da nave
	private void updateSaucer() {

		this.saucer.move();
	}
}