package org.example.advent.of.code.day10;

import org.example.util.Direction;
import org.example.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day10a {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day10/day10resource.txt");
    byte[][] bytes = fileRead();
    int rowLen = bytes.length;
    int colLen = bytes[0].length;
    Map<Pair<Integer,Integer>,Integer> finalResult = new HashMap<>();
    private byte[][] fileRead(){
        List<byte[]> bytes = new ArrayList<>();
        try (Stream<String> lines = Files.lines(filePath)) {
            bytes = lines.map(String::getBytes).toList();

        } catch (IOException e) {
            System.out.println("failed to read file");
        }
        return bytes.toArray(new byte[0][]);
    }

    public int totalCount(){
        for (int i =0 ;i< rowLen; i++){
            for(int j =0; j<colLen; j++){
                if(bytes[i][j]==48){
                    boolean [][] covered = new boolean[rowLen][colLen];
                    internalCount(i,j,covered);
                }
            }
        }
        int finalCount = 0;
        for(int val: finalResult.values()){
            finalCount+=val;
        }
        return finalCount;
    }

    public void internalCount(int i, int j, boolean[][] covered){
        if(covered[i][j]){
            return;
        }
        if(bytes[i][j]==57){
            covered[i][j]=true;
            Pair<Integer, Integer> currPos = new Pair<>(i,j);
            finalResult.put(currPos, finalResult.getOrDefault(currPos,0)+1);
            return;
        }
        for(Direction direction : Direction.values()){
            if(isValidPoint(i,j,direction)){
                if(bytes[i+direction.getI()][j+direction.getJ()]==bytes[i][j]+1){
                    internalCount(i+direction.getI(),j+direction.getJ(),covered);
                }
            }
        }
        covered[i][j]=true;
    }

    private boolean isValidPoint(int i, int j, Direction direction) {
        return  i+direction.getI()>=0
                && i+direction.getI()<rowLen
                && j+direction.getJ()>=0
                && j+direction.getJ()<colLen;
    }
}
