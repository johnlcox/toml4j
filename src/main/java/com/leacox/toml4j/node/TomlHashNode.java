package com.leacox.toml4j.node;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TomlHashNode extends TomlNode {
	Map<String, TomlNode> hash = new LinkedHashMap<String, TomlNode>();

	@Override
	public TomlNodeType getNodeType() {
		return TomlNodeType.HASH;
	}

	@Override
	public int size() {
		return hash.size();
	}

	public TomlHashNode put(String key, long value) {
		if (key == null) {
			throw new NullPointerException("key:null");
		}

		hash.put(key, TomlIntegerNode.valueOf(value));

		return this;
	}

	public TomlHashNode put(String key, TomlNode node) {
		if (key == null) {
			throw new NullPointerException("key:null");
		}
		if (node == null) {
			throw new NullPointerException("value:null");
		}

		hash.put(key, node);

		return this;
	}

	@Override
	public TomlNode get(String key) {
		return hash.get(key);
	}

	@Override
	public TomlNode get(int index) {
		return null;
	}

	@Override
	public String asStringValue() {
		return "";
	}

	@Override
	public Iterable<TomlNode> children() {
		return new Iterable<TomlNode>() {
			@Override
			public Iterator<TomlNode> iterator() {
				return hash.values().iterator();
			}
		};
	}

	@Override
	public Iterable<Map.Entry<String, TomlNode>> fields() {
		return new Iterable<Map.Entry<String, TomlNode>>() {
			@Override
			public Iterator<Map.Entry<String, TomlNode>> iterator() {
				return hash.entrySet().iterator();
			}
		};
	}

	@Override
	public String toString() {
		StringBuilder hashBuilder = new StringBuilder();

		for (Map.Entry<String, TomlNode> entry : hash.entrySet()) {
			TomlNode node = entry.getValue();
			String key = entry.getKey();
			if (node.isValueNode() || node.isArray()) {
				hashBuilder.append(key).append(" = ").append(node.toString()).append("\n");
			} else {
				hashBuilder.append(generateKeyGroups(key, node));
			}
		}

		return hashBuilder.deleteCharAt(hashBuilder.length() - 1).toString();
	}

	private StringBuilder generateKeyGroups(String key, TomlNode node) {
		StringBuilder keyGroupBuilder = new StringBuilder();
		TomlNode currentNode = node;
		if (currentNode.isHash()) {
			keyGroupBuilder.append("[").append(key).append("]").append("\n");
			for (Map.Entry<String, TomlNode> child : node.fields()) {
				String childKey = child.getKey();
				TomlNode childNode = child.getValue();

				if (childNode.isValueNode() || childNode.isArray()) {
					keyGroupBuilder.append(childKey).append(" = ").append(childNode.toString()).append("\n");
				} else {
					keyGroupBuilder.append(generateKeyGroups(key + "." + childKey, childNode));
				}
			}
		}

		return keyGroupBuilder;
	}
}
