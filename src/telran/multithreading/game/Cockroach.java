package telran.multithreading.game;

public class Cockroach extends Thread {
	private final int id;
	private final int distance;
	static int fotofinish = -1;

	public Cockroach(int id, int distance) {
		this.id = id;
		this.distance = distance;
	}

	@Override
	public void run() {
		for (int i = 1; i < distance; i++) {
			System.out.printf("Runner №%d run %d meters.\n", id, i);
			try {
				Thread.sleep((long) (Math.random() * 100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.printf("*****Runner №%d finished!!*****\n", id);
		if (fotofinish == -1) {
			fotofinish = id;
		}
	}
}
