package concurrency.udemy.synchronize.volatiledemo;

/**
 * Volatile has happen-before constrain (the same as synchronized) for
 * visibility
 * 
 * @author Luong
 *
 */
public class VolatileDemo {

	static volatile int counter;

	public static void main(String[] args) {
		new ReadThread().start();
		new WriteThread().start();
	}

	private static class ReadThread extends Thread {

		@Override
		public void run() {
			int lastValue = VolatileDemo.counter;
			while (true) {
				if (lastValue != VolatileDemo.counter) {
					System.err.println("Got change : " + VolatileDemo.counter);
					lastValue = VolatileDemo.counter;
				}
			}
		}

	}

	private static class WriteThread extends Thread {
		@Override
		public void run() {
			while (true) {
				VolatileDemo.counter++;
				System.out.println("Increase to " + VolatileDemo.counter);
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
