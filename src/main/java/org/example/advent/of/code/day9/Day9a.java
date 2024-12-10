package org.example.advent.of.code.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day9a {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day9/day9resource.txt");
    
    private String fileRead(){
        String content = null;
        try{
            content= new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            System.out.println("no file found");
        }
        return content;
    }


    public long totalCount(){
        String content = fileRead();
        List<Integer> list =Arrays.stream(content.split(""))
                .mapToInt(Integer::parseInt)
                .boxed()
                .toList();
        List<Integer> disk = new ArrayList<>();
        int id =0;
        boolean isSpace = false;
        for(int l: list){
            if (isSpace) {
                for (int i = 0; i < l; i++) {
                    disk.add(-1);
                }
            } else {
                for (int i = 0; i < l; i++) {
                    disk.add(id);
                }
                id++;
            }

            isSpace=!isSpace;
        }

        int start =0;
        int end =disk.size()-1;
        while (start<=end){
            if(disk.get(end)==-1){
                end--;
                continue;
            }
            if(disk.get(start)!=-1){
                start++;
                continue;
            }
            disk.set(start,disk.get(end));
            disk.set(end,-1);
            start++;
            end--;
        }

        long count = 0;
        for (int index = 0; index < disk.size(); index++) {
            if(disk.get(index)!=-1){
                count += (long) index *disk.get(index);
            }

        }
        return count;

    }





}
