package telran.multithreading;

import telran.multithreading.consumers.Receiver;
import telran.multithreading.producers.Sender;

public class SenderReceiversAppl {

    private static final int N_MESSAGES = 20;
    private static final int N_RECEIVERS = 10;

    public static void main(String[] args) throws InterruptedException {
        MessageBox messageBoxEven = new MessageBox();
        MessageBox messageBoxOdd = new MessageBox();
        Sender sender = new Sender(messageBoxEven, messageBoxOdd, N_MESSAGES);
        sender.start();
        for (int i = 0; i < N_RECEIVERS; i++) {
            new Receiver(messageBoxEven, messageBoxOdd, i % 2 == 0).start();
        }
        sender.join();
    }
}