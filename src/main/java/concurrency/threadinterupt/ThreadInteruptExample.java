package concurrency.threadinterupt;

import static java.lang.String.format;

public class ThreadInteruptExample implements Runnable {

	public static void main(String[] args) throws InterruptedException {
		Thread myT = new Thread(new ThreadInteruptExample(), "MyThread");
		myT.start();
		System.out.println(format("[%s] sleep in main thread for 5s...	", Thread.currentThread().getName()));
		Thread.sleep(5_000);
		
		System.out.println(format("[%s] interrupt myThread", Thread.currentThread().getName()));
		myT.interrupt();

		System.out.println(format("[%s] sleep in main thread for 5s...	", Thread.currentThread().getName()));
		Thread.sleep(5_000);
		
		System.out.println(format("[%s] interrupt myThread", Thread.currentThread().getName()));
		myT.interrupt();
	}

	@Override
	public void run() {
		try {
			System.out.println(format("[%s] is starting executing!", Thread.currentThread().getName()));
			Thread.sleep(Long.MAX_VALUE);
			System.out
					.println(format("[%s] finished waiting for %s!", Thread.currentThread().getName(), Long.MAX_VALUE));
		} catch (InterruptedException e) {
			System.out.println(format("[%s]  interrupted by exeption", Thread.currentThread().getName()));
		}
		while (!Thread.interrupted()) {
			// do nothing
		}
		System.out.println(format("[%s] is interrupted the second time", Thread.currentThread().getName()));
	}
}
