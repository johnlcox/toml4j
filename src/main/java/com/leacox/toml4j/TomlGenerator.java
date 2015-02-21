package com.leacox.toml4j;

import com.leacox.toml4j.node.TomlArrayNode;
import com.leacox.toml4j.node.TomlHashNode;
import com.leacox.toml4j.node.TomlNode;
import com.leacox.toml4j.node.TomlTableArrayNode;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

public class TomlGenerator {
  private final static Charset ENCODING = Charset.forName("UTF8");

  public void writeTo(File file, TomlNode node) throws IOException {
    OutputStream outputStream = new FileOutputStream(file);
    try {
      writeTo(outputStream, node);
    } finally {
      outputStream.flush();
      outputStream.close();
    }
  }

  public void writeTo(OutputStream outputStream, TomlNode node) throws IOException {
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
    try {
      writeTo(bufferedOutputStream, node);
    } finally {
      bufferedOutputStream.flush();
    }
  }

  private void writeTo(BufferedOutputStream outputStream, TomlNode node) throws IOException {
    switch (node.getNodeType()) {
      case STRING:
        String stringValue =
            new StringBuilder().append("\"").append(StringUtils.escapeString(node.stringValue()))
                .append("\"").toString();
        outputStream.write(stringValue.getBytes(ENCODING));
        break;
      case INTEGER:
        String longValue = String.valueOf(node.longValue());
        outputStream.write(longValue.getBytes(ENCODING));
        break;
      case FLOAT:
        String doubleValue = String.valueOf(node.doubleValue());
        outputStream.write(doubleValue.getBytes(ENCODING));
        break;
      case BOOLEAN:
        String booleanValue = String.valueOf(node.booleanValue());
        outputStream.write(booleanValue.getBytes(ENCODING));
        break;
      case DATETIME:
        String dateTimeValue = node.asStringValue();
        outputStream.write(dateTimeValue.getBytes(ENCODING));
        break;
      case ARRAY:
        writeArray(outputStream, (TomlArrayNode) node);
        break;
      case HASH:
        writeHash(outputStream, (TomlHashNode) node);
        break;
      default:
        break;
    }
  }

  private void writeArray(BufferedOutputStream outputStream, TomlArrayNode array)
      throws IOException {
    outputStream.write("[".getBytes(ENCODING));

    Iterator<TomlNode> values = array.children().iterator();
    for (; values.hasNext(); ) {
      TomlNode node = values.next();
      writeTo(outputStream, node);
      if (values.hasNext()) {
        outputStream.write(", ".getBytes(ENCODING));
      }
    }

    outputStream.write("]".getBytes(ENCODING));
  }

  private void writeHash(BufferedOutputStream outputStream, TomlHashNode hash) throws IOException {
    writeHash(outputStream, hash, (String) null);
  }

  private void writeHash(BufferedOutputStream outputStream, TomlHashNode hash, String keyGroup)
      throws IOException {
    if (keyGroup != null) {
      String keyGroupLine =
          new StringBuilder().append("[").append(keyGroup).append("]\n").toString();
      outputStream.write(keyGroupLine.getBytes(ENCODING));
    }

    Iterator<Map.Entry<String, TomlNode>> fields = hash.fields().iterator();
    for (; fields.hasNext(); ) {
      Map.Entry<String, TomlNode> entry = fields.next();
      TomlNode node = entry.getValue();
      String key = entry.getKey();
      if (node.isValueNode() || node.isArray()) {
        String keyEquals = new StringBuilder().append(key).append(" = ").toString();
        outputStream.write(keyEquals.getBytes(ENCODING));
        writeTo(outputStream, node);
      } else if (node.isHash()) {
        String childKeyGroup =
            keyGroup == null ? key : new StringBuilder(keyGroup).append(".").append(key)
                .toString();
        writeHash(outputStream, (TomlHashNode) node, childKeyGroup);
      } else if (node.isArrayOfTables()) {
        String childKeyGroup =
            keyGroup == null ? key : new StringBuilder(keyGroup).append(".").append(key)
                .toString();
        writeArrayOfTables(outputStream, (TomlTableArrayNode) node, childKeyGroup);
      }

      if (fields.hasNext()) {
        outputStream.write("\n".getBytes(ENCODING));
      }
    }
  }

  private void writeArrayOfTables(
      BufferedOutputStream outputStream, TomlTableArrayNode tableArray, String keyGroup)
      throws IOException {
    Iterator<TomlNode> children = tableArray.children().iterator();
    for (; children.hasNext(); ) {
      TomlNode node = children.next();

      if (keyGroup != null) {
        String keyGroupLine =
            new StringBuilder().append("[[").append(keyGroup).append("]]\n").toString();
        outputStream.write(keyGroupLine.getBytes(ENCODING));
      }
      writeHash(outputStream, (TomlHashNode) node);

      if (children.hasNext()) {
        outputStream.write("\n".getBytes(ENCODING));
      }
    }
  }
}
