package telran.multithreading;

public class Deadlock1 {
    public static void main(String[] args) {
        Object resourceA = new Object();
        Object resourceB = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println("Thread 1: Busy resource A");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resourceB) {
                    System.out.println("Thread 1: Busy resource B");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resourceB) {
                System.out.println("Thread 2: Busy resource B");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resourceA) {
                    System.out.println("Thread 2: Busy resource A");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
