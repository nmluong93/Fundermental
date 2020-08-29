package concurrency.twowaystocreatethread;

public class ExtendsThread extends Thread {

	public ExtendsThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " is executing");
	}

	public static void main(String[] args) {
		ExtendsThread test = new ExtendsThread("ABC");
		test.start();
	}
}
