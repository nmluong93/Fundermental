package concurrency.executors;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorsExample implements Callable<String> {

	private static Random rand = new Random(System.currentTimeMillis());
	@Override
	public String call() throws Exception {
		Thread.sleep(1_000);
		return Thread.currentThread().getName() + " " + String.valueOf(rand.nextInt(100));
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(500);
		Future<String>[] futures = new Future[500];
		for (int i = 0; i < futures.length; i++) {
			futures[i] = executor.submit(new ExecutorsExample());
		}
		for (int i = 0; i < futures.length; i++) {
			String intVal = futures[i].get();
			System.err.println(intVal);
		}
		executor.shutdown();
	}
}
