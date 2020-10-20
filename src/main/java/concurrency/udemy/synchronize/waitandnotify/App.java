package concurrency.udemy.synchronize.waitandnotify;

public class App {

	public static void main(String[] args) {
		MessageBean msg = new MessageBean();
		Runnable producer = new Producer(msg);
		Thread t1 = new Thread(producer);
		
		Runnable consumer = new Consumer(msg);
		Thread t2 = new Thread(consumer);
		
		t2.start();
		t1.start();
	}
}
