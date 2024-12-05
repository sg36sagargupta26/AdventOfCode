package org.example.advent.of.code.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day2a {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day2/day2resource.txt");

    public int safeReports(){
        AtomicInteger totalSafeReports = new AtomicInteger();
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(line -> {
                List<Integer> list =Arrays.stream(line.split(" "))
                        .mapToInt(Integer::parseInt)
                        .boxed()
                        .toList();

                if(isSafeList(list)){
                    totalSafeReports.getAndIncrement();
                }
            });
        } catch (IOException e) {
            System.out.println("failed to read file");
        }
        return totalSafeReports.get();
    }

    private boolean isSafeList(List<Integer> list) {
        int size = list.size();
        if(size<=1){
            return true;
        }
        if(size == 2){
            return checkAdjacentNumbers(list.get(0),list.get(1));
        }
        boolean increasing = list.get(0)<list.get(1);
        for(int i =1;i<size;i++){
            if(checkAdjacentNumbers(list.get(i),list.get(i-1) )){
                return false;
            }
            if(increasing  && list.get(i)<=list.get(i-1)){
                return false;
            }
            if(!increasing && list.get(i)>=list.get(i-1)){
                return false;
            }
        }
        return true;
    }

    private boolean checkAdjacentNumbers(Integer integer, Integer integer1) {
        if(Objects.equals(integer, integer1)){
            return false;
        }
        return Math.abs(integer - integer1) > 3;
    }



}
