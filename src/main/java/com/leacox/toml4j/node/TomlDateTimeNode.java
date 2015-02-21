package com.leacox.toml4j.node;

import com.leacox.toml4j.ParseException;

import org.joda.time.DateTime;

public class TomlDateTimeNode extends TomlValueNode {
  private final String value;

  public TomlDateTimeNode(String value) {
    try {
      parse(value);
    } catch (IllegalArgumentException e) {
      throw new ParseException("Invalid date value: " + value);
    }
    this.value = value;
  }

  public static TomlDateTimeNode valueOf(String value) {
    return new TomlDateTimeNode(value);
  }

  private DateTime parse(String value) {
    return DateTime.parse(value);
  }

  @Override
  public TomlNodeType getNodeType() {
    return TomlNodeType.DATETIME;
  }

  @Override
  public DateTime dateTimeValue() {
    return parse(value);
  }

  @Override
  public String asStringValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
