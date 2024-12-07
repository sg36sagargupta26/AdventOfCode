package org.example.advent.of.code.day5;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Day5b {
    Path filePathRules = Paths.get("src/main/java/org/example/advent/of/code/day5/day5resource1.txt");
    Path filePathUpdates = Paths.get("src/main/java/org/example/advent/of/code/day5/day5resource2.txt");
    Map<Integer,Set<Integer>> orderingRules = new HashMap<>();

    public Map<Integer,Set<Integer>> getOrderingRule(){
        Map<Integer,Set<Integer>> orderingRules = new HashMap<>();
        try (Stream<String> lines = Files.lines(filePathRules)) {
            lines.forEach(line -> {
                String [] strings =line.split("\\|");
                int a = Integer.parseInt(strings[0]);
                int b = Integer.parseInt(strings[1]);
                Set <Integer> set;
                if(orderingRules.containsKey(a)){
                    set = orderingRules.get(a);
                    set.add(b);
                    orderingRules.put(a,set);
                }else{
                    set=new HashSet<>();
                    set.add(b);
                    orderingRules.put(a,set);
                }
            });
        } catch (IOException e) {
            System.out.println("failed to read file");
        }
        return orderingRules;
    }



    public int totalMidSum(){
        int sum =0;
        this.orderingRules = getOrderingRule();
        try (Stream<String> lines = Files.lines(filePathUpdates)) {
            sum += lines.map(this::mapStringToList)
                    .filter(this::isNotFollowingRule)
                    .map(this::correctOrder)
                    .map(this::midPoint)
                    .mapToInt(Integer::intValue)
                    .sum();
        } catch (IOException e) {
            System.out.println("failed to read file");
        }
        return sum;
    }

    private List<Integer> mapStringToList(String line){
        return Arrays.stream(line.split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .toList();
    }
    private boolean isFollowingRule(final List<Integer> list){
        List<Integer> readValues = new ArrayList<>();
        for(int v: list){
            if(this.orderingRules.containsKey(v)){
                Set<Integer> currRule= this.orderingRules.get(v);
                for(int rv: readValues){
                    if(currRule.contains(rv)){
                        return false;
                    }
                }
            }
            readValues.add(v);
        }
        return true;
    }

    private boolean isNotFollowingRule(final List<Integer> list){
        return !isFollowingRule(list);
    }

    //used bubble sort to sort them in correct order
    private List<Integer> correctOrder(final List<Integer> newList){
        List<Integer> list = new ArrayList<>(newList);
        int n = list.size();
        for(int i=n-1; i >= 0; i--){
            boolean isPositionFixed = false;
            while (!isPositionFixed){
                boolean noSwapping = true;
                Set<Integer> currRule= this.orderingRules.get(list.get(i));
                if(currRule!=null){
                    for (int j = i - 1; j >= 0; j--) {
                        if (currRule.contains(list.get(j))) {
                            noSwapping = false;
                            int tempI = list.get(i);
                            int tempJ = list.get(j);
                            list.set(i, tempJ);
                            list.set(j, tempI);
                            break;
                        }
                    }
                }
                if(noSwapping){
                    isPositionFixed= true;
                }
            }
        }
        return list;

    }

    private int midPoint(final List<Integer> list){
        return list.get(list.size()/2);
    }
}


