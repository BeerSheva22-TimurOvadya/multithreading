package telran.multithreading.garage;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class GarageAppl {
static final long MODEL_TIME = 10000; 
static final long MIN_SERVICE_TIME = 60;
static final long MAX_SERVICE_TIME = 600;
static final int N_WORKERS = 5;
static final int PROB_CAR_MIN = 10;
private static final int  CAPCITY = 15;
static int rejectsCounter = 0;
static int carsCounter = 0;
static BlockingQueue<Cars> cars = new LinkedBlockingQueue<>(CAPCITY);
	public static void main(String[] args) throws InterruptedException {
		
		Workers workers[] = new Workers[N_WORKERS];
		startWorkers(workers);
		Instant start = Instant.now();
		for (int i = 0; i < MODEL_TIME; i++) {
			Cars car = getCar();
			if (car != null) {
				if (cars.offer(car)) {
					carsCounter++;
				} else {
					rejectsCounter++;
				}
			}
			Thread.sleep(1);
		}
		stopWorkers(workers);
		System.out.printf("modelling time %d hours; cars served %d; cars rejected %d",
				ChronoUnit.MILLIS.between(start, Instant.now()) / 60, carsCounter, rejectsCounter);
		

	}
	private static void stopWorkers(Workers[] workers) {
		Arrays.stream(workers).forEach(w -> {
			w.setRunning(false);
			w.interrupt();
		});
		
	}
	private static Cars getCar() {
		Cars car = null;
		if (getRandomNumber(0, 100) < PROB_CAR_MIN) {
			car = new Cars(getRandomNumber(MIN_SERVICE_TIME, MAX_SERVICE_TIME));
		}
		return car;
	}
	private static long getRandomNumber(long min, long max) {
		
		return ThreadLocalRandom.current().nextLong(min, max);
	}
	private static void startWorkers(Workers[] workers) {
		IntStream.range(0, workers.length).forEach(i -> {
			workers[i] = new Workers(cars);
			workers[i].start();
		});
		
	}

}