package com.leacox.toml4j.node;

public abstract class TomlValueNode extends TomlNode {
	@Override
	public boolean isValueNode() {
		return true;
	}

	@Override
	public TomlNode get(int index) {
		return null;
	}
}
