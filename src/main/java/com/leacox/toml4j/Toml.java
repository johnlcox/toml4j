package com.leacox.toml4j;

public interface Toml {
	public <T> T readValue(String key, Class<T> type);
}
