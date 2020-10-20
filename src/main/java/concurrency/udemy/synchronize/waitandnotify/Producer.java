package concurrency.udemy.synchronize.waitandnotify;

public class Producer implements Runnable {

	private final MessageBean message;

	public Producer(MessageBean massage) {
		super();
		this.message = massage;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2_000);
			synchronized (message) {
				message.setM("ABC");
				message.notify();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
