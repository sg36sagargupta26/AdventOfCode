package org.example.advent.of.code.day1;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Day1 {
    List<Integer> firstList = new ArrayList<>();
    List<Integer> secondList = new ArrayList<>();
    Path filePath = Paths.get("day1resource.txt");

    public void readFileParallely(){




        try (Stream<String> lines = Files.lines(filePath)) {
            lines.parallel().forEach(line -> {
                // Process each line
                System.out.println(Thread.currentThread().getName() + " - " + line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
