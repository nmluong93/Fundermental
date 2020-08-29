package concurrency.twowaystocreatethread;

public class UseRunnable implements Runnable{

	public void run() {
		System.err.println("Running");
		try {
			Thread.sleep(1_000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " is executing!");
		
	}
	public static void main(String[] args) {
		Thread t = new Thread(new UseRunnable(), "Runnable thread ");
		t.start();
	}
}
