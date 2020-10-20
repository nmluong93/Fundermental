package concurrency.udemy.synchronize.waitandnotify;

public class MessageBean {

	private String m;

	public synchronized String getM() {
		return m;
	}

	public synchronized void setM(String m) {
		this.m = m;
	}
	
}
