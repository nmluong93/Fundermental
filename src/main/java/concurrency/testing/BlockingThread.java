package concurrency.testing;

public class BlockingThread extends Thread {

	private SimpleBlockingQueue<?> queue;
	private boolean wasInterrupted;
	private boolean reachedAfterGet;
	private boolean throwableThrown;

	public BlockingThread(SimpleBlockingQueue<?> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			try {
				queue.get();
			} catch (InterruptedException e) {
				wasInterrupted = true;
			}
			reachedAfterGet = true;
		} catch (Throwable throwable) {
			throwableThrown = true;
		}
	}

	public boolean isWasInterrupted() {
		return wasInterrupted;
	}

	public boolean isReachedAfterGet() {
		return reachedAfterGet;
	}

	public boolean isThrowableThrown() {
		return throwableThrown;
	}

}
