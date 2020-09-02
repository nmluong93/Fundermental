package concurrency.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorExample implements Runnable {

	private static AtomicInteger counter = new AtomicInteger();
	private final int taskId;

	public ThreadPoolExecutorExample(int taskId) {
		super();
		this.taskId = taskId;
	}

	protected int getTaskId() {
		return taskId;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Runnable> queue = new LinkedBlockingDeque<Runnable>(10);
		ThreadFactory threadFactory = new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				int count = counter.getAndIncrement();
				System.out.println("Creating new thread: " + count);
				return new Thread(r, "MyThread " + count);
			}
		};
		RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {

			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				if (r instanceof ThreadPoolExecutorExample) {
					ThreadPoolExecutorExample threadPoolExecutorExample = (ThreadPoolExecutorExample) r;
					System.err.println("Rejecting task with id " + threadPoolExecutorExample.getTaskId());
				}
			}
		};
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1, TimeUnit.SECONDS, queue, threadFactory,
				rejectedExecutionHandler);
		for (int i = 0; i < 100; i++) {
			executor.execute(new ThreadPoolExecutorExample(i));
		}
		executor.shutdown();
	}
}
