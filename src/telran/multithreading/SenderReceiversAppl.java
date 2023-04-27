package telran.multithreading;

import java.util.*;

import telran.multithreading.consumers.Receiver;
import telran.multithreading.producers.Sender;

public class SenderReceiversAppl {

	private static final int N_MESSAGES = 20;
	private static final int N_RECEIVERS = 10;

	public static void main(String[] args) throws InterruptedException {
	    MessageBox messageBox = new MessageBox();
	    Sender sender = new Sender(messageBox, N_MESSAGES);
	    sender.start();
	    List<Receiver> receivers = new ArrayList<>();
	    for (int i = 0; i < N_RECEIVERS; i++) {
	      Receiver receiver = new Receiver(messageBox);
	      receiver.start();
	      receivers.add(receiver);
	    }
	    sender.join();
	    receivers.forEach(r -> r.interrupt());
	    receivers.forEach(r -> {
	    	try {
				r.join();
			} catch (Exception e) {
				// TODO: handle exception
			}
	    });
	    System.out.println("Number of processed messages: " + Receiver.counter);
	  }
		


}