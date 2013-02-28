package com.leacox.toml4j.node;

public class TomlIntegerNode extends TomlValueNode {
	private final long value;

	public TomlIntegerNode(long value) {
		this.value = value;
	}

	public static TomlIntegerNode valueOf(long value) {
		return new TomlIntegerNode(value);
	}

	@Override
	public TomlNodeType getNodeType() {
		return TomlNodeType.INTEGER;
	}

	@Override
	public long longValue() {
		return value;
	}

	@Override
	public double doubleValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
