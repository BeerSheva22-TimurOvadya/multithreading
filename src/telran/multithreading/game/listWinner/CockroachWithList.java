package telran.multithreading.game.listWinner;



public class CockroachWithList extends Thread {
    private final int id;
    private final int distance;
    long finishTime;

    public CockroachWithList(int id, int distance) {
        this.id = id;
        this.distance = distance;
    }

    @Override
    public void run() {
        for (int i = 1; i <= distance; i++) {
            System.out.printf("Runner №%d run %d meters.\n", id, i);
            try {
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        finishTime = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public long getFinishTime() {
        return finishTime;
    }
}