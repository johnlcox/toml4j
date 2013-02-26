package com.leacox.toml4j;

import static org.junit.Assert.assertEquals;

import java.util.zip.DataFormatException;

import org.junit.Test;

public class ParserTest {
	@Test
	public void test() throws DataFormatException {
		String tomlString = "[key.group]\nconnection_max = 5000";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals(5000, tomlNode.get("key").get("group").get("connection_max").longValue());
	}
}
