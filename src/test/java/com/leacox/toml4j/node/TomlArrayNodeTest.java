package com.leacox.toml4j.node;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Test;

import com.leacox.toml4j.ISO8601;

public class TomlArrayNodeTest {
	@Test
	public void testToStringDate() throws ParseException {
		Calendar date1 = ISO8601.toCalendar("1979-05-27T07:32:12Z");
		Calendar date2 = ISO8601.toCalendar("1980-09-27T02:47:12Z");
		Calendar date3 = ISO8601.toCalendar("1981-02-27T20:32:00Z");

		TomlArrayNode array = new TomlArrayNode();
		array.add(new TomlDateTimeNode(date1));
		array.add(new TomlDateTimeNode(date2));
		array.add(new TomlDateTimeNode(date3));

		assertEquals("[1979-05-27T07:32:12Z, 1980-09-27T02:47:12Z, 1981-02-27T20:32:00Z]", array.toString());
	}
}
