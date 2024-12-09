package org.example.advent.of.code.day8;

import org.example.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Day8a {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day8/day8resource.txt");
    byte[][] grid;
    Map<Byte, List<Pair<Integer,Integer>>> map = new HashMap<>();
    Set<Pair<Integer,Integer>> nodes = new HashSet<>();
    int rowLength ;
    int colLength ;


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
        addAntennas();
        for(Map.Entry<Byte,List<Pair<Integer,Integer>>> entry: map.entrySet()){
            List<Pair<Integer,Integer>> list = entry.getValue();
            addToSet(list);
        }
        return nodes.size();
    }

    private void addToSet(List<Pair<Integer, Integer>> list) {
        for(int i =0 ;i<list.size();i++){
            for(int j =i+1;j< list.size();j++){
                Pair<Integer,Integer> firstPoint = list.get(i);
                Pair<Integer,Integer> secondPoint = list.get(j);
                int deltaI = firstPoint.key()- secondPoint.key();
                int deltaJ = firstPoint.value() - secondPoint.value();
                Pair<Integer,Integer> res1 = new Pair<>(firstPoint.key()+deltaI,firstPoint.value()+deltaJ);
                Pair<Integer,Integer> res2 = new Pair<>(secondPoint.key()-deltaI,secondPoint.value()-deltaJ);
                if(isInGrid(res1)){
                    nodes.add(res1);
                }
                if(isInGrid(res2)){
                    nodes.add(res2);
                }
            }
        }
    }

    private void addAntennas(){
        byte[][] bytes = fileRead();
        this.grid = bytes;
        this.rowLength = bytes.length;
        this.colLength = bytes[0].length;
        for(int i=0;i<rowLength;i++){
            for(int j=0;j<colLength;j++){
                if (bytes[i][j] != '.') {
                    List<Pair<Integer,Integer>>list= map.getOrDefault(bytes[i][j],new ArrayList<>());
                    list.add(new Pair<>(i,j));
                    map.put(bytes[i][j],list);
                }
            }
        }
    }

    boolean isInGrid(Pair<Integer,Integer> pair) {
        return pair.key() >= 0 && pair.key() < rowLength && pair.value() >= 0 && pair.value() < colLength;
    }
}
