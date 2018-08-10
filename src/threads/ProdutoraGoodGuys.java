package threads;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import personagens.Eleven;
import personagens.K9;
import personagens.Tardis;
import personagens.Ten;
import personagens.generic.GoodGuys;
import type.GoodGuysEnum;

public class ProdutoraGoodGuys extends Thread {

	BlockingQueue<GoodGuys> link;
	GoodGuysEnum tipo;

	public ProdutoraGoodGuys(GoodGuysEnum tipo, BlockingQueue<GoodGuys> pilha) {
		this.tipo = tipo;
		this.link = pilha;
	}

	private GoodGuys getNew(int x, int y) {
		switch (this.tipo) {
		case ELEVEN:
			return new Eleven(x, y);
		case K9:
			return new K9(x, y);
		case TEN:
			return new Ten(x, y);
		case TARDIS:
			return new Tardis(x, y);
		default:
			return null;
		}
	}

	@Override
	public void run() {
		Random ran = new Random();
		while (true) {
			GoodGuys doc = this.getNew(1050, ran.nextInt(600));
			try {
				link.put(doc);
				Thread.sleep(ran.nextInt(800) + 600);
			} catch (InterruptedException ex) {
				break;
			}
		}
	}
}