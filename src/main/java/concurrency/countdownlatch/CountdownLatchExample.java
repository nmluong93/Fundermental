package concurrency.countdownlatch;

import static java.lang.String.format;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchExample implements Runnable {

	private static final int THREAD_NUM = 5;
	private static final CountDownLatch LATCH = new CountDownLatch(THREAD_NUM);
	private static final Random RAND = new Random(100_000);

	@Override
	public void run() {
		try {
			int radomSleep = Math.abs(RAND.nextInt()/10_000);
			System.out.println(format("Current thread %s is sleeping for %s seconds", Thread.currentThread().getName(),
					radomSleep));
			Thread.sleep(radomSleep);
			LATCH.countDown();
			System.out.println(format("Current thread %s is waiting for LATCH", Thread.currentThread().getName()));
			LATCH.await();
			System.out.println(format("Current thread %s finished", Thread.currentThread().getName()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ExecutorService exe = Executors.newFixedThreadPool(THREAD_NUM);
		for (int i = 0; i < THREAD_NUM; i++) {
			exe.submit(new CountdownLatchExample());
		}
		exe.shutdown();
	}
}
