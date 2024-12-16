package org.example.advent.of.code.day14;

import org.example.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day14a {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day14/day14resource.txt");
    List<Pair<Integer,Integer>> positions ;
    List<Pair<Integer,Integer>> vectors;
    int rowLen;
    int colLen;
    int size;
    int time;
    int rMid;
    int cMid;

    public Day14a() {
        positions = new ArrayList<>();
        vectors = new ArrayList<>();
        addFileDetails();
        rowLen = 103;
        colLen = 101;
        time =100;
        size = positions.size();
        rMid = rowLen/2;
        cMid = colLen/2;
    }

    private void addFileDetails(){
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(this::addNumbers);
        } catch (IOException e) {
            System.out.println("failed to read file");
        }

    }

    private void addNumbers(String line) {
        Pattern pattern = Pattern.compile("-?\\d+"); // Regular expression to match one or more digits
        Matcher matcher = pattern.matcher(line);
        int a = 0, b =0 , c =0, d= 0, count = 0;
        while (matcher.find() && count<=3) {
            if(count ==0){
                a = Integer.parseInt(matcher.group());
            } else if (count ==1) {
                b = Integer.parseInt(matcher.group());
            }else if (count ==2) {
                c = Integer.parseInt(matcher.group());
            }else if (count ==3) {
                d = Integer.parseInt(matcher.group());
            }
            count++;
        }
        positions.add(new Pair<>(a,b));
        vectors.add(new Pair<>(c,d));
    }

    public int safetyFactor(){
        int topLeft =0;
        int topRight = 0;
        int bottomLeft =0;
        int bottomRight =0;
        for(int i =0;i<size;i++){
            int currY = positions.get(i).key() + (time*vectors.get(i).key());
            int currX = positions.get(i).value() + (time*vectors.get(i).value());

            currX = Math.floorMod(currX,rowLen);
            currY = Math.floorMod(currY,colLen);

            if(checkTopLeft(currX,currY)){
                topLeft++;
            } else if (checkTopRight(currX,currY)) {
                topRight++;
            } else if (checkBottomLeft(currX,currY)) {
                bottomLeft++;
            } else if (checkBottomRight(currX,currY)) {
                bottomRight++;
            }
        }
        return topLeft*topRight*bottomRight*bottomLeft;
    }

    private boolean checkTopLeft(int currX, int currY) {
        return currX>=0
                && currX<rMid
                && currY>=0
                && currY<cMid;
    }
    private boolean checkTopRight(int currX, int currY) {
        return currX>=0
                && currX>rMid
                && currY>cMid
                && currY<colLen;
    }
    private boolean checkBottomLeft(int currX, int currY) {
        return currX>rMid
                && currX<rowLen
                && currY>=0
                && currY<cMid;
    }
    private boolean checkBottomRight(int currX, int currY) {
        return currX<rMid
                && currX<rowLen
                && currY>cMid
                && currY<colLen;
    }

}
