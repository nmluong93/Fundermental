package concurrency.udemy.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockApp {

	private ReentrantLock lock = new ReentrantLock();
	private int count;

	public static void main(String[] args) {
		new ReentrantLockApp().recursiveCallS();
	}

	public void recursiveCall() {
		lock.lock();
		try {
			System.out.println("Call lock " + lock.getHoldCount() + " times");
			if (lock.getHoldCount() < 10) {
				recursiveCall();
			} 
			System.out.println("I'm locking and increase count to " + ++count);
			
		} finally {
			lock.unlock();
		}
	}
	
	public synchronized void recursiveCallS() {
		System.out.println("Count + " + ++count);
		if(count < 10) {
			recursiveCallS();
		}

	}
}
