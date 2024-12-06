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

public class Day2b {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day2/day2resource.txt");

    public int safeReports(){
        AtomicInteger totalSafeReports = new AtomicInteger();
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(line -> {
                List<Integer> list =Arrays.stream(line.split(" "))
                        .mapToInt(Integer::parseInt)
                        .boxed()
                        .toList();

                if(canBeIncreasing(list)|| canBeDecreasing(list)){
                    totalSafeReports.getAndIncrement();
                }
            });
        } catch (IOException e) {
            System.out.println("failed to read file");
        }
        return totalSafeReports.get();
    }

    private boolean canBeIncreasing(List<Integer> list) {
        final int n = list.size();
        int i;

        for (i = 0; i < n - 1; ++i) {
            if (list.get(i) >= list.get(i+1) || checkAdjacentElement(list.get(i), list.get(i+1) )) {
                break;
            }
        }
        if (i == n - 1) {
            return true;
        }

        int a = list.get(i);
        // here i <= n - 2
        // option 1 remove i
        if (i == 0) {
            if(isIncreasing(list, i + 1)){
                return true;
            }
        }else if (list.get(i-1) < list.get(i+1)) {
            if(!checkAdjacentElement(list.get(i-1), list.get(i+1)) && isIncreasing(list, i + 1)){
                return true;
            }
        }
        if(i == n-2){
            return true;
        }else if (list.get(i) < list.get(i + 2)){
            return !checkAdjacentElement(list.get(i), list.get(i+2)) && isIncreasing(list, i + 2);
        }
        return false;
    }

    private boolean isIncreasing(List<Integer> list, int start) {
        for (int i = start; i < list.size() - 1; ++i) {
            if (list.get(i) >=list.get(i+1) || checkAdjacentElement(list.get(i), list.get(i+1))) {
                return false;
            }
        }
        return true;
    }


    private boolean canBeDecreasing(List<Integer> list) {
        final int n = list.size();
        int i;

        for (i = 0; i < n - 1; ++i) {
            if (list.get(i) <= list.get(i+1) || checkAdjacentElement(list.get(i), list.get(i+1) )) {
                break;
            }
        }
        if (i == n - 1) {
            return true;
        }

        int a = list.get(i);
        // here i <= n - 2
        // option 1 remove i
        if (i == 0) {
            if(isDecreasing(list, i + 1)){
                return true;
            }
        }else if (list.get(i-1) > list.get(i+1)) {
            if(!checkAdjacentElement(list.get(i-1), list.get(i+1)) && isDecreasing(list, i + 1)){
                return true;
            }
        }
        if(i == n-2){
            return true;
        }else if (list.get(i) > list.get(i + 2)){
            return !checkAdjacentElement(list.get(i), list.get(i+2)) && isDecreasing(list, i + 2);
        }
        return false;
    }

    private boolean isDecreasing(List<Integer> list, int start) {
        for (int i = start; i < list.size() - 1; ++i) {
            if (list.get(i) <=list.get(i+1) || checkAdjacentElement(list.get(i), list.get(i+1))) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAdjacentElement(int a, int b){
        return Math.abs(a - b) > 3;
    }
}
