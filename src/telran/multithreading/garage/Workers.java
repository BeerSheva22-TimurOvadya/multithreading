package telran.multithreading.garage;

import java.util.concurrent.*;

public class Workers extends Thread {
	private BlockingQueue<Cars> cars;
	private volatile boolean running = true;
	

	public void setRunning(boolean running) {
		this.running = running;
	}


	@Override
	public void run() {
		Cars car = null;
		while(running) {
			try {
				car = cars.take();
				carService(car);
			} catch (InterruptedException e) {
				
			}
		}
		while((car = cars.poll()) != null) {
			carService(car);
		}
		

	}


	private void carService(Cars car) {
		try {
			sleep(car.getServiceTime());
		} catch (InterruptedException e) {
			
		}
		
	}


	public Workers(BlockingQueue<Cars> cars) {
		this.cars = cars;
	}
}