package telran.multithreading.game;

import java.util.*;

public class CockroachRace {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean ContinueGame = true;
		while (ContinueGame) {
			Cockroach.fotoFinish = -1;
			System.out.println("Enter the number of runners in the race (minimum 2): ");
			int runner = scanner.nextInt();
			System.out.println("Enter track length (minimum 1): ");
			int distance = scanner.nextInt();
			if (runner >= 2 && distance >= 1) {
				List<Cockroach> cockroachThreads = new ArrayList<>();
				System.out.println("The race has begun");
				for (int i = 1; i <= runner; i++) {
					Cockroach cockroach = new Cockroach(i, distance);
					cockroachThreads.add(cockroach);
					cockroach.start();
				}				
				for (Cockroach cockroach : cockroachThreads) {
					try {
						cockroach.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("The race is over! Winner is the runner №" + Cockroach.fotoFinish);
			} else {
				System.out.println("The race must have a minimum of 2 runners, and the distance must be at least 1 meter.");						
			}			
			System.out.println("Start a new race? (y/n)");
			String answer = scanner.next();			
			if (!"y".equalsIgnoreCase(answer)) {
				ContinueGame = false;
			}
		}
		scanner.close();
	}
}