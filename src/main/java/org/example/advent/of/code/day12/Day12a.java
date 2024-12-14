package org.example.advent.of.code.day12;

import org.example.util.Direction;
import org.example.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day12a {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day12/day12resource.txt");
    byte[][] bytes = fileRead();
    int rowLen = bytes.length;
    int colLen = bytes[0].length;
    boolean [][] covered = new boolean[rowLen][colLen];

    private byte[][] fileRead(){
        List<byte[]> bytes = new ArrayList<>();
        try (Stream<String> lines = Files.lines(filePath)) {
            bytes = lines.map(String::getBytes).toList();

        } catch (IOException e) {
            System.out.println("failed to read file");
        }
        return bytes.toArray(new byte[0][]);
    }

    public int totalCost(){
        int cost = 0;
        for(int i =0; i< rowLen;i++){
            for(int j =0;j<colLen;j++){
                if(!covered[i][j]){
                    Pair<Integer,Integer> areaPerim = formGroup(i,j);
                    cost += areaPerim.key()* areaPerim.value();
                }
            }
        }

        return cost;
    }

    private Pair<Integer, Integer> formGroup(int i, int j) {
        int perimeter = 0;
        int area = 1;
        covered[i][j] = true;
        for(Direction direction: Direction.values()){
            if(isValidAndMatching(i,j,direction)){
                int i1 = i + direction.getI();
                int j1 = j + direction.getJ();
                if(!covered[i1][j1]){
                    var res = formGroup(i1, j1);
                    perimeter += res.value();
                    area += res.key();
                }

            }else{
                perimeter++;
            }
        }
        return new Pair<>(area,perimeter);
    }

    private boolean isValidAndMatching(int i, int j, Direction d) {
        int i1 = i + d.getI();
        int j1 = j + d.getJ();
        return i1 >=0
                && i1 <rowLen
                && j1 >=0
                && j1 <colLen
                && bytes[i][j]==bytes[i1][j1];

    }
}
