package list;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class Vactor<E> {
	private Object[] data;
	private int size;
	private int incrementCapacity;

	public Vactor() {
		this(10);
	}

	public Vactor(int initCapacity) {
		this(initCapacity, 0);
	}

	public Vactor(int initCapacity, int incrementCapacity) {
		if (initCapacity < 0)
			throw new IllegalArgumentException("Illegal Capaciry: " + initCapacity);
		this.incrementCapacity = incrementCapacity;
		data = new Object[initCapacity];
	}

	public synchronized void add(E e) {
		ensureCapacity();
		data[size++] = e;
	}

	private void ensureCapacity() {
		if (data.length <= size) {
			int newCapacity = 0;
			if (incrementCapacity > 0)
				newCapacity = data.length + incrementCapacity;
			else
				newCapacity = data.length * 2;
			data = Arrays.copyOf(data, newCapacity);
		}
	}

	public synchronized E get(int index) {
		if (index >= size)
			throw new ArrayIndexOutOfBoundsException(index);
		return (E) data[index];
	}

	public synchronized E remove(int index) {
		if (index >= size)
			throw new ArrayIndexOutOfBoundsException(index);
		E oldValue = (E) data[index];

		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(data, index + 1, data, index, numMoved);
		data[--size] = null;
		return oldValue;
	}

	public synchronized boolean remove(E obj) {
		int i = indexOf(obj);
		if (i >= 0) {
			remove(obj);
			return true;
		}
		return false;
	}

	public synchronized int indexOf(Object o) {
		return indexOf(o, 0);
	}

	public synchronized int indexOf(Object o, int index) {
		if (o == null) {
			for (int i = index; i < size; i++)
				if (data[i] == null)
					return i;
		} else {
			for (int i = index; i < size; i++)
				if (o.equals(data[i]))
					return i;
		}
		return -1;
	}

	public Enumeration<E> elements() {
		return new Enumeration<E>() {
			int count = 0;

			public synchronized boolean hasMoreElements() {
				return count < size;
			}

			public synchronized E nextElement() {
				if (count < size)
					return (E) data[count++];

				throw new NoSuchElementException("Custom Vector Enumeration");
			}
		};
	}

}
