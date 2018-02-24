package io;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class LoadDataTest {

    @Test
    public void testLoadData() {

        try (Stream<String> lines = Files.lines(Paths.get("src/test/resources/multiple-peaks.txt"))) {
            List<String> expected = lines.collect(Collectors.toList());

            assertEquals(64, expected.size());

        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }

    }

}
