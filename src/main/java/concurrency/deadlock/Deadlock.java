package concurrency.deadlock;

import static java.lang.String.format;

import java.util.Random;

public class Deadlock implements Runnable {

	private static final Object obj1 = new Object();
	private static final Object obj2 = new Object();
	private final Random random = new Random(System.currentTimeMillis());

	public static void main(String[] args) {
		Thread t1 = new Thread(new Deadlock(), "DeadLock1");
		Thread t2 = new Thread(new Deadlock(), "DeadLock2");
		t1.start();
		t2.start();
	}

	@Override
	public void run() {
		for (int i = 0; i < 10_000; i++) {
			boolean b = random.nextBoolean();
			if (b) {
				System.out.println(format("'%s' is trying to lock resource 1", Thread.currentThread().getName()));
				synchronized (obj1) {
					System.out.println(format("'%s' is locking resource 1", Thread.currentThread().getName()));
					System.out.println(format("'%s' is trying to lock resource 2", Thread.currentThread().getName()));
					synchronized (obj2) {
						System.out.println(format("'%s' is locking resource 2", Thread.currentThread().getName()));
					}
				}
			}

			else {
				System.out.println(format("'%s' is trying to lock resource 2", Thread.currentThread().getName()));
				synchronized (obj2) {
					System.out.println(format("'%s' is locking resource 2", Thread.currentThread().getName()));
					System.out.println(format("'%s' is trying to lock resource 1", Thread.currentThread().getName()));
					synchronized (obj1) {
						System.out.println(format("'%s' is locking resource 1", Thread.currentThread().getName()));
					}
				}
			}
		}
	}

}
