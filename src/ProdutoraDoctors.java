import java.util.Random;
import java.util.concurrent.BlockingQueue;

import personagens.Doctor;

public class ProdutoraDoctors extends Thread {

	BlockingQueue<Doctor> link;
	int numero;

	public ProdutoraDoctors(int numero, BlockingQueue<Doctor> pilha) {
		this.numero = numero;
		this.link = pilha;
	}

	@Override
	public void run() {
		Random ran = new Random();
		while (true) {
			Doctor doc = new Doctor(1200, ran.nextInt(600));
			try {
				link.put(doc);
				Thread.sleep(ran.nextInt(800)+600);
			} catch (InterruptedException ex) {
				break;
			}
		}
	}
}