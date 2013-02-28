package com.leacox.toml4j.node;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

import com.leacox.toml4j.EmptyIterator;

public abstract class TomlNode {
	public boolean isValueNode() {
		switch (getNodeType()) {
		case STRING:
		case INTEGER:
		case FLOAT:
		case BOOLEAN:
		case DATETIME:
			return true;
		default:
			return false;
		}
	}

	public boolean isContainerNode() {
		switch (getNodeType()) {
		case HASH:
		case ARRAY:
			return true;
		default:
			return false;
		}
	}

	public boolean isArray() {
		return getNodeType() == TomlNodeType.ARRAY;
	}

	public boolean isHash() {
		return getNodeType() == TomlNodeType.HASH;
	}

	public boolean isString() {
		return getNodeType() == TomlNodeType.STRING;
	}

	public boolean isInteger() {
		return getNodeType() == TomlNodeType.INTEGER;
	}

	public boolean isFloat() {
		return getNodeType() == TomlNodeType.FLOAT;
	}

	public boolean isBoolean() {
		return getNodeType() == TomlNodeType.BOOLEAN;
	}

	public boolean isDateTime() {
		return getNodeType() == TomlNodeType.DATETIME;
	}

	public boolean booleanValue() {
		return false;
	}

	public long longValue() {
		return 0;
	}

	public double doubleValue() {
		return 0.0;
	}

	public Calendar calendarValue() {
		return null;
	}

	public String stringValue() {
		return null;
	}

	public TomlNode get(String key) {
		return null;
	}

	public boolean contains(String key) {
		return get(key) != null;
	}

	public Iterable<TomlNode> children() {
		return new Iterable<TomlNode>() {
			@Override
			public Iterator<TomlNode> iterator() {
				return EmptyIterator.getInstance();
			}
		};
	}

	public Iterable<Map.Entry<String, TomlNode>> fields() {
		return new Iterable<Map.Entry<String, TomlNode>>() {
			@Override
			public Iterator<Map.Entry<String, TomlNode>> iterator() {
				return EmptyIterator.getInstance();
			}
		};
	}

	/**
	 * Returns the number of child nodes of this node.
	 * 
	 * @return
	 */
	public int size() {
		return 0;
	}

	public abstract TomlNodeType getNodeType();

	public abstract TomlNode get(int index);

	@Override
	public abstract String toString();
}
