package com.leacox.toml4j;

public class StringUtils {
	public static String escapeString(String value) {
		StringBuilder unescapedStringBuilder = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			char character = value.charAt(i);
			switch (character) {
			case '\u0008':
				unescapedStringBuilder.append("\\b");
				break;
			case '\t':
				unescapedStringBuilder.append("\\t");
				break;
			case '\n':
				unescapedStringBuilder.append("\\n");
				break;
			case '\f':
				unescapedStringBuilder.append("\\f");
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
					case 'b':
						unescapedStringBuilder.append('\u0008');
						break;
					case 't':
						unescapedStringBuilder.append('\t');
						break;
					case 'n':
						unescapedStringBuilder.append('\n');
						break;
					case 'f':
						unescapedStringBuilder.append('\f');
						break;
					case 'r':
						unescapedStringBuilder.append('\r');
						break;
					case '"':
						unescapedStringBuilder.append('\"');
						break;
					case '/':
						unescapedStringBuilder.append('/');
						break;
					case '\\':
						unescapedStringBuilder.append('\\');
						break;
					case 'u':
						i++; // Advance to the start of the unicode code point.
						int codePoint = readUnicodeCodePoint(value, i);
						if (Character.isISOControl(codePoint)) {
							throw new ParseException(
									"String value contains invalid unicode characters (control characters): " + value);
						}
						unescapedStringBuilder.append(Character.toChars(codePoint));
						i += 3; // Advance to the end of the unicode code point.
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

	private static int readUnicodeCodePoint(String value, int startIndex) {
		String hexValue = value.substring(startIndex, startIndex + 4);
		return Integer.parseInt(hexValue, 16);
	}
}
