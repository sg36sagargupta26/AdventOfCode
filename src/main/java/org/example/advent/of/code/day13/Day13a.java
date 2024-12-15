package org.example.advent.of.code.day13;

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

public class Day13a {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day13/day13resource.txt");
    List<Pair<Integer,Integer>> buttonA ;
    List<Pair<Integer,Integer>> buttonB;
    List<Pair<Integer,Integer>> prizes;
    int size;

    public Day13a() {
        buttonA = new ArrayList<>();
        buttonB = new ArrayList<>();
        prizes = new ArrayList<>();
        addFileDetails();
        size = buttonA.size();
    }

    private void addFileDetails(){
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(line -> {
                if(line.contains("Button A")){
                    buttonA.add(addNumbers(line));
                } else if (line.contains("Button B")) {
                    buttonB.add(addNumbers(line));
                } else if (line.contains("Prize")){
                    prizes.add(addNumbers(line));
                }
            });
        } catch (IOException e) {
            System.out.println("failed to read file");
        }

    }

    private Pair<Integer, Integer> addNumbers(String line) {
        Pattern pattern = Pattern.compile("\\d+"); // Regular expression to match one or more digits
        Matcher matcher = pattern.matcher(line);
        int a = 0, b =0 , count = 0;
        while (matcher.find() && count<=1) {
            if(count ==0){
                a = Integer.parseInt(matcher.group());
            } else if (count ==1) {
                b = Integer.parseInt(matcher.group());
            }
            count++;
        }
        return new Pair<>(a,b);
    }

    public long fewestToken(){
        long total = 0;
        for(int i =0;i<size;i++){
            int a=minToken(buttonA.get(i),buttonB.get(i),prizes.get(i));
            total+=a;
        }

        return total;
    }

    private int minToken(Pair<Integer, Integer> buttonA, Pair<Integer, Integer> buttonB, Pair<Integer, Integer> prizes) {
        int ax = buttonA.key();
        int ay = buttonA.value();
        int bx = buttonB.key();
        int by = buttonB.value();
        int px = prizes.key();
        int py = prizes.value();
        if((ax*by - ay*bx) == 0 || (bx*ay - by*ax)==0){
            //both button displaces in same line
            int a = checkOne( ax, ay, px, py);
            int b = checkOne( bx, by, px, py);
            if(a==-1 && b==-1){
                return 0;
            }
            if(a==-1){
                return b>100?0:b;
            }
            if(b==-1){
                return a>100?0:3*a;
            }
            return Math.min(3*a,b);


        }
        if((px*by - py*bx)%(ax*by - ay*bx)!=0 || (px*ay - py*ax)%(bx*ay - by*ax)!=0){
            return 0;
        }

        int A = (px*by - py*bx)/(ax*by - ay*bx);
        int B = (px*ay - py*ax)/(bx*ay - by*ax);
        if(A>100 || B>100){
            return 0;
        }
        return 3*A+B;
    }

    private int checkOne(int ax, int ay, int px, int py) {
        if(px%ax!=0 || py%ay!=0){
            return -1;
        }
        if(px/ax != py/ay){
            return -1;
        }
        return px/ax;
    }


}
