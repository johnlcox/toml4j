package com.leacox.toml4j.node;

public class TomlBooleanNode extends TomlValueNode {
  private final boolean value;

  public TomlBooleanNode(boolean value) {
    this.value = value;
  }

  public static TomlBooleanNode valueOf(boolean value) {
    return new TomlBooleanNode(value);
  }

  @Override
  public TomlNodeType getNodeType() {
    return TomlNodeType.BOOLEAN;
  }

  @Override
  public boolean booleanValue() {
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
