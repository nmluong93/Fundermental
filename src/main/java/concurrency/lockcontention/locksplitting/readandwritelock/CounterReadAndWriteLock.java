package concurrency.lockcontention.locksplitting.readandwritelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import concurrency.lockcontention.Counter;

public class CounterReadAndWriteLock implements Counter {

	private static final ReadWriteLock REENTRANT_READ_WRITE_LOCK = new ReentrantReadWriteLock();
	private static final Lock CUST_READ_LOCK = REENTRANT_READ_WRITE_LOCK.readLock();
	private static final Lock CUST_WRITE_LOCK = REENTRANT_READ_WRITE_LOCK.writeLock();
	private static final Lock SHIPPING_READ_LOCK = REENTRANT_READ_WRITE_LOCK.readLock();
	private static final Lock SHIPPING_WRITE_LOCK = REENTRANT_READ_WRITE_LOCK.writeLock();
	
	private long custCount;
	private long shippingCount;

	@Override
	public void increaseCustomerCount() {
		CUST_WRITE_LOCK.lock();
		custCount++;
		CUST_WRITE_LOCK.unlock();
	}

	@Override
	public void increaseShippingCount() {
		SHIPPING_WRITE_LOCK.lock();
		shippingCount++;
		SHIPPING_WRITE_LOCK.unlock();
	}

	@Override
	public long getCustomerCount() {
		CUST_READ_LOCK.lock();
		long count = custCount;
		CUST_READ_LOCK.unlock();
		return count;
	}

	@Override
	public long getShippingCount() {
		SHIPPING_READ_LOCK.lock();
		long count = shippingCount;
		SHIPPING_READ_LOCK.unlock();
		return count;
	}
}
