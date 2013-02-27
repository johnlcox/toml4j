package com.leacox.toml4j.node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TomlHashNode extends TomlNode {
	Map<String, TomlNode> hash = new HashMap<String, TomlNode>();

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
	public Iterable<TomlNode> children() {
		return new Iterable<TomlNode>() {
			@Override
			public Iterator<TomlNode> iterator() {
				return hash.values().iterator();
			}
		};
	}
}
