package org.example.advent.of.code.day12;

import org.example.util.Direction;
import org.example.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Day12b {
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
                    Set<Pair<Integer,Integer>> corners = new HashSet<>();
                    boolean [][] individualCovered = new boolean[rowLen][colLen];
                    Pair<Integer, Set<Pair<Integer,Integer>>> innerRes = formGroup(i,j,corners,individualCovered);
                    cost += innerRes.key() * innerRes.value().size();
                }
            }
        }

        return cost;
    }

    private Pair<Integer, Set<Pair<Integer,Integer>>> formGroup(int i, int j, Set<Pair<Integer, Integer>> corners, boolean[][] individualCovered) {
        int area = 1;
        covered[i][j] = true;
        individualCovered[i][j] = true;
        for(Direction direction: Direction.values()){
            if(isValidAndMatching(i,j,direction)){
                int i1 = i + direction.getI();
                int j1 = j + direction.getJ();
                if(!covered[i1][j1]){
                    var res = formGroup(i1, j1, corners, individualCovered);
                    area += res.key();
                }
                //concave points
                Direction right = Direction.rotateRight(direction);
                Direction left = Direction.rotateLeft(direction);
                addSingleCornerConcave(i, j, corners, direction, right);
                addSingleCornerConcave(i, j, corners, direction, left);
            }else{
                //convex points
                Direction right = Direction.rotateRight(direction);
                Direction left = Direction.rotateLeft(direction);
                addSingleCornerConvex(i, j, corners, direction, right, individualCovered);
                addSingleCornerConvex(i, j, corners, direction, left, individualCovered);
            }
        }
        return new Pair<>(area,corners);
    }

    private void addSingleCornerConvex(int i, int j, Set<Pair<Integer, Integer>> corners, Direction direction, Direction right, boolean[][] individualCovered) {
        if(!isValidAndMatching(i, j, right)){
            int x = direction.getI() + right.getI();
            int y = direction.getJ() + right.getJ();
            x= x==-1?0:1;
            y= y==-1?0:1;
            Pair<Integer, Integer> point = new Pair<>(i + x, j + y);
            if(corners.contains(point)){
                int nx = direction.getI() + right.getI();
                int ny = direction.getJ() + right.getJ();
                if(isValidAndMatching(i,j,nx,ny) && individualCovered[i+nx][j+ny]){
                    corners.add(new Pair<>(-1* point.key(),-1* point.value()));
                }
            }
            corners.add(point);
        }
    }
    private void addSingleCornerConcave(int i, int j, Set<Pair<Integer, Integer>> corners, Direction direction, Direction right) {
        if(isValidAndMatching(i, j, right)){
            int x = direction.getI() + right.getI();
            int y = direction.getJ() + right.getJ();
            if(!isValidAndMatching(i,j,x,y)){
                x= x==-1?0:1;
                y= y==-1?0:1;
                Pair<Integer, Integer> point = new Pair<>(i + x, j + y);
                corners.add(point);

            }
        }
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
    private boolean isValidAndMatching(int i, int j,int iDelta, int jDelta) {
        int i1 = i + iDelta;
        int j1 = j + jDelta;
        return i1 >=0
                && i1 <rowLen
                && j1 >=0
                && j1 <colLen
                && bytes[i][j]==bytes[i1][j1];

    }



}
