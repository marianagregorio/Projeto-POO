import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import personagens.Doctor;

public class ConsumidoraDoctors extends Thread {

	BlockingQueue<Doctor> link;
	
	ArrayList<Doctor> doctors = new ArrayList<>();

	public ConsumidoraDoctors(BlockingQueue<Doctor> pilha) {
		this.link = pilha;
	}
	
	public ArrayList<Doctor> getDoctors() {
		return this.doctors;
	}

	@Override
	public void run() {
		Random ran = new Random();
		for (int i = 0; i < 5000; i++) {
			try {
				Doctor doc = link.take();
				Thread.sleep(ran.nextInt(800)+200);
				this.doctors.add(doc);
			} catch (InterruptedException ex) {
				break;
			}
		}
	}

}