package concurrency.udemy.livenessfailure.deadlock;

import java.util.Random;

public class App {

	public static void main(String[] args) throws InterruptedException {
		Thread[] ts = new Thread[5];
		for (int i = 0; i < ts.length; i++) {
			ts[i] = new DeadlockChallenge();
			ts[i].start();
		}
		for (int i = 0; i < ts.length; i++) {
			ts[i].join();
		}
		System.out.println("End");
	}

	private static class DeadlockChallenge extends Thread {

		private static Object lock1 = new Object();
		private static Object lock2 = new Object();

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				boolean bolVal = new Random().nextBoolean();
				try {
					if (bolVal) {
						doSomething1();
					} else {
						doSomething2();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void doSomething1() throws InterruptedException {
			synchronized (lock1) {
				System.out.println("Thread " + getCurrentThreadName() + "  acquired lock 1 ");
				Thread.sleep(1_000);
				System.out.println("Thread " + getCurrentThreadName() + "Acquire lock 1 ");
				Thread.sleep(1_000);
				System.err.println("Thread " + getCurrentThreadName() + " trying to acquired lock 2");
				synchronized (lock2) {
					System.out.println("Thread " + getCurrentThreadName() + "  acquired lock 2 ");
				}
			}
		}

		private void doSomething2() throws InterruptedException {
			synchronized (lock2) {
				System.out.println("Thread " + getCurrentThreadName() + "  acquired lock 2 ");
				Thread.sleep(1_000);
				System.out.println("Thread " + getCurrentThreadName() + "Acquire lock 2 ");
				Thread.sleep(1_000);
				System.err.println("Thread " + getCurrentThreadName() + " trying to acquired lock 1");
				synchronized (lock1) {
					System.out.println("Thread " + getCurrentThreadName() + "  acquired lock 1 ");
				}
			}
		}

		private String getCurrentThreadName() {
			return Thread.currentThread().getName();
		}
	}
}
