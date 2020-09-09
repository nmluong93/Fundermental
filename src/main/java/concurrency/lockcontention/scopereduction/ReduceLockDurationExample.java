package concurrency.lockcontention.scopereduction;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReduceLockDurationExample implements Runnable {

	private static final int NUMBER_OF_THREADS = 5;
	private static final Map<String, Integer> MAP = new HashMap<String, Integer>();

	@Override
	public void run() {
//		for (int i = 0; i < 10_000; i++) {
//			synchronized (MAP) {
//				UUID randomUUID = UUID.randomUUID();
//				Integer value = Integer.valueOf(42);
//				MAP.put(randomUUID.toString(), value);
//			}
//		}
//		Thread.yield();
		for (int i = 0; i < 10_000; i++) {
			UUID randomUUID = UUID.randomUUID();
			Integer value = Integer.valueOf(42);
			synchronized (MAP) {
				MAP.put(randomUUID.toString(), value);
			}
		}
		Thread.yield();
	}

	public static void main(String[] args) throws InterruptedException {
		Thread[] threads = new Thread[NUMBER_OF_THREADS];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new ReduceLockDurationExample());
		}
		long start = System.currentTimeMillis();
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
		}
		System.out.println(System.currentTimeMillis() - start + "ms");
	}
}
