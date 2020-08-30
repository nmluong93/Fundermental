package concurrency.waitandnotify;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SynchronizedAndWait {

	private static final Queue<Integer> queue = new ConcurrentLinkedDeque<Integer>();

	private synchronized Integer getNextInt() {
		Integer nextVal = null;
		while (nextVal == null) {
			synchronized (queue) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				nextVal = queue.poll();
			}
		}
		return nextVal;
	}

	private synchronized void putInt(Integer val) {
		synchronized (queue) {
			queue.add(val);
			queue.notify();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final SynchronizedAndWait synchronizedAndWait = new SynchronizedAndWait();
		Thread consumer = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				synchronizedAndWait.putInt(i);
			}
		}, "Put value");

		Thread producer = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				int nextInt = synchronizedAndWait.getNextInt();
				System.out.println("Next int " + nextInt);
			}
		}, "Get value");

		consumer.start();
		producer.start();
		consumer.join();
		producer.join();
	}
}
