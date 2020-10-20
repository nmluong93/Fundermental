package concurrency.udemy.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;


public class App {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		testRunnable();
	}
	
	
	static void task() throws InterruptedException, ExecutionException {
		int numberOfThread = 8;
		int maxNum = 1000;
		
		ExecutorService exe = Executors.newFixedThreadPool(numberOfThread);
		Future<Integer>[] futures = new Future[numberOfThread];
		
		for (int i = 0; i < numberOfThread; i++) {
			futures[i] = exe.submit(() -> ThreadLocalRandom.current().nextInt(maxNum));
		}
		
		for (int i = 0; i < futures.length; i++) {
			System.out.println(futures[i].get());
		}
		exe.shutdown();
	}
	
	static void testRunnable() {
		int numberOfThread = 8;
		int maxNum = 1000;
		
		ExecutorService exe = Executors.newFixedThreadPool(numberOfThread);
		
		for (int i = 0; i < numberOfThread; i++) {
			exe.submit(() -> System.out.println(ThreadLocalRandom.current().nextInt(maxNum)));
		}
		
		exe.shutdown();
	}
}
