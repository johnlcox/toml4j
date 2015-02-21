package com.leacox.toml4j.node;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.text.ParseException;

public class TomlArrayNodeTest {
  @Test
  public void testToStringDate() throws ParseException {
    TomlArrayNode array = new TomlArrayNode();
    array.add(TomlDateTimeNode.valueOf("1979-05-27T07:32:12-08:00"));
    array.add(TomlDateTimeNode.valueOf("1980-09-27T02:47:12Z"));
    array.add(TomlDateTimeNode.valueOf("1981-02-27T20:32:00Z"));

    assertEquals(
        "[1979-05-27T07:32:12-08:00, 1980-09-27T02:47:12Z, 1981-02-27T20:32:00Z]",
        array.toString());
  }
}
