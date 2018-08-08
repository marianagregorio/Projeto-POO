import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import personagens.Eleven;
import personagens.GoodGuys;

public class ConsumidoraDoctors extends Thread {

	BlockingQueue<GoodGuys> link;
	
	ArrayList<GoodGuys> doctors = new ArrayList<>();

	public ConsumidoraDoctors(BlockingQueue<GoodGuys> doctors2) {
		this.link = doctors2;
	}
	
	public ArrayList<GoodGuys> getDoctors() {
		return this.doctors;
	}

	@Override
	public void run() {
		Random ran = new Random();
		for (int i = 0; i < 5000; i++) {
			try {
				GoodGuys doc = link.take();
				Thread.sleep(ran.nextInt(800)+200);
				this.doctors.add(doc);
			} catch (InterruptedException ex) {
				break;
			}
		}
	}

}