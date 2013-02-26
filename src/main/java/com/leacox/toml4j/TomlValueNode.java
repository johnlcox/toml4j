package com.leacox.toml4j;

public abstract class TomlValueNode extends TomlNode {
	@Override
	public boolean isValueNode() {
		return true;
	}
}
