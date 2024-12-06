package org.example.advent.of.code.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day4a {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day4/day4resource.txt");
    int [][] directions = {{-1,1},{-1,0},{-1,-1},
                            {0,1},        {0,-1},
                            {1,1},{1,0},{1,-1}};


    public byte[][] fileRead(){
        List<byte[]> bytes = new ArrayList<>();
        try (Stream<String> lines = Files.lines(filePath)) {
            bytes = lines.map(String::getBytes).toList();
        } catch (IOException e) {
            System.out.println("failed to read file");
        }
        return bytes.toArray(new byte[0][]);
    }

    public int totalCount(){
        byte[][] bytes = fileRead();
        int total = 0;
        int rowLength = bytes.length;
        int colLength = bytes[0].length;
        for(int i=0;i<rowLength;i++){
            for(int j=0;j<colLength;j++){
                if(bytes[i][j]=='X'){
                    for(int [] dir : directions){
                        if(i+3*dir[0]>=0 && i+3*dir[0]<rowLength && j+3*dir[1]>=0 && j+3*dir[1]<colLength ){
                            if(bytes[i+dir[0]][j+dir[1]]=='M' && bytes[i+2*dir[0]][j+2*dir[1]]=='A' && bytes[i+3*dir[0]][j+3*dir[1]]=='S'){
                                total++;
                            }
                        }
                    }
                }
            }
        }
        return total;
    }
}
