package com.leacox.toml4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Test;

import com.leacox.toml4j.node.TomlNode;

public class TomlParserTest {
	@Test
	public void testStringValue() throws IOException {
		String tomlString = "mystring = \"This is a string\"";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals("This is a string", tomlNode.get("mystring").stringValue());
	}

	@Test
	public void testStringValueMultipleSpecialCharacters() throws IOException {
		String tomlString = "mystring = \"I'm a string. \\\"You can quote me\\\". Tab \\t newline \\n you get it.\"";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals("I'm a string. \"You can quote me\". Tab \t newline \n you get it.", tomlNode.get("mystring")
				.stringValue());
	}

	@Test
	public void testStringValueBackspaceCharacter() throws IOException {
		String tomlString = "mystring = \"This is a string with backspace: \\b\"";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals("This is a string with backspace: \b", tomlNode.get("mystring").stringValue());
	}

	@Test
	public void testStringValueTabCharacter() throws IOException {
		String tomlString = "mystring = \"This is a string with tab: \\t\"";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals("This is a string with tab: \t", tomlNode.get("mystring").stringValue());
	}

	@Test
	public void testStringValueLineFeedCharacter() throws IOException {
		String tomlString = "mystring = \"This is a string with linefeed: \\n\"";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals("This is a string with linefeed: \n", tomlNode.get("mystring").stringValue());
	}

	@Test
	public void testStringValueFormFeedCharacter() throws IOException {
		String tomlString = "mystring = \"This is a string with form feed: \\f\"";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals("This is a string with form feed: \f", tomlNode.get("mystring").stringValue());
	}

	@Test
	public void testStringValueCarriageReturnCharacter() throws IOException {
		String tomlString = "mystring = \"This is a string with carriage return: \\r\"";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals("This is a string with carriage return: \r", tomlNode.get("mystring").stringValue());
	}

	@Test
	public void testStringValueSlashCharacter() throws IOException {
		String tomlString = "mystring = \"This is a string with a slash (optional to escape): \\/\"";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals("This is a string with a slash (optional to escape): /", tomlNode.get("mystring").stringValue());
	}

	@Test
	public void testStringValueQuoteCharacter() throws IOException {
		String tomlString = "mystring = \"This is a string with quote: \\\"\"";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals("This is a string with quote: \"", tomlNode.get("mystring").stringValue());
	}

	@Test
	public void testStringValueBackslashCharacter() throws IOException {
		String tomlString = "mystring = \"This is a string with backslash: \\\\\"";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals("This is a string with backslash: \\", tomlNode.get("mystring").stringValue());
	}

	@Test
	public void testStringValueBasicUnicode() throws IOException {
		String tomlString = "mystring = \"I'm a string. \\\"You can quote me\\\". Name\\tJos\\u00E9\\nLocation\\tSF.\"";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals("I'm a string. \"You can quote me\". Name\tJosé\nLocation\tSF.", tomlNode.get("mystring")
				.stringValue());
	}

	@Test
	public void testIntegerValue() throws IOException {
		String tomlString = "[key.group]\nconnection_max = 5000";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals(5000, tomlNode.get("key").get("group").get("connection_max").longValue());
	}

	@Test
	public void testIntegerValueNegative() throws IOException {
		String tomlString = "[key.group]\nconnection_max = -400";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals(-400, tomlNode.get("key").get("group").get("connection_max").longValue());
	}

	@Test
	public void testFloatValue() throws IOException {
		String tomlString = "floatvalue = 372.9821";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals(372.9821, tomlNode.get("floatvalue").doubleValue(), 0.00001);
	}

	@Test
	public void testFloatValueNegative() throws IOException {
		String tomlString = "floatvalue = -372.9821";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals(-372.9821, tomlNode.get("floatvalue").doubleValue(), 0.00001);
	}

	@Test
	public void testFloatValuePi() throws IOException {
		String tomlString = "pi =  3.14159265358979323846264338327950288";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertEquals(3.14159265358979323846264338327950288, tomlNode.get("pi").doubleValue(),
				0.0000000000000000000000000000000000000000001);
	}

	@Test
	public void testDateTimeValue() throws IOException {
		String tomlString = "dob =  1979-05-27T07:32:12Z";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		Calendar dob = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
		dob.clear(); // Clear millis
		dob.set(1979, 4, 27, 7, 32, 12);

		Calendar tomlCalendar = tomlNode.get("dob").calendarValue();
		assertEquals(dob.getTimeZone(), tomlCalendar.getTimeZone());
		assertEquals(dob.getTimeInMillis(), tomlCalendar.getTimeInMillis());
	}

	@Test
	public void testBooleanValueTrue() throws IOException {
		String tomlString = "mybool = true";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertTrue(tomlNode.get("mybool").booleanValue());
	}

	@Test
	public void testBooleanValueFalse() throws IOException {
		String tomlString = "mybool = false";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		assertFalse(tomlNode.get("mybool").booleanValue());
	}

	@Test
	public void testSimpleArray() throws IOException {
		String tomlString = "array = [1, 2, 3]";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		TomlNode array = tomlNode.get("array");

		assertEquals(3, array.size());
		assertEquals(1, array.get(0).longValue());
		assertEquals(2, array.get(1).longValue());
		assertEquals(3, array.get(2).longValue());
	}

	@Test(expected = ParseException.class)
	public void testArrayWithIntegerAndFloatIsInvalid() throws IOException {
		String tomlString = "array = [1, 2.0]";

		TomlParser parser = new TomlParser();
		parser.parse(tomlString);
	}

	@Test
	public void testMultilineArray() throws IOException {
		String tomlString = "array = [\n1 , \n2,\n 3,4]";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		TomlNode array = tomlNode.get("array");

		assertEquals(4, array.size());
		assertEquals(1, array.get(0).longValue());
		assertEquals(2, array.get(1).longValue());
		assertEquals(3, array.get(2).longValue());
		assertEquals(4, array.get(3).longValue());
	}

	@Test
	public void testNestedArrays() throws IOException {
		String tomlString = "nestedarray = [[1,3, 5],[2,4],[7]]";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);

		TomlNode array = tomlNode.get("nestedarray");

		assertEquals(3, array.size());

		TomlNode arrayOne = array.get(0);
		assertEquals(3, arrayOne.size());
		assertEquals(1, arrayOne.get(0).longValue());
		assertEquals(3, arrayOne.get(1).longValue());
		assertEquals(5, arrayOne.get(2).longValue());

		TomlNode arrayTwo = array.get(1);
		assertEquals(2, arrayTwo.size());
		assertEquals(2, arrayTwo.get(0).longValue());
		assertEquals(4, arrayTwo.get(1).longValue());

		TomlNode arrayThree = array.get(2);
		assertEquals(1, arrayThree.size());
		assertEquals(7, arrayThree.get(0).longValue());
	}

	@Test
	public void testMultilineNestedArrays() throws IOException {
		String tomlString = "superarray = [\n[1,3],\n[2\n,4\n,6],[3\n,5]]";

		TomlParser parser = new TomlParser();
		TomlNode tomlNode = parser.parse(tomlString);
		TomlNode array = tomlNode.get("superarray");

		assertEquals(3, array.size());

		TomlNode arrayOne = array.get(0);
		assertEquals(2, arrayOne.size());
		assertEquals(1, arrayOne.get(0).longValue());
		assertEquals(3, arrayOne.get(1).longValue());

		TomlNode arrayTwo = array.get(1);
		assertEquals(3, arrayTwo.size());
		assertEquals(2, arrayTwo.get(0).longValue());
		assertEquals(4, arrayTwo.get(1).longValue());
		assertEquals(6, arrayTwo.get(2).longValue());

		TomlNode arrayThree = array.get(2);
		assertEquals(2, arrayThree.size());
		assertEquals(3, arrayThree.get(0).longValue());
		assertEquals(5, arrayThree.get(1).longValue());
	}

	@Test
	public void testParseThrowsParseExceptionForKeyGroupAsKey() throws IOException {
		String tomlString = "[keygroup] = 5000";

		TomlParser parser = new TomlParser();
		try {
			parser.parse(tomlString);
			fail("Expected ParseException");
		} catch (ParseException e) {
		}
	}
}
