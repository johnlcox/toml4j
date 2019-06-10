package com.leacox.toml4j.node;
import com.leacox.toml4j.TomlParser;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.Assert.assertTrue;

public class TomlNodeTest {
    @Test
    public void testIsContainerNode() throws IOException {
        InputStream exampleStream = getClass().getResourceAsStream("/example.toml");

        TomlNode rootNode = new TomlParser().parse(exampleStream);
        assertTrue(rootNode.get("products").isContainerNode());
        assertTrue(rootNode.get("clients").isContainerNode());
        assertTrue(rootNode.get("servers").isContainerNode());

    }
}
