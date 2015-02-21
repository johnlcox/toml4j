package com.leacox.toml4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import com.leacox.toml4j.node.TomlNode;

import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

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
  public void testGetStringReturnsNullForMissingKey() throws IOException {
    String tomlString = "mystring = \"This is a string\"";

    TomlParser parser = new TomlParser();
    TomlNode tomlNode = parser.parse(tomlString);

    Toml toml = Toml.from(tomlNode);

    assertNull(toml.getString("someotherkey"));
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
  public void testGetStringWithKeyGroupReturnsNullForMissingKey() throws IOException {
    String tomlString = "[my.key.group]\nmystring = \"This is a string\"";

    TomlParser parser = new TomlParser();
    TomlNode tomlNode = parser.parse(tomlString);

    Toml toml = Toml.from(tomlNode);

    assertNull(toml.getString("some.other.key"));
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
  public void testGetLongReturnsNullForMissingKey() throws IOException {
    String tomlString = "[key.group]\nanumber = 333";

    TomlParser parser = new TomlParser();
    TomlNode tomlNode = parser.parse(tomlString);

    Toml toml = Toml.from(tomlNode);

    assertNull(toml.getLong("a.missing.key"));
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
  public void testGetDoubleReturnsNullForMissingKey() throws IOException {
    String tomlString = "floatvalue = 372.9821";

    TomlParser parser = new TomlParser();
    TomlNode tomlNode = parser.parse(tomlString);

    Toml toml = Toml.from(tomlNode);

    assertNull(toml.getDouble("missingfloatvalue"));
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
  public void testGetCalendarReturnsNullForMissingKey() throws IOException {
    String tomlString = "dob =  1979-05-27T07:32:12Z";

    TomlParser parser = new TomlParser();
    TomlNode tomlNode = parser.parse(tomlString);

    Toml toml = Toml.from(tomlNode);

    assertNull(toml.getCalendar("amissingcalendar"));
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
  public void testGetBooleanReturnsNullForMissingKey() throws IOException {
    String tomlString = "mybool = false";

    TomlParser parser = new TomlParser();
    TomlNode tomlNode = parser.parse(tomlString);

    Toml toml = Toml.from(tomlNode);

    assertNull(toml.getBoolean("notmybool"));
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

  @Test
  public void testGetListOfWithSimpleArrayReturnsNullForMissingKey() throws IOException {
    String tomlString = "array = [1, 2, 3]";

    TomlParser parser = new TomlParser();
    TomlNode tomlNode = parser.parse(tomlString);

    Toml toml = Toml.from(tomlNode);

    assertNull(toml.getListOf("missingarray", Long.class));
  }

  @Test
  public void testGetListOfList() throws IOException {
    String tomlString = "superarray = [[1, 2, 3], [\"one\", \"two\", \"three\"]]";

    Toml toml = Toml.from(tomlString);

    @SuppressWarnings("rawtypes")
    List<List> superarray = toml.getListOf("superarray", List.class);

    @SuppressWarnings("unchecked")
    List<Long> intList = superarray.get(0);
    assertEquals(3, intList.size());
    assertEquals(1, intList.get(0).longValue());
    assertEquals(2, intList.get(1).longValue());
    assertEquals(3, intList.get(2).longValue());

    @SuppressWarnings("unchecked")
    List<String> stringList = superarray.get(1);
    assertEquals(3, stringList.size());
    assertEquals("one", stringList.get(0));
    assertEquals("two", stringList.get(1));
    assertEquals("three", stringList.get(2));
  }
}
