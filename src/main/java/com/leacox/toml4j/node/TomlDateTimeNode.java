package com.leacox.toml4j.node;

import com.leacox.toml4j.ISO8601;

import java.util.Calendar;

public class TomlDateTimeNode extends TomlValueNode {
  private final Calendar value;

  public TomlDateTimeNode(Calendar value) {
    this.value = value;
  }

  public static TomlDateTimeNode valueOf(Calendar value) {
    return new TomlDateTimeNode(value);
  }

  @Override
  public TomlNodeType getNodeType() {
    return TomlNodeType.DATETIME;
  }

  @Override
  public Calendar calendarValue() {
    return value;
  }

  @Override
  public String asStringValue() {
    return ISO8601.fromCalendar(value);
  }

  @Override
  public String toString() {
    return ISO8601.fromCalendar(value);
  }
}
