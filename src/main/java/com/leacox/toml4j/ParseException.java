package com.leacox.toml4j;

public class ParseException extends RuntimeException {
	public ParseException(String errorMessage) {
		super(errorMessage);
	}

	private static final long serialVersionUID = 5781840390746662361L;
}
