package org.example.advent.of.code.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Day9b {
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

    public long totalCount() {
        String input = fileRead();

        long checksum = 0L;
        int[] filesystem = new int[input.length()];
        for(int i = 0; i < filesystem.length; i++) {
            filesystem[i] = input.charAt(i) - '0';
        }


        int[] openStartIndex = new int[filesystem.length];
        openStartIndex[0] = 0;
        for(int i = 1; i < filesystem.length; i++) {
            openStartIndex[i] = openStartIndex[i - 1] + filesystem[i - 1];
        }


        for(int right = filesystem.length - 1; right >= 0; right -= 2) {
            boolean found = false;
            for (int left = 1; left < right; left += 2) {
                if (filesystem[left] >= filesystem[right]) {
                    for (int i = 0; i < filesystem[right]; i++) {
                        checksum += (long) (right / 2) * (openStartIndex[left] + i);
                    }
                    filesystem[left] -= filesystem[right];
                    openStartIndex[left] += filesystem[right];
                    found = true;
                    break;
                }
            }
            if (!found) {
                for (int i = 0; i < filesystem[right]; i++) {
                    checksum += (long) (right / 2) * (openStartIndex[right] + i);
                }
            }
        }

        return checksum;
    }



}
