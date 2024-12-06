package org.example.advent.of.code.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day3b {
    Path filePath = Paths.get("src/main/java/org/example/advent/of/code/day3/day3resource.txt");

    /***
     * we are using regex :
     * mul\((\d{1,3}),(\d{1,3})\)
     * Explanation:
     * 1st Alternative
     * mul matches the characters mul literally
     * \( matches the character ( with index 4010 (2816 or 508) literally
     * Capturing Group (\d{1,3})
     * \d matches a digit (equivalent to [0-9])
     * {1,3} matches the previous token between 1 and 3 times, as many times as possible, giving back as needed (greedy)
     * , matches the character , with index 4410 (2C16 or 548) literally
     * \) matches the character ) with index 4110 (2916 or 518) literally
     * 2nd Alternative do\(\)
     * matches to string do() literally
     * 3rd Alternative don\'t\(\)
     * matches to string don't() literally
     */
    public int sumOfMul(){
        int totalSum = 0;
        Pattern groupPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don\\'t\\(\\)");
        Pattern numberPattern = Pattern.compile("\\d{1,3}");
        Pattern doPattern = Pattern.compile("do\\(\\)");
        Pattern notPattern = Pattern.compile("don\\'t\\(\\)");

        try{
            String content = new String(Files.readAllBytes(filePath));
            boolean notMul = false;
            boolean addTheRes = true;
            Matcher matcher = groupPattern.matcher(content);
            int res = 0;
            while (matcher.find()) {
                String group = matcher.group();
                //check for do() pattern
                Matcher doMatcher = doPattern.matcher(group);
                while (doMatcher.find()){
                    addTheRes = true;
                    notMul = true;
                }
                //check for don't() pattern
                Matcher notMatcher = notPattern.matcher(group);
                while (notMatcher.find()){
                    addTheRes =false;
                    notMul = true;
                }

                if(addTheRes && !notMul){
                    Matcher numberMatcher = numberPattern.matcher(group);
                    int num =1;
                    while (numberMatcher.find()){
                        num*=Integer.parseInt(numberMatcher.group());
                    }
                    res+=num;
                }

                notMul= false;
            }
            totalSum+=res;
        }catch (IOException e){
            System.out.println("failed to read file");
        }
        return totalSum;
    }
}
