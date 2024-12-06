package org.example.advent.of.code.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Day1a {
    PriorityQueue<Integer> firstList = new PriorityQueue<>();
    PriorityQueue<Integer> secondList = new PriorityQueue<>();
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day1/day1resource.txt");

    private void addIntegersToList(){
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(line -> {
                //Both the lists are separated by 3 spaces and regex " {3}" is for that
                String [] parsedLine = line.split(" {3}");
                Integer firstInt = Integer.parseInt(parsedLine[0]);
                Integer secondInt = Integer.parseInt(parsedLine[1]);
                firstList.add(firstInt);
                secondList.add(secondInt);
            });
        } catch (IOException e) {
            System.out.println("failed to read file");
        }
    }

    public int distance(){
        addIntegersToList();
        int totalDistance =0 ;
        while (!firstList.isEmpty() && !secondList.isEmpty()){
           totalDistance += Math.abs(firstList.poll() - (int)(secondList.poll()));
        }
        return totalDistance;
    }
}
