package com.leacox.toml4j;

import static org.junit.Assert.assertEquals;

import com.leacox.toml4j.node.TomlNode;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class TomlGeneratorTest {
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

  @Test
  public void testWriteToToExample() throws IOException {
    InputStream exampleStream = getClass().getResourceAsStream("/example.toml");

    TomlNode rootNode = new TomlParser().parse(exampleStream);

    String expectedString =
        "title = \"TOML Example\"\n"
            + "[owner]\n"
            + "name = \"Tom Preston-Werner\"\n"
            + "organization = \"GitHub\"\n"
            + "bio = \"GitHub Cofounder & CEO\\nLikes tater tots and beer.\"\n"
            + "dob = 1979-05-27T07:32:00Z\n"
            + "[database]\n"
            + "server = \"192.168.1.1\"\n"
            + "ports = [8001, 8001, 8002]\n"
            + "connection_max = 5000\n"
            + "enabled = true\n"
            + "[servers]\n"
            + "[servers.alpha]\n"
            + "ip = \"10.0.0.1\"\n"
            + "dc = \"eqdc10\"\n"
            + "[servers.beta]\n"
            + "ip = \"10.0.0.2\"\n"
            + "dc = \"eqdc10\"\n"
            + "country = \"中国\"\n"
            + "[clients]\n"
            + "data = [[\"gamma\", \"delta\"], [1, 2]]\n"
            + "hosts = [\"alpha\", \"omega\"]\n"
            + "[[products]]\n"
            + "name = \"Hammer\"\n"
            + "sku = 738594937\n"
            + "[[products]]\n"
            + "name = \"Nail\"\n"
            + "sku = 284758393\n"
            + "color = \"gray\"";
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

    String expectedString =
        "[the]\n"
            + "test_string = \"You'll hate me after this - #\"\n"
            + "[the.hard]\n"
            + "test_array = [\"] \", \" # \"]\n"
            + "test_array2 = [\"Test #11 ]proved that\", \"Experiment #9 was a success\"]\n"
            + "another_test_string = \" Same thing, but with a string #\"\n"
            + "harder_test_string = \" And when \\\"'s are in the string, along with # \\\"\"\n"
            + "[the.hard.\"bit#\"]\n"
            + "\"what?\" = \"You don't think some user won't do that?\"\n"
            + "multi_line_array = [\"]\"]";

    File generatedFile = new File("generated_hard_example.toml");
    new TomlGenerator().writeTo(generatedFile, rootNode);
    String generatedText = readFile("generated_hard_example.toml");
    generatedFile.delete();

    assertEquals(expectedString, generatedText);
  }
}
