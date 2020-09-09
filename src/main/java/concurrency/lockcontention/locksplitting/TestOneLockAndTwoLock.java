package concurrency.lockcontention.locksplitting;

import java.util.concurrent.ThreadLocalRandom;

import concurrency.lockcontention.Counter;

public class TestOneLockAndTwoLock implements Runnable {

	private Counter counter;

	public TestOneLockAndTwoLock(Counter counter) {
		super();
		this.counter = counter;
	}

	private static class OneLockCounter implements Counter {
		private long customerCount;
		private long shippingCount;

		@Override
		public synchronized void increaseCustomerCount() {
			customerCount++;

		}

		@Override
		public synchronized void increaseShippingCount() {
			shippingCount++;

		}

		@Override
		public synchronized long getCustomerCount() {
			return customerCount;
		}

		@Override
		public synchronized long getShippingCount() {
			return shippingCount;
		}
	}

	private static class TwoLockCounter implements Counter {
		private long customerCount;
		private long shippingCount;
		private Object custLock = new Object();
		private Object shippingLock = new Object();

		@Override
		public void increaseCustomerCount() {
			synchronized (custLock) {
				customerCount++;
			}

		}

		@Override
		public void increaseShippingCount() {
			synchronized (shippingLock) {
				shippingCount++;
			}

		}

		@Override
		public long getCustomerCount() {
			synchronized (custLock) {
				return customerCount;
			}
		}

		@Override
		public long getShippingCount() {
			synchronized (shippingLock) {
				return shippingCount;
			}
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < 100_000; i++) {
			if (ThreadLocalRandom.current().nextBoolean()) {
				counter.increaseCustomerCount();
			} else {
				counter.increaseShippingCount();
			}
		}
		System.err.println(Thread.currentThread().getName() + " customer : " + counter.getCustomerCount() + " \n "
				+ " Shipping :" + counter.getShippingCount());
	}

	public static void main(String[] args) throws InterruptedException {
		Thread[] ts = new Thread[5];
		for (int i = 0; i < ts.length; i++) {
			ts[i] = new Thread(new TestOneLockAndTwoLock(new OneLockCounter()));
		}
		long start = System.currentTimeMillis();
		for (int i = 0; i < ts.length; i++) {
			ts[i].start();
		}
		for (int i = 0; i < ts.length; i++) {
			ts[i].join();
		}

		System.out.println(System.currentTimeMillis() - start + "ms");
	}

}
