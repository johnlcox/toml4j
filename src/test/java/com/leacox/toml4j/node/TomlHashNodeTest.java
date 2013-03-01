package com.leacox.toml4j.node;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.leacox.toml4j.TomlParser;

public class TomlHashNodeTest {
	@Test
	public void testToStringExample() throws IOException {
		InputStream exampleStream = getClass().getResourceAsStream("/example.toml");

		TomlNode rootNode = new TomlParser().parse(exampleStream);

		String expectedString = "title = \"TOML Example\"\n[owner]\nname = \"Tom Preston-Werner\"\norganization = \"GitHub\"\nbio = \"GitHub Cofounder & CEO\\nLikes tater tots and beer.\"\ndob = 1979-05-27T07:32:00Z\n[database]\nserver = \"192.168.1.1\"\nports = [8001, 8001, 8002]\nconnection_max = 5000\nenabled = true\n[servers]\n[servers.alpha]\nip = \"10.0.0.1\"\ndc = \"eqdc10\"\n[servers.beta]\nip = \"10.0.0.2\"\ndc = \"eqdc10\"\n[clients]\ndata = [[\"gamma\", \"delta\"], [1, 2]]\nhosts = [\"alpha\", \"omega\"]";

		assertEquals(expectedString, rootNode.toString());
	}

	@Test
	public void testToStringHardExample() throws IOException {

		InputStream exampleStream = getClass().getResourceAsStream("/hard_example.toml");

		TomlNode rootNode = new TomlParser().parse(exampleStream);

		String expectedString = "[the]\ntest_string = \"You'll hate me after this - #\"\n[the.hard]\ntest_array = [\"] \", \" # \"]\ntest_array2 = [\"Test #11 ]proved that\", \"Experiment #9 was a success\"]\nanother_test_string = \" Same thing, but with a string #\"\nharder_test_string = \" And when \\\"'s are in the string, along with # \\\"\"\n[the.hard.bit#]\nwhat? = \"You don't think some user won't do that?\"\nmulti_line_array = [\"]\"]";

		assertEquals(expectedString, rootNode.toString());
	}
}
