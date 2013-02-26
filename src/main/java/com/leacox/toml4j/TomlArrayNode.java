package com.leacox.toml4j;

import java.util.ArrayList;
import java.util.List;

public class TomlArrayNode extends TomlNode {
	List<TomlNode> values = new ArrayList<TomlNode>();

	@Override
	public TomlNodeType getNodeType() {
		return TomlNodeType.ARRAY;
	}

	public TomlArrayNode add(TomlNode value) {
		if (value == null) {
			throw new NullPointerException("value:null");
		}

		values.add(value);
		return this;
	}
}
