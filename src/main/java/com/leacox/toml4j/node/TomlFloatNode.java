package com.leacox.toml4j.node;

public class TomlFloatNode extends TomlValueNode {
  private final double value;

  public TomlFloatNode(double value) {
    this.value = value;
  }

  public static TomlFloatNode valueOf(double value) {
    return new TomlFloatNode(value);
  }

  @Override
  public TomlNodeType getNodeType() {
    return TomlNodeType.FLOAT;
  }

  @Override
  public long longValue() {
    return (long) value;
  }

  @Override
  public double doubleValue() {
    return value;
  }

  @Override
  public String asStringValue() {
    return String.valueOf(value);
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
