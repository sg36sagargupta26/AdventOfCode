package org.example.advent.of.code.day11;

import org.example.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day11/day11resource.txt");
    int blinks =75;
    Map<Pair<Long,Integer>,Long> memo = new HashMap<>();


    private List<Long> fileRead(){
        String content = null;
        try{
            content= new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            System.out.println("no file found");
        }
        assert content != null;
        return Arrays.stream(content.split(" ")).mapToLong(Long::parseLong).boxed().toList();
    }

    public long totalCount(){
        List<Long> stones = fileRead();
        long total = 0;
        for(long l: stones){
            total += internalCount(l,0);
        }
        return total;
    }

    public long internalCount(long l, int blinks){
        if(blinks ==this.blinks){
            return 1;
        }
        Pair<Long,Integer> currPair = new Pair<>(l,blinks);
        if(memo.containsKey(currPair)){
            return memo.get(currPair);
        }
        String s = Long.toString(l);
        long res;
        if(l==0){
            res= internalCount(1,blinks+1);
        }else if(s.length()%2==0){
            String sa = s.substring(0, (s.length()/2));
            String sb = s.substring(s.length()/2);
            res = internalCount(Long.parseLong(sa),blinks+1)+internalCount(Long.parseLong(sb),blinks+1);
        }else{
            res = internalCount(l*2024,blinks+1);
        }
        memo.put(currPair,res);
        return res;
    }
}
