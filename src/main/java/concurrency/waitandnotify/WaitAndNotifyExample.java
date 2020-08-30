package concurrency.waitandnotify;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class WaitAndNotifyExample {

	private static Queue<Integer> queue = new ConcurrentLinkedDeque<>();
	private static final long START_MILLIS = System.currentTimeMillis();

	public static class Consumer implements Runnable {

		@Override
		public void run() {
			while (System.currentTimeMillis() < (START_MILLIS + 100_000)) {
				synchronized (queue) {
					try {
						queue.wait();
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					if (!queue.isEmpty()) {
						Integer integer = queue.poll();
						System.out.println(String.format("'%s' : %s", Thread.currentThread().getName(), integer));
					}
				}
			}
		}
	}

	public static class Producer implements Runnable {

		@Override
		public void run() {
			int i = 0;
			while (System.currentTimeMillis() < (START_MILLIS + 100_000)) {
				queue.add(i++);
				synchronized (queue) {
					queue.notify();
				}
				try {
					Thread.sleep(1_0);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			synchronized (queue) {
				queue.notifyAll();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread[] consumerThreads = new Thread[5];
		for (int i = 0; i < consumerThreads.length; i++) {
			consumerThreads[i] = new Thread(new Consumer(), "Consumer - " + i);
			consumerThreads[i].start();
		}
		Thread producerThread = new Thread(new Producer(), "Producer");
		producerThread.start();
		for (int i = 0; i < consumerThreads.length; i++) {
			consumerThreads[i].join();
		}
		producerThread.join();
	}

}
