package telran.multithreading.game.listWinner;

import java.util.*;

import telran.view.*;

public class CockroachRaceWithList {
	
	public static void main(String[] args) {
		InputOutput io = new StandardInputOutput();
		Menu main = new Menu("Game <<Cockroach Race v.2.0>> ", Item.of("Start game", x -> startRace(io), false), Item.exit());
		main.perform(io);
	}
	
    public static void startRace(InputOutput io) {
    	CockroachRaceWithList race = new CockroachRaceWithList();
    	
		int runner = io.readInt("Enter the number of runners in the race (2-20):",
				"ERROR: ", 2, 20);
		
		int distance = io.readInt("Enter track length (1-100): ",
				"ERROR: ", 1, 100);
		
        while (true) {
            if (runner >= 2 && distance >= 1) {
                List<CockroachWithList> cockroachThreads = new ArrayList<>();
                
                long startTime = System.currentTimeMillis();
                
                for (int i = 1; i <= runner; i++) {
                    CockroachWithList cockroach = new CockroachWithList(i, distance);
                    cockroachThreads.add(cockroach);
                    cockroach.start();
                }
                
                for (CockroachWithList cockroach : cockroachThreads) {
                    try {
                        cockroach.join();
                        cockroach.finishTime -= startTime;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
                cockroachThreads.sort(Comparator.comparingLong(CockroachWithList::getFinishTime));
                
                System.out.println("******Race results******");
                int rank = 1;
                for (CockroachWithList cockroach : cockroachThreads) {
                   System.out.printf("%d: Runner№%d finished in %dms\n", rank, cockroach.getId(), cockroach.getFinishTime());
                	rank++;
                }
            } 
             break;
        }

    }
}