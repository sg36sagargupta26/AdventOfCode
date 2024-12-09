package org.example.advent.of.code.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class Day7b {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day7/day7resource.txt");

    public long safeReports(){
        AtomicLong totalSafeReports = new AtomicLong();
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(line -> {
                List<Long> list = Arrays.stream(line.split(" |: "))
                        .mapToLong(Long::parseLong)
                        .boxed()
                        .toList();

                if(isValid(list,list.getFirst(),1,list.size()-1)){
                    totalSafeReports.getAndAdd(list.getFirst());
                }
            });
        } catch (IOException e) {
            System.out.println("failed to read file");
        }
        return totalSafeReports.get();
    }

    private boolean isValid(List<Long> l, long target, int start, int end) {
        if (start == end && target == l.getLast()) {
            return true;
        }

        if (target < 0) {
            return false;
        }
        if (start + 1 > end) {
            return false;
        }
        //addition
        List<Long> list = new ArrayList<>(l);
        final long nextElement = list.get(start + 1);
        long add = list.get(start) + nextElement;
        list.set(start + 1, add);
        boolean isValidAdd = isValid(list, target, start + 1, end);
        long mul = list.get(start) * nextElement;
        list.set(start + 1, mul);
        boolean isValidMul = isValid(list, target, start + 1, end);
        long concat = Long.parseLong(list.get((start)) + "" + nextElement);
        list.set(start + 1, concat);
        boolean isValidConcat = isValid(list,target,start+1,end);
        return isValidAdd || isValidMul || isValidConcat;
    }

}
