package com.leacox.toml4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.zip.DataFormatException;

import org.junit.Test;

public class ParserTest {
	@Test
	public void test() throws DataFormatException, IOException {
		String tomlString = "[key.group]\nconnection_max = 5000";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals(5000, tomlNode.get("key").get("group").get("connection_max").longValue());
	}

	@Test
	public void testSimpleArray() throws DataFormatException, IOException {
		String tomlString = "array = [1, 2, 3]";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertTrue(true);
	}

	@Test
	public void testMultilineArray() throws DataFormatException, IOException {
		String tomlString = "array = [\n1 , \n2,\n 3]";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertTrue(true);
	}

	@Test
	public void testNestedArrays() throws DataFormatException, IOException {
		String tomlString = "superarray = [[1,3],[2,4],[3,5]]";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertTrue(true);
	}

	@Test
	public void testMultilineNestedArrays() throws DataFormatException, IOException {
		String tomlString = "superarray = [\n[1,3],\n[2\n,4\n],[3\n,5]]";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertTrue(true);
	}
}
