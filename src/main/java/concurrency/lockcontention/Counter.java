package concurrency.lockcontention;

public interface Counter {

	void increaseCustomerCount();
	void increaseShippingCount();
	long getCustomerCount();
	long getShippingCount();
}
