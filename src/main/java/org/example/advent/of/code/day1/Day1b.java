package org.example.advent.of.code.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day1b{
    Map<Integer,Integer> firstMap = new HashMap<>();
    Map<Integer, Integer> secondMap = new HashMap<>();
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day1/day1resource.txt");

    private void addIntegersToMap(){
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(line -> {
                //Both the lists are seperated by 3 spaces and regex " {3}" is for that
                String [] parsedLine = line.split(" {3}");
                Integer firstInt = Integer.parseInt(parsedLine[0]);
                Integer secondInt = Integer.parseInt(parsedLine[1]);
                firstMap.put(firstInt,firstMap.getOrDefault(firstInt,0)+1);
                secondMap.put(secondInt,secondMap.getOrDefault(secondInt,0)+1);

            });
        } catch (IOException e) {
            System.out.println("failed to read file");
        }
    }

    public long similarity(){
        addIntegersToMap();
        long totalSimilarity =0 ;
        for (Map.Entry<Integer, Integer> entry : firstMap.entrySet()) {
            Integer k = entry.getKey();
            Integer v = entry.getValue();
            totalSimilarity += (long) k * v * secondMap.getOrDefault(k, 0);
        }

        return totalSimilarity;
    }
}
