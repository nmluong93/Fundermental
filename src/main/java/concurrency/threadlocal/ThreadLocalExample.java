package concurrency.threadlocal;

import static java.lang.String.format;

/**
 * As you can see that set the {@link ThreadLocal} value in the constructor of
 * {@link ThreadLocalExample} doesn't work as expected. Since we call the
 * constructor of ThreadLocalExample in the main method (the main thread) not
 * the threads of (new Thread[5]). So we must call it in run method of each
 * thread to set the right value to {@link ThreadLocal}
 * 
 * @author luongnm
 *
 */
public class ThreadLocalExample implements Runnable {

	private static final ThreadLocal<Integer> THREAD_LOCAL_VALUE = new ThreadLocal<Integer>();
	private Integer integerValue;

	public ThreadLocalExample(Integer intVal) {
		// this won't work since it is called in main thread (main method)
		// ThreadLocalExample.THREAD_LOCAL_VALUE.set(intVal);
		integerValue = intVal;
	}

	public static void main(String[] args) throws InterruptedException {
		Thread[] threads = new Thread[5];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new ThreadLocalExample(i), "Thread " + i);
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
		}
		System.out.println("End main thread " + Thread.currentThread().getName());
	}

	@Override
	public void run() {
		THREAD_LOCAL_VALUE.set(integerValue);
		System.out.println(format("Thread '%s' having int value = %s in ThreadLocal ", Thread.currentThread().getName(),
				THREAD_LOCAL_VALUE.get()));
	}

}
