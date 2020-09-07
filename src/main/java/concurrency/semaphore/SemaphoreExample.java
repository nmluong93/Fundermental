package concurrency.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreExample implements Runnable {
	private static final Semaphore SEMAPHORE = new Semaphore(3, true);
	private static final AtomicInteger COUNTER = new AtomicInteger();
	private static final long END_MILLIS = System.currentTimeMillis() + 10_000;

	@Override
	public void run() {
		while (System.currentTimeMillis() < END_MILLIS) {
			try {
				SEMAPHORE.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int count = COUNTER.incrementAndGet();
			System.out.println(String.format("Current %s accquire lock of semaphore and counter = %s ",
					Thread.currentThread().getName(), count));
			if(count > 3) {
				throw new IllegalStateException("More than 3 threads accquired the lock");
			}
			COUNTER.decrementAndGet();
			SEMAPHORE.release();
		}
	}
	
	public static void main(String[] args) {
		ExecutorService exe = Executors.newFixedThreadPool(6);
		for (int i = 0; i < 6; i++) {
			exe.submit(new SemaphoreExample());
		}
		exe.shutdown();
	}

}
