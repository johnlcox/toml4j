package com.leacox.toml4j;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilsTest {
	@Test
	public void testunescapeStringBasicUnicode() {
		String value = "\u00e9";

		String result = StringUtils.unescapeString(value);

		assertEquals("é", result);
	}
}
