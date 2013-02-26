package com.leacox.toml4j;

import java.util.Iterator;

public class EmptyIterator<T> implements Iterator<T> {
	private static final EmptyIterator<Object> INSTANCE = new EmptyIterator<Object>();

	private EmptyIterator() {
	}

	@SuppressWarnings("unchecked")
	public static <T> EmptyIterator<T> getInstance() {
		return (EmptyIterator<T>) INSTANCE;
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public T next() {
		return null;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
