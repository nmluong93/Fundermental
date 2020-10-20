package concurrency.udemy.interrupt;

public class App {

	public static void main(String[] args) throws InterruptedException {
		Thread t = new MyThread();

		t.start();
		Thread.sleep(2_000);
		
		t.interrupt();
	}
}
