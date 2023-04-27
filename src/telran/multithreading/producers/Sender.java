package telran.multithreading.producers;

import telran.multithreading.MessageBox;

public class Sender extends Thread {
    private MessageBox messageBoxEven;
    private MessageBox messageBoxOdd;
    private int nMessages;

    public Sender(MessageBox messageBoxEven, MessageBox messageBoxOdd, int nMessages) {
        this.messageBoxEven = messageBoxEven;
        this.messageBoxOdd = messageBoxOdd;
        this.nMessages = nMessages;
    }

    @Override
    public void run() {
        for (int i = 1; i <= nMessages; i++) {
            if (i % 2 == 0) {
                messageBoxEven.put("message" + i);
            } else {
                messageBoxOdd.put("message" + i);
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {

            }
        }
    }
}