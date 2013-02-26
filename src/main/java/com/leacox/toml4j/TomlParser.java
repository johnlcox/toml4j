package com.leacox.toml4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class TomlParser {
	private static final Matcher commentMatcher = Pattern.compile("#.*").matcher("");
	private static final Matcher keyGroupExpressionMatcher = Pattern.compile("\\[(\\w+(\\.\\w+)*)]").matcher("");
	private static final Matcher valueExpressionMatcher = Pattern.compile("([^\\s]+)\\s*=(.+)").matcher("");

	private static final Matcher integerValueMatcher = Pattern.compile("(\\d+)").matcher("");

	private static final Matcher arrayValueMatcher = Pattern.compile("\\[(.*)\\]").matcher("");

	public TomlNode parse(String tomlString) throws DataFormatException, IOException {
		TomlHashNode rootNode = new TomlHashNode();
		// TODO: Need TomlContainerNode
		TomlHashNode currentNode = rootNode;
		BufferedReader reader = new BufferedReader(new StringReader(tomlString));
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				stripCommentAndWhitespace(line);

				if (keyGroupExpressionMatcher.reset(line).matches()) {
					String keyGroupPath = keyGroupExpressionMatcher.group(1);
					currentNode = rootNode;
					for (String keyGroup : keyGroupPath.split("\\.")) {
						TomlNode existingNode = currentNode.get(keyGroup);
						if (existingNode != null && !existingNode.isHash()) {
							throw new DataFormatException("Duplicate key found: " + keyGroupPath);
						}

						if (existingNode == null) {
							existingNode = new TomlHashNode();
							currentNode.put(keyGroup, existingNode);
						}

						currentNode = (TomlHashNode) existingNode;
					}
				}

				if (valueExpressionMatcher.reset(line).matches()) {
					String key = valueExpressionMatcher.group(1);
					String value = valueExpressionMatcher.group(2).trim();

					if (currentNode.contains(key)) {
						throw new DataFormatException("Duplicate key found");
					}

					// Parse out whole multiline array
					if (value.startsWith("[") && !value.endsWith("]")) {
						value = parseMultilineArray(value, reader);
					}

					currentNode.put(key, parseValue(value));
				}
			}

			return rootNode;
		} finally {
			reader.close();
		}
	}

	private String parseMultilineArray(String firstLine, BufferedReader reader) throws DataFormatException, IOException {
		StringBuilder singleLineArrayBuilder = new StringBuilder(firstLine);
		boolean arrayEndingFound = false;
		String line;
		while ((line = reader.readLine()) != null) {
			singleLineArrayBuilder.append(line);

			if (line.endsWith("]")) {
				arrayEndingFound = true;
				break;
			}
		}

		if (!arrayEndingFound) {
			throw new DataFormatException("Unclosed array");
		}

		return singleLineArrayBuilder.toString();
	}

	private TomlNode parseValue(String value) throws DataFormatException {
		if (integerValueMatcher.reset(value).matches()) {
			return TomlIntegerNode.valueOf(Long.valueOf(value));
		} else if (arrayValueMatcher.reset(value).matches()) {
			return parseArrayValue(value);
		} else {
			throw new DataFormatException("Invalid value: " + value);
		}
	}

	private TomlNode parseArrayValue(String value) throws DataFormatException {
		// Remove surrounding brackets '[' and ']'
		value = value.substring(1, value.length() - 1);
		TomlArrayNode arrayNode = new TomlArrayNode();
		if (value.matches(".*(?:\\]),.*")) { // Nested arrays
			// Split with lookbehind to keep brackets
			for (String nestedValue : value.split("(?<=(?:\\])),")) {
				TomlNode nestedArrayNode = parseValue(nestedValue.trim());
				arrayNode.add(nestedArrayNode);
			}
		} else {
			for (String arrayValue : value.split("\\,")) {
				TomlNode arrayValueNode = parseValue(arrayValue.trim());
				arrayNode.add(arrayValueNode);
			}
		}

		return arrayNode;
	}

	private void stripCommentAndWhitespace(String line) {
		commentMatcher.reset(line).replaceAll("").trim();
	}
}
