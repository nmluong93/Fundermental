package concurrency.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

public class TestClass {

	@Test
	public void testPutOnEmptyQueueBlocks() throws InterruptedException {
		final SimpleBlockingQueue<Object> testedObj = new SimpleBlockingQueue<>();

		BlockingThread blockingThread = new BlockingThread(testedObj);
		blockingThread.start();
		Thread.sleep(5_000);
		assertThat(blockingThread.isReachedAfterGet(), is(false));
		assertThat(blockingThread.isWasInterrupted(), is(false));
		assertThat(blockingThread.isThrowableThrown(), is(false));

		testedObj.put(new Object());

		Thread.sleep(1_000);
		assertThat(blockingThread.isReachedAfterGet(), is(true));
		assertThat(blockingThread.isWasInterrupted(), is(false));
		assertThat(blockingThread.isThrowableThrown(), is(false));
		blockingThread.join();
	}

	@Test
	public void testParallelInsertionAndConsumption() throws InterruptedException, ExecutionException {
		final int num_Thread = 6;
		final SimpleBlockingQueue<Integer> testedQueue = new SimpleBlockingQueue<>();

		ExecutorService exe = Executors.newFixedThreadPool(num_Thread);
		CountDownLatch countDownLatch = new CountDownLatch(num_Thread);

		List<Future<Integer>> futurePuts = new ArrayList<>();
		for (int i = 0; i < num_Thread / 2; i++) {
			Future<Integer> futurePut = exe.submit(new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					int sum = 0;
					for (int i = 0; i < 1_000; i++) {
						int nextInt = ThreadLocalRandom.current().nextInt(100);
						testedQueue.put(nextInt);
						sum += nextInt;
					}
					countDownLatch.countDown();
					return sum;
				}
			});
			futurePuts.add(futurePut);
		}

		List<Future<Integer>> futureGets = new ArrayList<>();
		for (int i = 0; i < num_Thread / 2; i++) {
			Future<Integer> futureGet = exe.submit(new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					int sum = 0;
					for (int i = 0; i < 1_000; i++) {
						Integer val = testedQueue.get();
						sum += val;
					}
					countDownLatch.countDown();
					return sum;
				}
			});
			futureGets.add(futureGet);
		}
		countDownLatch.await();
		int sumPut = 0;
		for (Future<Integer> futurePut : futurePuts) {
			sumPut += futurePut.get();
		}
		int sumGet = 0;
		for (Future<Integer> futureGet : futureGets) {
			sumGet += futureGet.get();
		}

		assertThat(sumPut, is(sumGet));
	}
}
