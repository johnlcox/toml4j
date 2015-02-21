package com.leacox.toml4j.node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TomlArrayNode extends TomlNode {
  List<TomlNode> values = new ArrayList<TomlNode>();

  @Override
  public TomlNodeType getNodeType() {
    return TomlNodeType.ARRAY;
  }

  @Override
  public int size() {
    return values.size();
  }

  public TomlArrayNode add(TomlNode value) {
    if (value == null) {
      throw new NullPointerException("value:null");
    }

    values.add(value);
    return this;
  }

  @Override
  public Iterable<TomlNode> children() {
    return new Iterable<TomlNode>() {
      @Override
      public Iterator<TomlNode> iterator() {
        return values.iterator();
      }
    };
  }

  @Override
  public TomlNode get(int index) {
    return values.get(index);
  }

  @Override
  public String asStringValue() {
    return "";
  }

  @Override
  public String toString() {
    StringBuilder arrayBuilder = new StringBuilder();

    arrayBuilder.append("[");
    Iterator<TomlNode> i = values.iterator();
    for (; ; ) {
      arrayBuilder.append(i.next().toString());
      if (!i.hasNext()) {
        break;
      }
      arrayBuilder.append(", ");
    }
    arrayBuilder.append("]");

    return arrayBuilder.toString();
  }
}
