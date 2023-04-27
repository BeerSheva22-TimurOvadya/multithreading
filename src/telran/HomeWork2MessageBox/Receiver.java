package telran.HomeWork2MessageBox;

public class Receiver extends Thread {
    private MessageBox messageBoxEven;
    private MessageBox messageBoxOdd;
    private boolean even;

    public Receiver(MessageBox messageBoxEven, MessageBox messageBoxOdd, boolean even) {
        this.messageBoxEven = messageBoxEven;
        this.messageBoxOdd = messageBoxOdd;
        this.even = even;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message;
                if (even) {
                    message = messageBoxEven.take();
                } else {
                    message = messageBoxOdd.take();
                }
                System.out.printf("thread: %s; received message: %s\n", getName(), message);
            } catch (InterruptedException e) {

            }
        }
    }
}