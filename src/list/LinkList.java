package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkList<E> {

	private Node<E> first;
	private Node<E> last;
	private int size;

	public LinkList() {

	}

	private static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;

		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

	public boolean add(E e) {
		addLast(e);
		return true;
	}

	public void add(int index, E element) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("" + index);

		if (index == size)
			addLast(element);
		else {
			Node<E> x = first;
			for (int i = 0; i < index; i++)
				x = x.next;
			final Node<E> pred = x.prev;
			final Node<E> newNode = new Node<>(pred, element, x);
			x.prev = newNode;

			if (pred == null)
				first = newNode;
			else
				pred.next = newNode;

			size++;
		}
	}

	public void addFirst(E e) {
		Node<E> f = first;
		Node<E> newNode = new Node<>(null, e, f);
		first = newNode;
		if (f == null)
			last = newNode;
		else
			f.prev = newNode;
		size++;
	}

	public void addLast(E e) {
		Node<E> l = first;
		Node<E> newNode = new Node<>(l, e, null);
		last = newNode;
		if (l == null)
			first = newNode;
		else
			l.next = newNode;
		size++;
	}

	public E get(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("" + index);

		Node<E> x = first;
		for (int i = 0; i < index; i++)
			x = x.next;
		return x.item;
	}

	public E getFirst(int index) {
		Node<E> f = first;
		if (f == null)
			throw new NoSuchElementException();
		return f.item;
	}

	public E getLast(int index) {
		Node<E> l = last;
		if (l == null)
			throw new NoSuchElementException();
		return l.item;
	}

	public boolean remove(Object o) {
		if (o == null) {
			for (Node<E> x = first; x != null; x = x.next) {
				if (x.next == null) {
					unlink(x);
					return true;
				}
			}
		} else {
			for (Node<E> x = first; x != null; x = x.next) {
				if (o.equals(x.item)) {
					unlink(x);
					return true;
				}
			}
		}
		return false;
	}

	E unlink(Node<E> x) {
		E element = x.item;
		Node<E> next = x.next;
		Node<E> prev = x.prev;

		if (prev == null)
			first = next;
		else {
			prev.next = next;
			x.prev = null;
		}

		if (next == null)
			last = prev;
		else {
			next.prev = prev;
			x.next = null;
		}

		x.item = null;
		size--;
		return element;
	}

	public E remove(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("" + index);

		Node<E> x = first;
		for (int i = 0; i < index; i++)
			x = x.next;
		unlink(x);
		return x.item;
	}

	public E removeFirst() {
		Node<E> f = first;
		if (f == null)
			throw new NoSuchElementException();

		E element = f.item;
		Node<E> next = f.next;
		f.item = null;
		f.next = null;
		first = next;
		if (next == null)
			last = null;
		else
			next.prev = null;

		size--;
		return element;
	}

	public E removeLast() {
		Node<E> l = last;
		if (l == null)
			throw new NoSuchElementException();

		E element = l.item;
		Node<E> prev = l.prev;
		l.item = null;
		l.prev = null;
		last = prev;
		if (prev == null)
			first = null;
		else
			prev.next = null;

		size--;
		return element;
	}

	public E reomve() {
		return removeFirst();
	}

	public E set(int index, E element) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("" + index);

		Node<E> x = first;
		for (int i = 0; i < index; i++)
			x = x.next;

		E oldValue = x.item;
		x.item = element;
		return oldValue;
	}

	public int indexOf(E o) {
		int index = 0;
		if (o == null) {
			for (Node<E> x = first; x != null; x = x.next) {
				if (x.item == null)
					return index;
				index++;
			}
		} else {
			for (Node<E> x = first; x != null; x = x.next) {
				if (x.item == null)
					return index;
				index++;
			}
		}
		return -1;
	}

	public int lastIndexOf(E o) {
		int index = size;
		if (o == null) {
			for (Node<E> x = last; x != null; x = x.prev) {
				index--;
				if (x.item == null)
					return index;
			}
		} else {
			for (Node<E> x = last; x != null; x = x.prev) {
				index--;
				if (o.equals(x.item))
					return index;
			}
		}
		return -1;
	}

	public boolean contains(E o) {
		return indexOf(o) != -1;
	}

	public Iterator<E> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<E> {
		int cursor = 0;

		@Override
		public boolean hasNext() {
			return cursor != size;
		}

		@Override
		public E next() {
			E element = LinkList.this.get(cursor);
			cursor++;
			return element;
		}

		public void remove() {
			LinkList.this.remove(cursor);
		}

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

}
