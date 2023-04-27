package telran.experiment2;

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
				int messageNumber = Integer.parseInt(message.replaceAll("\\D+", ""));
				if ((even && messageNumber % 2 == 0) || (!even && messageNumber % 2 != 0)) {
					System.out.printf("thread: %s; received message: %s\n", getName(), message);
				} else {
					messageBox.put(message);
				}
			} catch (InterruptedException e) {

			}
		}
	}
}