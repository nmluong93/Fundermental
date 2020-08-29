package concurrency.threadjoin;

import java.util.Random;

public class JoinThreadExample implements Runnable {


	@Override
	public void run() {
		try {
			Thread.sleep(10_000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(String.format("[%s] finished", Thread.currentThread().getName()));
	}

	public static void main(String[] args) throws InterruptedException {
		Thread[] ts = new Thread[5];
		for (int i = 0; i < ts.length; i++) {
			ts[i] = new Thread(new JoinThreadExample(), " joinThread" + i);
			ts[i].start();
		}
		for (int i = 0; i < ts.length; i++) {
			ts[i].join();
		}
		System.out.println(String.format("[%s] all threads have finished", Thread.currentThread().getName()));
	}
}
