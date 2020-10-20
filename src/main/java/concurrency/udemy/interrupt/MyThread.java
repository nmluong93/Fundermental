package concurrency.udemy.interrupt;

public class MyThread extends Thread {

	@Override
	public void run() {
		try {
			Thread.sleep(15_000);
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
			return;
		}
		System.out.println("Not passing here!");
	}

}
