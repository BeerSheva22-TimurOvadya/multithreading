package telran.multithreading;

public class Printer extends Thread {
	private String str;
	private int nRuns;

	@Override
	public void run() {
		for(int i=0; i < nRuns; i++) {
			System.out.println(str);
			try {
				sleep(10);
			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}

	}

	public Printer(String str, int nRuns) {
		super();
		this.str = str;
		this.nRuns = nRuns;
	}
}
