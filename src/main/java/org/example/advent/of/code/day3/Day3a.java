package org.example.advent.of.code.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3a {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day3/day3resource.txt");

    /***
     * we are using regex :
     * mul\((\d{1,3}),(\d{1,3})\)
     * Explanation:
     * mul matches the characters mul literally
     * \( matches the character ( with index 4010 (2816 or 508) literally
     * Capturing Group (\d{1,3})
     * \d matches a digit (equivalent to [0-9])
     * {1,3} matches the previous token between 1 and 3 times, as many times as possible, giving back as needed (greedy)
     * , matches the character , with index 4410 (2C16 or 548) literally
     * \) matches the character ) with index 4110 (2916 or 518) literally
     *
     */
    public int sumOfMul(){
        int totalSum = 0;
        Pattern groupPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        Pattern numberPattern = Pattern.compile("\\d{1,3}");

        try{
            String content = new String(Files.readAllBytes(filePath));
            Matcher matcher = groupPattern.matcher(content);
            int res = 0;
            while (matcher.find()) {
                String group = matcher.group();
                Matcher numberMatcher = numberPattern.matcher(group);
                int num =1;
                while (numberMatcher.find()){
                    num*=Integer.parseInt(numberMatcher.group());
                }
                res+=num;
            }
            totalSum+=res;
        }catch (IOException e){
            System.out.println("failed to read file");
        }
        return totalSum;
    }
}
