package telran.HomeWork2MessageBox;

public class MessageBox {
	String message;

	synchronized public void put(String message) {
		this.message = message;
		notify();
	}

	synchronized public String take() throws InterruptedException {
		while (message == null) {
			wait();
		}
		String res = message;
		message = null;
		return res;
	}
}