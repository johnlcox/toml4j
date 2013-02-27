package com.leacox.toml4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.Test;

import com.leacox.toml4j.node.TomlNode;

public class TomlTest {
	@Test
	public void testGetString() throws IOException {
		String tomlString = "mystring = \"This is a string\"";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		Toml toml = Toml.from(tomlNode);

		assertEquals("This is a string", toml.getString("mystring"));
	}

	@Test
	public void testGetStringWithKeyGroup() throws IOException {
		String tomlString = "[my.key.group]\nmystring = \"This is a string\"";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		Toml toml = Toml.from(tomlNode);

		assertEquals("This is a string", toml.getString("my.key.group.mystring"));
	}

	@Test
	public void testGetLong() throws IOException {
		String tomlString = "[key.group]\nanumber = 333";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		Toml toml = Toml.from(tomlNode);

		assertEquals(333, toml.getLong("key.group.anumber").longValue());
	}

	@Test
	public void testGetDouble() throws IOException {
		String tomlString = "floatvalue = 372.9821";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		Toml toml = Toml.from(tomlNode);

		assertEquals(372.9821, toml.getDouble("floatvalue"), 0.00001);
	}

	@Test
	public void testGetCalendar() throws IOException {
		String tomlString = "dob =  1979-05-27T07:32:12Z";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		Toml toml = Toml.from(tomlNode);

		Calendar dob = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
		dob.clear(); // Clear millis
		dob.set(1979, 4, 27, 7, 32, 12);

		Calendar tomlCalendar = toml.getCalendar("dob");

		assertEquals(dob.getTimeZone(), tomlCalendar.getTimeZone());
		assertEquals(dob.getTimeInMillis(), tomlCalendar.getTimeInMillis());
	}

	@Test
	public void testGetBoolean() throws IOException {
		String tomlString = "mybool = false";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		Toml toml = Toml.from(tomlNode);

		assertFalse(toml.getBoolean("mybool"));
	}

	@Test
	public void testGetListOfWithSimpleArray() throws IOException {
		String tomlString = "array = [1, 2, 3]";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		Toml toml = Toml.from(tomlNode);
		List<Long> list = toml.getListOf("array", Long.class);

		assertEquals(3, list.size());
		assertEquals(1, list.get(0).longValue());
		assertEquals(2, list.get(1).longValue());
		assertEquals(3, list.get(2).longValue());
	}
}
