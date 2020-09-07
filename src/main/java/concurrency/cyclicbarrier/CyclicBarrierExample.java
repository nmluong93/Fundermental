package concurrency.cyclicbarrier;

import static java.lang.String.format;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierExample implements Runnable {

	private static final int NUMBER_OF_THREADS = 5;
	private static final AtomicInteger COUNTER = new AtomicInteger();
	private static final Random RANDOM = new Random(System.currentTimeMillis());
	private static final CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(NUMBER_OF_THREADS, () -> {
		COUNTER.incrementAndGet();
	});

	@Override
	public void run() {
		try {
			while (COUNTER.get() < 3) {
				int randSleep = RANDOM.nextInt();
				System.out.println(format("%s is sleeping for %s ", Thread.currentThread().getName(), randSleep));
				Thread.sleep(randSleep);
				System.out.println(format("%s is waiting for barrier", Thread.currentThread().getName()));
				CYCLIC_BARRIER.await();
				System.out.println(format("%s finished", Thread.currentThread().getName()));
			}
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ExecutorService exe = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			exe.submit(new CyclicBarrierExample());
		}
		exe.shutdown();
	}

}
