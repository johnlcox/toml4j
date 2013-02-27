package com.leacox.toml4j.node;

public class TomlStringNode extends TomlValueNode {
	private final String value;

	public TomlStringNode(String value) {
		this.value = value;
	}

	public static TomlStringNode valueOf(String value) {
		return new TomlStringNode(value);
	}

	@Override
	public TomlNodeType getNodeType() {
		return TomlNodeType.STRING;
	}

	@Override
	public String stringValue() {
		return value;
	}
}
