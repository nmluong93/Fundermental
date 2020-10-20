package concurrency.udemy.condition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StringQueue {

	private final Queue<String> queue = new LinkedList<String>();
	private final Lock lock = new ReentrantLock();
	private final Condition nonFull = lock.newCondition();
	private final Condition nonEmpty = lock.newCondition();

	private final int maxSize;

	public StringQueue(int size) {
		super();
		this.maxSize = size;
	}

	public void add(String str) throws InterruptedException {
		lock.lock();
		try {
			while (isFull()) {
				nonFull.await();
			}
			System.out.println("Adding " + str + " into queue");
			queue.add(str);

			// now it is not empty => set the nonEmpty to signal
			nonEmpty.signal();

		} finally {
			lock.unlock();
		}
	}

	public void remove() throws InterruptedException {
		lock.lock();
		try {
			while(isEmpty()) {
				nonEmpty.await();
			}
			System.out.println("Remove " + queue.remove() + " from the Queue");
			nonFull.signal();
		} finally {
			lock.unlock();
		}
	}

	public boolean isFull() {
		return queue.size() >= maxSize;
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}
}
