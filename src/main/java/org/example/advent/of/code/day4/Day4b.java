package org.example.advent.of.code.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day4b {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day4/day4resource.txt");


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
        byte[][] bytes = fileRead();
        int total = 0;
        int rowLength = bytes.length;
        int colLength = bytes[0].length;
        for(int i=0;i<rowLength;i++){
            for(int j=0;j<colLength;j++){
                if(bytes[i][j]=='A'){
                    if( i+1<rowLength &&  j+1<colLength && i-1>=0 && j-1>=0){
                        if(bytes[i-1][j+1]=='M' && bytes[i-1][j-1]=='M'
                            && bytes[i+1][j+1]=='S' && bytes[i+1][j-1]=='S'){
                            /*
                             * Scenario 1:
                             *  M _ M
                             *  _ A _
                             *  S _ S
                             */
                            total++;
                        }else if(bytes[i-1][j+1]=='S' && bytes[i-1][j-1]=='S'
                                && bytes[i+1][j+1]=='M' && bytes[i+1][j-1]=='M'){
                            /*
                             * Scenario 2:
                             *  S _ S
                             *  _ A _
                             *  M _ M
                             */
                            total++;
                        }else if(bytes[i-1][j+1]=='M' && bytes[i-1][j-1]=='S'
                                && bytes[i+1][j+1]=='M' && bytes[i+1][j-1]=='S'){
                            /*
                             * Scenario 3:
                             *  M _ S
                             *  _ A _
                             *  M _ S
                             */
                            total++;
                        }else if(bytes[i-1][j+1]=='S' && bytes[i-1][j-1]=='M'
                                && bytes[i+1][j+1]=='S' && bytes[i+1][j-1]=='M'){
                            /*
                             * Scenario 4:
                             *  S _ M
                             *  _ A _
                             *  S _ M
                             */
                            total++;
                        }
                    }
                }
            }
        }
        return total;
    }
}
