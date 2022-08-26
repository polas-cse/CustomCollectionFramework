package list;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<E> {

	private Object[] data;
	private int size;

	public ArrayList() {
		this(10);
	}

	public ArrayList(int initCapacity) {
		if (initCapacity > 0)
			this.data = new Object[initCapacity];
		else if (initCapacity == 0)
			this.data = new Object[0];
		else
			throw new IllegalArgumentException("Illegal Capacity: " + initCapacity);
	}

	public void add(E e) {
		ensureCapacity();
		data[size++] = e;
	}

	private void ensureCapacity() {
		if (data.length <= size) {
			int oldCapacity = data.length;
			int newCapacity = (oldCapacity * 3) / 2 + 1;
			data = Arrays.copyOf(data, newCapacity);
		}
	}

	public E get(int index) {
		if (index >= size)
			throw new ArrayIndexOutOfBoundsException(index);

		return (E) data[index];
	}

	public E set(int index, E element) {
		if (index >= size - 1)
			throw new ArrayIndexOutOfBoundsException(index);

		E oldValue = (E) data[index];
		data[index] = element;
		return oldValue;
	}

	public E remove(int index) {
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

	public Iterator<E> iterator() {
		return new Itr();
	}

	public String toString() {
		Iterator<E> it = iterator();
		if (!it.hasNext())
			return "[]";

		StringBuffer sb = new StringBuffer();
		sb.append('[');
		for (;;) {
			E e = it.next();
			sb.append(e == this ? "(this Collection) " : e);
			if (!it.hasNext())
				return sb.append(']').toString();
			sb.append(',').append(' ');
		}
	}

	private class Itr implements Iterator<E> {
		int cursor;

		public boolean hasNext() {
			return cursor != ArrayList.this.size;
		}

		@Override
		@SuppressWarnings("unchecked")
		public E next() {
			return (E) ArrayList.this.data[cursor++];
		}

		public void remove() {
			ArrayList.this.remove(cursor);
		}

	}

}
