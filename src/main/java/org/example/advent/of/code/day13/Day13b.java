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

public class Day13b {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day13/day13resource.txt");
    List<Pair<Long,Long>> buttonA ;
    List<Pair<Long,Long>> buttonB;
    List<Pair<Long,Long>> prizes;
    long size;

    public Day13b() {
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
                    prizes.add(addPrizes(line));
                }
            });
        } catch (IOException e) {
            System.out.println("failed to read file");
        }

    }

    private Pair<Long, Long> addNumbers(String line) {
        Pattern pattern = Pattern.compile("\\d+"); // Regular expression to match one or more digits
        Matcher matcher = pattern.matcher(line);
        long a = 0, b =0 , count = 0;
        while (matcher.find() && count<=1) {
            if(count ==0){
                a = Long.parseLong(matcher.group());
            } else if (count ==1) {
                b = Long.parseLong(matcher.group());
            }
            count++;
        }
        return new Pair<>(a,b);
    }

    private Pair<Long, Long> addPrizes(String line) {
        Pattern pattern = Pattern.compile("\\d+"); // Regular expression to match one or more digits
        Matcher matcher = pattern.matcher(line);
        long a = 0, b =0 , count = 0;
        while (matcher.find() && count<=1) {
            if(count ==0){
                a = Long.parseLong(matcher.group());
            } else if (count ==1) {
                b = Long.parseLong(matcher.group());
            }
            count++;
        }
        return new Pair<>(a+10000000000000L,b+10000000000000L);
    }

    public long fewestToken(){
        long total = 0;
        for(int i =0;i<size;i++){
            long a=minToken(buttonA.get(i),buttonB.get(i),prizes.get(i));
            total+=a;
        }

        return total;
    }

    private long minToken(Pair<Long, Long> buttonA, Pair<Long, Long> buttonB, Pair<Long, Long> prizes) {
        long ax = buttonA.key();
        long ay = buttonA.value();
        long bx = buttonB.key();
        long by = buttonB.value();
        long px = prizes.key();
        long py = prizes.value();
        if((ax*by - ay*bx) == 0 || (bx*ay - by*ax)==0){
            //both button displaces in same line
            long a = checkOne( ax, ay, px, py);
            long b = checkOne( bx, by, px, py);
            if(a==-1 && b==-1){
                return 0;
            }
            if(a==-1){
                return b;
            }
            if(b==-1){
                return 3*a;
            }
            return Math.min(3*a,b);


        }
        if((px*by - py*bx)%(ax*by - ay*bx)!=0 || (px*ay - py*ax)%(bx*ay - by*ax)!=0){
            return 0;
        }

        long A = (px*by - py*bx)/(ax*by - ay*bx);
        long B = (px*ay - py*ax)/(bx*ay - by*ax);
        return 3*A+B;
    }

    private long checkOne(long ax, long ay, long px, long py) {
        if(px%ax!=0 || py%ay!=0){
            return -1;
        }
        if(px/ax != py/ay){
            return -1;
        }
        return px/ax;
    }
}
