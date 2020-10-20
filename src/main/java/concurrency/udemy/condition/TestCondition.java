package concurrency.udemy.condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCondition {

	public static void main(String[] args) {
		StringQueue stringQueu = new StringQueue(10);

		ExecutorService exe = Executors.newFixedThreadPool(2);
		exe.submit(() -> {
			try {
				for (int i = 0; i < 10; i++) {
					stringQueu.add(String.valueOf(i));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		exe.submit(() -> {
			try {
				for (int i = 0; i < 10; i++) {
					stringQueu.remove();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		exe.shutdown();
	}
}
