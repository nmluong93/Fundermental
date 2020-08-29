package concurrency.atomicaccess;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

public class AtomicAccess implements Runnable {

	private static final String KEY_1 = "k1";
	private static final String KEY_2 = "k2";
	private static final String KEY_3 = "k3";
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");

	private volatile static Map<String, String> configure = new HashedMap<>();

	@Override
	public void run() {
		// atomic operation
		Map<String, String> copyConfig = configure;
		String val1 = copyConfig.get(KEY_1);
		String val2 = copyConfig.get(KEY_2);
		String val3 = copyConfig.get(KEY_3);
		if (!val1.equals(val2) && !val2.equals(val3)) {
			throw new IllegalStateException("Illegal state");
		}
		System.out.println(String.format("'%s' finished", Thread.currentThread().getName()));
	}

	static void readConfigure() {
		Map<String, String> readConfig = new HashMap<String, String>();
		java.util.Date now = new java.util.Date();
		readConfig.put(KEY_1, SDF.format(now));
		readConfig.put(KEY_2, SDF.format(now));
		readConfig.put(KEY_3, SDF.format(now));
		// atomic operation
		configure = readConfig;
	}

	public static void main(String[] args) throws InterruptedException {
		readConfigure();

		Thread readConfigureT = new Thread(() -> {
			for (int i = 0; i < 10_000; i++) {
				readConfigure();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "Read-Configure thread");

		readConfigureT.start();

		Thread[] atomicAccess = new Thread[5];
		for (int i = 0; i < atomicAccess.length; i++) {
			atomicAccess[i] = new Thread(new AtomicAccess(), "Atomic access thread " + i);
			atomicAccess[i].start();
		}

		for (int i = 0; i < atomicAccess.length; i++) {
			atomicAccess[i].join();
		}
		System.out.println(String.format("'%s': All threads have finished", Thread.currentThread().getName()));
	}

}
