package telran.multithreading.util;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLinkedBlockingQueue<E> implements BlockingQueue<E> {
	LinkedList<E> list = new LinkedList<>();
	int limit = Integer.MAX_VALUE;
	
	private Lock lock = new ReentrantLock();
	private Condition consumerWaiting = lock.newCondition();
	private Condition producerWaiting = lock.newCondition();

	
	public MyLinkedBlockingQueue(int limit) {
		this.limit = limit;
	}

	public MyLinkedBlockingQueue() {
		this(Integer.MAX_VALUE);
	}

	

	@Override
	public E remove() {
		try {
			lock.lock();
			if (list.size() == 0) {
				throw new NoSuchElementException();
			}
			E res = list.remove(0);
			producerWaiting.signal();
			return res;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E poll() {
		E res = null;
		try {
			lock.lock();
			if (list.size() != 0) {
				res = list.remove(0);
				producerWaiting.signal();
			}

			return res;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E element() {
		try {
			lock.lock();
			if (list.size() == 0) {
				throw new NoSuchElementException();
			}
			return list.get(0);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E peek() {
		E res = null;
		try {
			lock.lock();
			if (list.size() != 0) {
				res = list.get(0);
				producerWaiting.signal();
			}
			return res;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public int size() {
		try {
			lock.lock();
			return list.size();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean isEmpty() {
		try {
			lock.lock();
			return list.isEmpty();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Iterator<E> iterator() {
		try {
			lock.lock();
			return list.iterator();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Object[] toArray() {
		try {
			lock.lock();
			return list.toArray();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		try {
			lock.lock();
			return list.toArray(a);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		try {
			lock.lock();
			return list.containsAll(c);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {		
		try {
			lock.lock();	
		int size = size();
			c.forEach(t -> {
				try {
					put(t);
				} catch (InterruptedException e) {
					
				}
			});
			return size != size();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		try {
			lock.lock();
			boolean res = list.removeAll(c);
			if (res) {
				producerWaiting.signal();
			}
			return res;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		try {
			lock.lock();
			boolean res = list.retainAll(c);
			if (res) {
				producerWaiting.signal();
			}
			return res;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void clear() {
		try {
			lock.lock();
			list.clear();
			producerWaiting.signal();
		} finally {
			lock.unlock();
		}

	}

	@Override
	public boolean add(E e) {
		try {
			lock.lock();
			if (list.size() == limit) {
				throw new IllegalStateException();
			}
			boolean res = list.add(e);
			consumerWaiting.signal();
			return res;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean offer(E e) {
		boolean res = true;
		try {
			lock.lock();
			if (list.size() == limit) {
				res = false;
			} else {
				list.add(e);
				consumerWaiting.signal();
			}

			return res;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void put(E e) throws InterruptedException {
		try {
			lock.lock();
			while (list.size() == limit) {
				producerWaiting.await();
			}
			list.add(e);
			consumerWaiting.signal();

		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		try {
			lock.lock();
			while (list.size() == limit) {
				if (!producerWaiting.await(timeout, unit)) {
					return false;
				}
			}
			list.add(e);
			consumerWaiting.signal();
			return true;

		} finally {
			lock.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		try {
			lock.lock();
			while (list.isEmpty()) {
				consumerWaiting.await();
			}
			E res = list.remove(0);
			producerWaiting.signal();
			return res;

		} finally {
			lock.unlock();
		}
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		try {
			lock.lock();
			while (list.isEmpty()) {
				if (!consumerWaiting.await(timeout, unit)) {
					return null;
				}
			}
			E res = list.remove(0);
			if (res != null) {
				producerWaiting.signal();
			}

			return res;

		} finally {
			lock.unlock();
		}
	}

	@Override
	public int remainingCapacity() {
		try {
			lock.lock();
			return limit - list.size();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		try {
			lock.lock();
			boolean res = list.remove(o);
			if (res) {
				producerWaiting.signal();
			}
			return res;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean contains(Object o) {
		try {
			lock.lock();
			return list.contains(o);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		throw new UnsupportedOperationException();
	}

}
