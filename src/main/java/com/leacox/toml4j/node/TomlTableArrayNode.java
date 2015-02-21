package com.leacox.toml4j.node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author John Leacox
 */
public class TomlTableArrayNode extends TomlNode {
  List<TomlNode> values = new ArrayList<TomlNode>();

  @Override
  public TomlNodeType getNodeType() {
    return TomlNodeType.ARRAY_OF_TABLES;
  }

  @Override
  public int size() {
    return values.size();
  }

  public TomlTableArrayNode add(TomlHashNode value) {
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

  // TODO This isn't right for table arrays. Refactor.
  @Override
  public String toString() {
    StringBuilder arrayTableBuilder = new StringBuilder();

    arrayTableBuilder.append("[");
    Iterator<TomlNode> i = values.iterator();
    for (; ; ) {
      arrayTableBuilder.append(i.next().toString());
      if (!i.hasNext()) {
        break;
      }
      arrayTableBuilder.append(", ");
    }
    arrayTableBuilder.append("]");

    return arrayTableBuilder.toString();
  }
}
