package telran.experiment;

public class Receiver extends Thread {
    private MessageBox messageBox;
    private boolean even;

    public Receiver(MessageBox messageBox, boolean even) {
        this.messageBox = messageBox;
        this.even = even;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = messageBox.take();
                boolean messageEven = message.charAt(message.length() - 1) == 'e';

                if (messageEven == even) {
                    System.out.printf("thread: %s; received message: %s\n", getName(), message);
                } else {
                    messageBox.put(message);
                }
            } catch (InterruptedException e) {

            }
        }
    }
}