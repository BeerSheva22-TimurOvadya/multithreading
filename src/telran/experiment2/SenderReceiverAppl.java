package telran.experiment2;

public class SenderReceiverAppl {

    private static final int N_MESSAGES = 20;
    private static final int N_RECEIVERS = 10;

    public static void main(String[] args) throws InterruptedException {
        MessageBox messageBox = new MessageBox();
        Sender sender = new Sender(messageBox, N_MESSAGES);
        sender.start();
        for (int i = 0; i < N_RECEIVERS; i++) {
            new Receiver(messageBox, i % 2 == 0).start();
        }
        sender.join();
    }
}