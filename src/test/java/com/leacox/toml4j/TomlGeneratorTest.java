package com.leacox.toml4j;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.junit.Test;

import com.leacox.toml4j.node.TomlNode;

public class TomlGeneratorTest {
	@Test
	public void testWriteToToExample() throws IOException {
		InputStream exampleStream = getClass().getResourceAsStream("/example.toml");

		TomlNode rootNode = new TomlParser().parse(exampleStream);

		String expectedString = "title = \"TOML Example\"\n[owner]\nname = \"Tom Preston-Werner\"\norganization = \"GitHub\"\nbio = \"GitHub Cofounder & CEO\\nLikes tater tots and beer.\"\ndob = 1979-05-27T07:32:00Z\n[database]\nserver = \"192.168.1.1\"\nports = [8001, 8001, 8002]\nconnection_max = 5000\nenabled = true\n[servers]\n[servers.alpha]\nip = \"10.0.0.1\"\ndc = \"eqdc10\"\n[servers.beta]\nip = \"10.0.0.2\"\ndc = \"eqdc10\"\n[clients]\ndata = [[\"gamma\", \"delta\"], [1, 2]]\nhosts = [\"alpha\", \"omega\"]";

		File generatedFile = new File("generated_example.toml");
		new TomlGenerator().writeTo(generatedFile, rootNode);
		String generatedText = readFile("generated_example.toml");
		generatedFile.delete();

		assertEquals(expectedString, generatedText);
	}

	@Test
	public void testToStringHardExample() throws IOException {

		InputStream exampleStream = getClass().getResourceAsStream("/hard_example.toml");

		TomlNode rootNode = new TomlParser().parse(exampleStream);

		String expectedString = "[the]\ntest_string = \"You'll hate me after this - #\"\n[the.hard]\ntest_array = [\"] \", \" # \"]\ntest_array2 = [\"Test #11 ]proved that\", \"Experiment #9 was a success\"]\nanother_test_string = \" Same thing, but with a string #\"\nharder_test_string = \" And when \\\"'s are in the string, along with # \\\"\"\n[the.hard.bit#]\nwhat? = \"You don't think some user won't do that?\"\nmulti_line_array = [\"]\"]";

		File generatedFile = new File("generated_hard_example.toml");
		new TomlGenerator().writeTo(generatedFile, rootNode);
		String generatedText = readFile("generated_hard_example.toml");
		generatedFile.delete();

		assertEquals(expectedString, generatedText);
	}

	private static String readFile(String path) throws IOException {
		FileInputStream stream = new FileInputStream(new File(path));
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			/* Instead of using default, pass in a decoder. */
			return Charset.defaultCharset().decode(bb).toString();
		} finally {
			stream.close();
		}
	}
}
