package com.leacox.toml4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.leacox.toml4j.node.TomlNode;

public class Toml {
	private final TomlNode rootNode;

	private Toml(TomlNode tomlNode) {
		this.rootNode = tomlNode;
	}

	public static Toml from(TomlNode tomlNode) {
		if (tomlNode == null) {
			throw new NullPointerException("tomlNode: null");
		}
		return new Toml(tomlNode);
	}

	public static Toml from(InputStream tomlInputStream) throws IOException {
		if (tomlInputStream == null) {
			throw new NullPointerException("tomlInputStream: null");
		}

		TomlNode tomlNode = new TomlParser().parse(tomlInputStream);
		return new Toml(tomlNode);
	}

	public static Toml from(String tomlString) throws IOException {
		if (tomlString == null) {
			throw new NullPointerException("tomlString: null");
		}

		TomlNode tomlNode = new TomlParser().parse(tomlString);
		return new Toml(tomlNode);
	}

	public String getString(String key) {
		TomlNode stringNode = get(key);
		if (!stringNode.isString()) {
			throw new IllegalArgumentException("Matching value of key '" + key + "' is not a String");
		}

		return stringNode.stringValue();
	}

	public Long getLong(String key) {
		TomlNode integerNode = get(key);
		if (!integerNode.isInteger()) {
			throw new IllegalArgumentException("Matching value of key '" + key + "' is not an Integer");
		}

		return integerNode.longValue();
	}

	public Double getDouble(String key) {
		TomlNode doubleNode = get(key);
		if (!doubleNode.isFloat()) {
			throw new IllegalArgumentException("Matching value of key '" + key + "' is not a Double");
		}

		return doubleNode.doubleValue();
	}

	public Boolean getBoolean(String key) {
		TomlNode booleanNode = get(key);
		if (!booleanNode.isBoolean()) {
			throw new IllegalArgumentException("Matching value of key '" + key + "' is not a Boolean");
		}

		return booleanNode.booleanValue();
	}

	public Calendar getCalendar(String key) {
		TomlNode calendarNode = get(key);
		if (!calendarNode.isDateTime()) {
			throw new IllegalArgumentException("Matching value of key '" + key + "' is not a Calendar");
		}

		return calendarNode.calendarValue();
	}

	// TODO: This won't support nested lists. Probably need something like
	// guavas TypeToken
	public <T> List<T> getListOf(String key, Class<T> type) {
		TomlNode arrayNode = get(key);
		if (!arrayNode.isArray()) {
			throw new IllegalArgumentException("Matching value of key '" + key + "' is not an Array");
		}

		List<T> list = new ArrayList<T>(arrayNode.size());
		for (TomlNode arrayValueNode : arrayNode.children()) {
			Object value = null;
			switch (arrayValueNode.getNodeType()) {
			case STRING:
				value = arrayValueNode.stringValue();
				break;
			case INTEGER:
				value = arrayValueNode.longValue();
				break;
			case FLOAT:
				value = arrayValueNode.doubleValue();
				break;
			case BOOLEAN:
				value = arrayValueNode.booleanValue();
				break;
			case DATETIME:
				value = arrayValueNode.calendarValue();
				break;
			default:
				throw new IllegalArgumentException("Value of key '" + key + "' does not match type '" + type + "'");
			}
			try {
				list.add(type.cast(value));
			} catch (ClassCastException e) {
				throw new IllegalArgumentException("Value of key '" + key + "' does not match type '" + type + "'");
			}

		}
		return list;
	}

	private TomlNode get(String key) {
		TomlNode foundNode = null;
		if (key.contains(".")) {
			TomlNode currentNode = rootNode;
			currentNode = rootNode;
			for (String keyPart : key.split("\\.")) {
				currentNode = currentNode.get(keyPart);
				if (currentNode == null) {
					break;
				}
			}

			foundNode = currentNode;
		} else {
			foundNode = rootNode.get(key);
		}

		if (foundNode == null) {
			throw new IllegalArgumentException("Unknown key");
		}

		return foundNode;
	}

	// public <T> T readValue(String key, Class<T> type);
}
