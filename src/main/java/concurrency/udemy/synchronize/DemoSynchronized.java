package concurrency.udemy.synchronize;

public class DemoSynchronized {

	public static void main(String[] args) {
		Counter counter = new Counter();
		for (int i = 0; i < 100; i++) {
			Thread t = new Thread(new TestSynchronized(counter));
			t.start();
		}
	}

	private static class TestSynchronized implements Runnable {
		private Counter counter;

		public TestSynchronized(Counter counter) {
			super();
			this.counter = counter;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (counter) {

					this.counter.increse();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.counter.decrease();
					System.out.println("Counter = " + this.counter.getCounter());
				}
			}
		}
	}

	private static class Counter {
		private int counter;

		public synchronized void increse() {
			counter++;
		}

		public synchronized void decrease() {
			counter--;
		}

		public synchronized int getCounter() {
			return counter;
		}

	}
}
