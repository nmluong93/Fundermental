package concurrency.udemy.synchronize.waitandnotify;

public class Consumer implements Runnable {

	private final MessageBean message;

	public Consumer(MessageBean massage) {
		super();
		this.message = massage;
	}

	@Override
	public void run() {
		synchronized (message) {
			if (message.getM() == null) {
				try {
					message.wait();
					System.out.println("Message from Consumer gained: " + message.getM());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
