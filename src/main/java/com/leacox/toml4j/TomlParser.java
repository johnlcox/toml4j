package com.leacox.toml4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class TomlParser {
	private static final Matcher commentMatcher = Pattern.compile("#.*").matcher("");
	private static final Matcher keyGroupExpressionMatcher = Pattern.compile("\\[(\\w+(\\.\\w+)*)]").matcher("");
	private static final Matcher valueExpressionMatcher = Pattern.compile("([^\\s]+)\\s*=(.+)").matcher("");

	private static final Matcher integerValueMatcher = Pattern.compile("(\\d+)").matcher("");

	public TomlNode parse(String tomlString) throws DataFormatException {
		TomlHashNode rootNode = new TomlHashNode();
		// TODO: Need TomlContainerNode
		TomlHashNode currentNode = rootNode;
		for (String line : tomlString.split("\n")) {
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

				currentNode.put(key, parseValue(value));
			}
		}

		return rootNode;
	}

	private TomlNode parseValue(String value) throws DataFormatException {
		if (integerValueMatcher.reset(value).matches()) {
			return TomlIntegerNode.valueOf(Long.valueOf(value));
		} else {
			throw new DataFormatException("Invalid value: " + value);
		}
	}

	private void stripCommentAndWhitespace(String line) {
		commentMatcher.reset(line).replaceAll("").trim();
	}
}
