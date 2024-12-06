package org.example.advent.of.code.day4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day4a {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day4/day4resource.txt");


    public int totalCount(){
        AtomicInteger totalSum = new AtomicInteger();
        Pattern xmasPattern = Pattern.compile("XMAS|SAMX");

        //Diagonal list
        AtomicReference<Set<Integer>> setX = new AtomicReference<>(new HashSet<>());
        AtomicReference<Set<Integer>> setM = new AtomicReference<>(new HashSet<>());
        AtomicReference<Set<Integer>> setA = new AtomicReference<>(new HashSet<>());
        AtomicReference<Set<Integer>> setRevM = new AtomicReference<>(new HashSet<>());
        AtomicReference<Set<Integer>> setRevA = new AtomicReference<>(new HashSet<>());
        AtomicReference<Set<Integer>> setRevS = new AtomicReference<>(new HashSet<>());

        //Vertical List
        AtomicReference<Set<Integer>> setVerticalX = new AtomicReference<>(new HashSet<>());
        AtomicReference<Set<Integer>> setVerticalM = new AtomicReference<>(new HashSet<>());
        AtomicReference<Set<Integer>> setVerticalA = new AtomicReference<>(new HashSet<>());
        AtomicReference<Set<Integer>> setRevVerticalM = new AtomicReference<>(new HashSet<>());
        AtomicReference<Set<Integer>> setRevVerticalA = new AtomicReference<>(new HashSet<>());
        AtomicReference<Set<Integer>> setRevVerticalS = new AtomicReference<>(new HashSet<>());




        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(line -> {
                horizontalCheck(line, xmasPattern, totalSum);
                byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
                Set<Integer> tempSetX = new HashSet<>();
                Set<Integer> tempSetM = new HashSet<>();
                Set<Integer> tempSetA = new HashSet<>();
                Set<Integer> tempSetRevM = new HashSet<>();
                Set<Integer> tempSetRevA = new HashSet<>();
                Set<Integer> tempSetRevS = new HashSet<>();
                //Vertical references
                Set<Integer> tempSetVerticalX = new HashSet<>();
                Set<Integer> tempSetVerticalM = new HashSet<>();
                Set<Integer> tempSetVerticalA = new HashSet<>();
                Set<Integer> tempSetRevVerticalM = new HashSet<>();
                Set<Integer> tempSetRevVerticalA = new HashSet<>();
                Set<Integer> tempSetRevVerticalS = new HashSet<>();
                int dkl=0;
                dkl++;
                for(int i =0;i< bytes.length;i++){

                    byte a = bytes[i];

                    //Diagonal counts
                    if(a=='X'){
                        diagonalOperationForX(tempSetX, i, setRevM.get(), totalSum);
                        verticalOperationForX(tempSetVerticalX,i,setRevVerticalM.get(),totalSum);
                    }else if(a=='M'){
                        diagonalOperationForM(setX.get(), i, tempSetM, setRevA.get(), tempSetRevM);
                        verticalOperationForM(setVerticalX.get(),i,tempSetM,setRevVerticalA.get(),tempSetRevVerticalM);
                    }else if(a=='A'){
                        diagonalOperationForA(setM.get(), i, tempSetA, setRevS.get(), tempSetRevA);
                        verticalOperationForA(setVerticalM.get(),i,tempSetVerticalA,setRevVerticalS.get(),tempSetRevA);
                    }else if(a=='S'){
                        diagonalOperationForS(tempSetRevS, i, setA.get(), totalSum);
                        verticalOperationForS(tempSetRevVerticalS,i,setVerticalA.get(),totalSum);
                    }
                }
                reset(setX, tempSetX, setM, tempSetM, setA, tempSetA, setRevS, tempSetRevS, setRevA, tempSetRevA, setRevM, tempSetRevM, setVerticalX, tempSetVerticalX, setVerticalM, tempSetVerticalM, setVerticalA, tempSetVerticalA, setRevVerticalS, tempSetRevVerticalS, setRevVerticalA, tempSetRevVerticalA, setRevVerticalM, tempSetRevVerticalM);


            });
        } catch (IOException e) {
            System.out.println("failed to read file");
        }
        return totalSum.get();
    }

    private void reset(AtomicReference<Set<Integer>> setX, Set<Integer> tempSetX, AtomicReference<Set<Integer>> setM, Set<Integer> tempSetM, AtomicReference<Set<Integer>> setA, Set<Integer> tempSetA, AtomicReference<Set<Integer>> setRevS, Set<Integer> tempSetRevS, AtomicReference<Set<Integer>> setRevA, Set<Integer> tempSetRevA, AtomicReference<Set<Integer>> setRevM, Set<Integer> tempSetRevM, AtomicReference<Set<Integer>> setVerticalX, Set<Integer> tempSetVerticalX, AtomicReference<Set<Integer>> setVerticalM, Set<Integer> tempSetVerticalM, AtomicReference<Set<Integer>> setVerticalA, Set<Integer> tempSetVerticalA, AtomicReference<Set<Integer>> setRevVerticalS, Set<Integer> tempSetRevVerticalS, AtomicReference<Set<Integer>> setRevVerticalA, Set<Integer> tempSetRevVerticalA, AtomicReference<Set<Integer>> setRevVerticalM, Set<Integer> tempSetRevVerticalM) {
        setX.set(tempSetX);
        setM.set(tempSetM);
        setA.set(tempSetA);

        setRevS.set(tempSetRevS);
        setRevA.set(tempSetRevA);
        setRevM.set(tempSetRevM);


        setVerticalX.set(tempSetVerticalX);
        setVerticalM.set(tempSetVerticalM);
        setVerticalA.set(tempSetVerticalA);

        setRevVerticalS.set(tempSetRevVerticalS);
        setRevVerticalA.set(tempSetRevVerticalA);
        setRevVerticalM.set(tempSetRevVerticalM);
    }


    private void horizontalCheck(String line, Pattern xmasPattern, AtomicInteger totalSum) {
        Matcher xmasMatcher = xmasPattern.matcher(line);
        while (xmasMatcher.find()){
            totalSum.getAndIncrement();
        }
    }

    private void diagonalOperationForS(Set<Integer> tempSetRevS, int i, Set<Integer> setA, AtomicInteger totalSum) {
        tempSetRevS.add(i);
        if(setA.contains(i -1)){
            totalSum.getAndIncrement();
        }
        if(setA.contains(i +1)){
            totalSum.getAndIncrement();
        }
    }

    private void verticalOperationForS(Set<Integer> tempSetRevS, int i, Set<Integer> setA, AtomicInteger totalSum) {
        tempSetRevS.add(i);
        if(setA.contains(i)){
            totalSum.getAndIncrement();
        }
    }

    private void diagonalOperationForM(Set<Integer> setX, int i, Set<Integer> tempSetM, Set<Integer> setRevA, Set<Integer> tempSetRevM) {
        if(setX.contains(i -1)|| setX.contains(i +1)){
            tempSetM.add(i);
        }
        if(setRevA.contains(i -1) || setRevA.contains(i +1)){
            tempSetRevM.add(i);
        }
    }

    private void verticalOperationForM(Set<Integer> setX, int i, Set<Integer> tempSetM, Set<Integer> setRevA, Set<Integer> tempSetRevM) {
        if(setX.contains(i)){
            tempSetM.add(i);
        }
        if(setRevA.contains(i)){
            tempSetRevM.add(i);
        }
    }

    private void diagonalOperationForA(Set<Integer> setM, int i, Set<Integer> tempSetA, Set<Integer> setRevS, Set<Integer> tempSetRevA) {
        if(setM.contains(i -1)|| setM.contains(i +1)){
            tempSetA.add(i);
        }
        if(setRevS.contains(i -1) || setRevS.contains(i +1)){
            tempSetRevA.add(i);
        }
    }

    private void verticalOperationForA(Set<Integer> setM, int i, Set<Integer> tempSetA, Set<Integer> setRevS, Set<Integer> tempSetRevA) {
        if(setM.contains(i)){
            tempSetA.add(i);
        }
        if(setRevS.contains(i)){
            tempSetRevA.add(i);
        }
    }



    private void diagonalOperationForX(Set<Integer> tempSetX, int i, Set<Integer> setRevM, AtomicInteger totalSum) {
        tempSetX.add(i);
        if(setRevM.contains(i -1)){
            totalSum.getAndIncrement();
        }
        if(setRevM.contains(i +1)){
            totalSum.getAndIncrement();
        }
    }
    private void verticalOperationForX(Set<Integer> tempSetX, int i, Set<Integer> setRevM, AtomicInteger totalSum) {
        tempSetX.add(i);
        if(setRevM.contains(i)){
            totalSum.getAndIncrement();
        }
    }
}
