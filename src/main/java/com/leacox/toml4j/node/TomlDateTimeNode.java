package com.leacox.toml4j.node;

import java.util.Calendar;

public class TomlDateTimeNode extends TomlValueNode {
	private final Calendar value;

	public TomlDateTimeNode(Calendar value) {
		this.value = value;
	}

	public static TomlDateTimeNode valueOf(Calendar value) {
		return new TomlDateTimeNode(value);
	}

	@Override
	public TomlNodeType getNodeType() {
		return TomlNodeType.DATETIME;
	}

	@Override
	public Calendar calendarValue() {
		return value;
	}
}
