package telran.multithreading;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;
import java.util.stream.IntStream;


public class ListOperationsontroller {

	private static final int N_NUMBERS = 100_000;
	private static final int N_THREADS = 100;
	private static final int UPDATE_PROB = 0;
	private static final int N_RUNS = 1000;

	public static void main(String[] args) {
		Integer numbers[] = new Integer[N_NUMBERS];
		Arrays.fill(numbers, 100);
		List<Integer> list = new LinkedList<>(Arrays.asList(numbers));
		ListOperations operations[] = new ListOperations[N_THREADS];
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		Lock readLock = lock.readLock();
		Lock writeLock = lock.writeLock();
		AtomicInteger count = new AtomicInteger(0);
		IntStream.range(0, N_THREADS).forEach(i -> {
			operations[i]= new ListOperations(UPDATE_PROB, list,  
					readLock, writeLock, count, N_RUNS);
			operations[i].start();
		});;
		Arrays.stream(operations).forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println("total number of iterations for waiting lock opened " + count);
	}
}
