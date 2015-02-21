package com.leacox.toml4j;

import com.leacox.toml4j.node.TomlArrayNode;
import com.leacox.toml4j.node.TomlNode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Toml {
  private final TomlNode rootNode;

  private Toml(TomlNode tomlNode) {
    this.rootNode = tomlNode;
  }

  public static Toml from(TomlNode tomlNode) {
    if (tomlNode == null) {
      throw new NullPointerException("tomlNode: null");
    }
    return new Toml(tomlNode);
  }

  public static Toml from(InputStream tomlInputStream) throws IOException {
    if (tomlInputStream == null) {
      throw new NullPointerException("tomlInputStream: null");
    }

    TomlNode tomlNode = new TomlParser().parse(tomlInputStream);
    return new Toml(tomlNode);
  }

  public static Toml from(String tomlString) throws IOException {
    if (tomlString == null) {
      throw new NullPointerException("tomlString: null");
    }

    TomlNode tomlNode = new TomlParser().parse(tomlString);
    return new Toml(tomlNode);
  }

  public void writeTo(File file) throws IOException {
    if (file == null) {
      throw new NullPointerException("file: null");
    }

    new TomlGenerator().writeTo(file, rootNode);
  }

  public void writeTo(OutputStream outputStream) throws IOException {
    if (outputStream == null) {
      throw new NullPointerException("outpuStream: null");
    }

    new TomlGenerator().writeTo(outputStream, rootNode);
  }

  public String getString(String key) {
    TomlNode stringNode = get(key);

    if (stringNode == null) {
      return null;
    }

    if (!stringNode.isString()) {
      throw new IllegalArgumentException("Matching value of key '" + key + "' is not a String");
    }

    return stringNode.stringValue();
  }

  public String getAsString(String key) {
    TomlNode valueNode = get(key);

    if (valueNode == null) {
      return null;
    }

    return valueNode.asStringValue();
  }

  public Long getLong(String key) {
    TomlNode integerNode = get(key);

    if (integerNode == null) {
      return null;
    }

    if (!integerNode.isInteger()) {
      throw new IllegalArgumentException("Matching value of key '" + key + "' is not an Integer");
    }

    return integerNode.longValue();
  }

  public Double getDouble(String key) {
    TomlNode doubleNode = get(key);

    if (doubleNode == null) {
      return null;
    }

    if (!doubleNode.isFloat()) {
      throw new IllegalArgumentException("Matching value of key '" + key + "' is not a Double");
    }

    return doubleNode.doubleValue();
  }

  public Boolean getBoolean(String key) {
    TomlNode booleanNode = get(key);

    if (booleanNode == null) {
      return null;
    }

    if (!booleanNode.isBoolean()) {
      throw new IllegalArgumentException("Matching value of key '" + key + "' is not a Boolean");
    }

    return booleanNode.booleanValue();
  }

  public Calendar getCalendar(String key) {
    TomlNode calendarNode = get(key);

    if (calendarNode == null) {
      return null;
    }

    if (!calendarNode.isDateTime()) {
      throw new IllegalArgumentException("Matching value of key '" + key + "' is not a Calendar");
    }

    return calendarNode.calendarValue();
  }

  public List<Object> getList(String key) {
    return getListOf(key, Object.class);
  }

  public <T> List<T> getListOf(String key, Class<T> type) {
    TomlNode arrayNode = get(key);

    if (arrayNode == null) {
      return null;
    }

    if (!arrayNode.isArray()) {
      throw new IllegalArgumentException("Matching value of key '" + key + "' is not an Array");
    }

    List<T> list = new ArrayList<T>(arrayNode.size());
    for (TomlNode arrayValueNode : arrayNode.children()) {
      try {
        list.add(type.cast(getValue(arrayValueNode)));
      } catch (ClassCastException e) {
        throw new IllegalArgumentException(
            "Value of key '" + key + "' does not match type '" + type + "'");
      }
    }
    return list;
  }

  public Toml getKeyGroup(String keyGroup) {
    TomlNode keyGroupNode = get(keyGroup);

    if (keyGroupNode == null) {
      return null;
    }

    if (!keyGroupNode.isHash()) {
      throw new IllegalArgumentException("Invalid keygroup: " + keyGroup);
    }

    return new Toml(keyGroupNode);
  }

  private TomlNode get(String key) {
    TomlNode foundNode = null;
    if (key.contains(".")) {
      TomlNode currentNode = rootNode;
      currentNode = rootNode;
      for (String keyPart : key.split("\\.")) {
        currentNode = currentNode.get(keyPart);
        if (currentNode == null) {
          break;
        }
      }

      foundNode = currentNode;
    } else {
      foundNode = rootNode.get(key);
    }

    return foundNode;
  }

  private List<Object> getAsList(TomlNode node) {
    TomlArrayNode arrayNode = (TomlArrayNode) node;

    List<Object> list = new ArrayList<Object>();
    for (TomlNode arrayValueNode : arrayNode.children()) {
      list.add(getValue(arrayValueNode));
    }

    return list;
  }

  private Object getValue(TomlNode valueNode) {
    Object value = null;
    switch (valueNode.getNodeType()) {
      case STRING:
        value = valueNode.stringValue();
        break;
      case INTEGER:
        value = valueNode.longValue();
        break;
      case FLOAT:
        value = valueNode.doubleValue();
        break;
      case BOOLEAN:
        value = valueNode.booleanValue();
        break;
      case DATETIME:
        value = valueNode.calendarValue();
        break;
      case ARRAY:
        value = getAsList(valueNode);
        break;
      default:
        throw new IllegalStateException(
            "Invalid value node type: '" + valueNode.getNodeType() + "'");
    }

    return value;
  }
}
