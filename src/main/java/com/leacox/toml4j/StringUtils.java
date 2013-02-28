package com.leacox.toml4j;

public class StringUtils {
	public static String escapeString(String value) {
		StringBuilder unescapedStringBuilder = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			char character = value.charAt(i);
			switch (character) {
			case '\u0000':
				unescapedStringBuilder.append("\\0");
				break;
			case '\t':
				unescapedStringBuilder.append("\\t");
				break;
			case '\n':
				unescapedStringBuilder.append("\\n");
				break;
			case '\r':
				unescapedStringBuilder.append("\\r");
				break;
			case '\"':
				unescapedStringBuilder.append("\\\"");
				break;
			case '\\':
				unescapedStringBuilder.append("\\\\");
				break;
			default:
				unescapedStringBuilder.append(character);
			}

		}

		return unescapedStringBuilder.toString();
	}

	public static String unescapeString(String value) {
		StringBuilder unescapedStringBuilder = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			char character = value.charAt(i);
			if (character == '\\') {
				i++; // Advance to character following escape character
				if (i < value.length()) {
					character = value.charAt(i);
					switch (character) {
					case '0':
						unescapedStringBuilder.append('\u0000');
						break;
					case 't':
						unescapedStringBuilder.append('\t');
						break;
					case 'n':
						unescapedStringBuilder.append('\n');
						break;
					case 'r':
						unescapedStringBuilder.append('\r');
						break;
					case '"':
						unescapedStringBuilder.append('\"');
						break;
					case '\\':
						unescapedStringBuilder.append('\\');
						break;
					default:
						throw new ParseException("String value contains an invalid escape sequence: " + value);
					}
				} else {
					throw new ParseException("String value contains an invalid escape sequence: " + value);
				}
			} else {
				unescapedStringBuilder.append(character);
			}
		}

		return unescapedStringBuilder.toString();
	}
}
