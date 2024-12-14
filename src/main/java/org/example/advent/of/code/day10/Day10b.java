package org.example.advent.of.code.day10;

import org.example.util.Direction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day10b {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day10/day10resource.txt");
    byte[][] bytes = fileRead();
    int rowLen = bytes.length;
    int colLen = bytes[0].length;
    boolean [][] covered = new boolean[rowLen][colLen];
    int [][] count = new int[rowLen][colLen];
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
        int total = 0;
        for (int i =0 ;i< rowLen; i++){
            for(int j =0; j<colLen; j++){
                if(bytes[i][j]==48){
                    total += internalCount(i,j);
                }
            }
        }
        return total;
    }

    public int internalCount(int i, int j){
        if(covered[i][j]){
            return count[i][j];
        }
        if(bytes[i][j]==57){
            covered[i][j]=true;
            count[i][j] =1 ;
            return 1;
        }
        int tc =0;
        for(Direction direction : Direction.values()){
            if(isValidPoint(i,j,direction)){
                if(bytes[i+direction.getI()][j+direction.getJ()]==bytes[i][j]+1){
                    tc+=internalCount(i+direction.getI(),j+direction.getJ());
                }
            }
        }
        covered[i][j]=true;
        count[i][j]=tc;
        return tc;
    }

    private boolean isValidPoint(int i, int j, Direction direction) {
        return  i+direction.getI()>=0
                && i+direction.getI()<rowLen
                && j+direction.getJ()>=0
                && j+direction.getJ()<colLen;
    }
}
