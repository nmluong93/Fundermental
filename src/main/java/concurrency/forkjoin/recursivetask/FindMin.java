package concurrency.forkjoin.recursivetask;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FindMin extends RecursiveTask<Integer> {

	private static final long serialVersionUID = -4642674031962482438L;
	private int[] numbers;
	private int startIndex;
	private int endIndex;

	public FindMin(int[] numbers, int startIndex, int endIndex) {
		this.numbers = numbers;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	@Override
	protected Integer compute() {
		int sliceLength = Math.abs(startIndex - endIndex) + 1;
		if (sliceLength > 2) {
			FindMin lowerFindMin = new FindMin(numbers, startIndex, startIndex + (sliceLength / 2) - 1);
			lowerFindMin.fork();
			FindMin upperFindMin = new FindMin(numbers, startIndex + (sliceLength / 2), endIndex);
			upperFindMin.fork();
			return Math.min(lowerFindMin.join(), upperFindMin.join());
		}
		return Math.min(numbers[startIndex], numbers[endIndex]);
	}

	public static void main(String[] args) {
		int[] numbers = new int[100];
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = random.nextInt(100);
		}
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		Integer min = pool.invoke(new FindMin(numbers, 0, numbers.length - 1));
		System.out.println(min);
	}
}
