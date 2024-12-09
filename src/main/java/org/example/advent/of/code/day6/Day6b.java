package org.example.advent.of.code.day6;

import org.example.util.Direction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.example.util.Helper.deepCopy;

public class Day6b {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day6/day6resource.txt");
    int initPositionX;
    int initPositionY;
    byte[][] bytes;

    private byte[][] fileRead(){
        List<byte[]> bytes = new ArrayList<>();
        try (Stream<String> lines = Files.lines(filePath)) {
            bytes = lines.map(String::getBytes).toList();
        } catch (IOException e) {
            System.out.println("failed to read file");
        }
        return bytes.toArray(new byte[0][]);
    }

    public byte[][] grid(){
        byte[][] gridRead = fileRead();
        this.bytes = deepCopy(gridRead);
        byte[][] grid = bytes.clone();
        int rowLength = grid.length;
        int colLength = grid[0].length;
        int initialPositionX =-1;
        int initialPositionY =-1;
        for(int i=0;i<rowLength;i++){
            for(int j=0;j<colLength;j++){
                if(grid[i][j]==94){
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
        initPositionX=initialPositionX;
        initPositionY=initialPositionY;
        gridRead[currPositionY][currPositionX]=88;
        Direction currDirection = Direction.UP;

        while (inRange(currPositionX, currDirection, colLength, currPositionY, rowLength)){
            if(gridRead[currPositionY+currDirection.getI()][currPositionX+currDirection.getJ()]==35){
                currDirection = Direction.rotateRight(currDirection);
            }else if(gridRead[currPositionY+currDirection.getI()][currPositionX+currDirection.getJ()]==88){
                currPositionX+=currDirection.getJ();
                currPositionY+=currDirection.getI();
            }else{
                gridRead[currPositionY+currDirection.getI()][currPositionX+currDirection.getJ()]=88;
                currPositionX+=currDirection.getJ();
                currPositionY+=currDirection.getI();
            }
        }
        return gridRead;
    }


    public int totalCount(){
        int total =0;
        byte [][] possiblePositions = grid();
        for(int i = 0; i< possiblePositions.length; i++){
            for(int j = 0; j<possiblePositions[0].length;j++){
                if(possiblePositions[i][j]==88){
                    if(!(i == initPositionX && j == initPositionY) && isCycle(i,j)){
                        System.out.println(i +" ; "+ j);
                        total++;
                    }
                }
            }
        }

        return total;
    }

    public boolean isCycle(int i, int j){
        byte [][] grid = deepCopy(this.bytes);
        grid[i][j]='O';

        int rowLength = grid.length;
        int colLength = grid[0].length;

        Direction slowCurrDirection = Direction.UP;
        int slowCurrPositionX =initPositionX;
        int slowCurrPositionY =initPositionY;

        Direction fastCurrDirection = Direction.UP;
        int fastCurrPositionX =initPositionX;
        int fastCurrPositionY =initPositionY;

        int sleep =2;
        while (inRange(fastCurrPositionX,fastCurrDirection,colLength,fastCurrPositionY,rowLength)){

            if(sleep%5==0){
                if(grid[slowCurrPositionY+slowCurrDirection.getI()][slowCurrPositionX+slowCurrDirection.getJ()]==35 ||grid[slowCurrPositionY+slowCurrDirection.getI()][slowCurrPositionX+slowCurrDirection.getJ()]=='O' ){
                    slowCurrDirection = Direction.rotateRight(slowCurrDirection);
                }else{
                    slowCurrPositionX+=slowCurrDirection.getJ();
                    slowCurrPositionY+=slowCurrDirection.getI();
                }
            }

            if(grid[fastCurrPositionY+fastCurrDirection.getI()][fastCurrPositionX+fastCurrDirection.getJ()]==35 || grid[fastCurrPositionY+fastCurrDirection.getI()][fastCurrPositionX+fastCurrDirection.getJ()]=='O'){
                fastCurrDirection = Direction.rotateRight(fastCurrDirection);
            }else{
                fastCurrPositionX+=fastCurrDirection.getJ();
                fastCurrPositionY+=fastCurrDirection.getI();
            }

            if(slowCurrPositionX==fastCurrPositionX && slowCurrPositionY==fastCurrPositionY && slowCurrDirection.equals(fastCurrDirection)){
                return true;
            }
            sleep++;
        }
        return false;

    }

    private boolean inRange(int slowCurrPositionX, Direction currDirection, int colLength, int slowCurrPositionY, int rowLength) {
        return slowCurrPositionX + currDirection.getJ() >= 0 && slowCurrPositionX + currDirection.getJ() < colLength
                && slowCurrPositionY + currDirection.getI() >= 0 && slowCurrPositionY + currDirection.getI() < rowLength;
    }
}
