package org.example.advent.of.code.day6;

import org.example.util.Direction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day6a {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day6/day6resource.txt");

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
        int initialPositionX =-1;
        int initialPositionY =-1;
        for(int i=0;i<rowLength;i++){
            for(int j=0;j<colLength;j++){
                if(bytes[i][j]==94){
                    initialPositionX =j;
                    initialPositionY =i;
                    break;
                }
            }
            if(initialPositionX!=-1){
                break;
            }
        }

        int currPositionX = initialPositionX;
        int currPositionY = initialPositionY;
        total++;
        bytes[currPositionY][currPositionX]=88;
        Direction currDirection = Direction.UP;

        while (currPositionX+currDirection.getJ()>=0 && currPositionX+currDirection.getJ()<colLength
                && currPositionY+ currDirection.getI()>=0 && currPositionY + currDirection.getI()<rowLength){
            if(bytes[currPositionY+currDirection.getI()][currPositionX+currDirection.getJ()]==35){
                //encountered obstacle turn right
                currDirection = Direction.rotateRight(currDirection);
            }else if(bytes[currPositionY+currDirection.getI()][currPositionX+currDirection.getJ()]==88){
                //already mapped no need to recount it just update current position
                currPositionX+=currDirection.getJ();
                currPositionY+=currDirection.getI();
            }else{
                //just move
                bytes[currPositionY+currDirection.getI()][currPositionX+currDirection.getJ()]=88;
                total++;
                currPositionX+=currDirection.getJ();
                currPositionY+=currDirection.getI();
            }
        }
        for(byte [] bytes1: bytes){
            System.out.println(new String(bytes1));
        }
        return total;
    }
}
