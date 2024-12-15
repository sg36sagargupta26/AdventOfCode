package org.example.util;

public enum Direction {
    UP(-1,0),
    DOWN(1,0),
    LEFT(0,-1),
    RIGHT(0,1);

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    private final int i;
    private final int j;
    Direction(int i,int j){
        this.i = i;
        this.j = j;
    }

    public static Direction rotateRight(Direction direction){
        return switch (direction) {
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            case LEFT -> UP;
        };
    }
    public static Direction rotateLeft(Direction direction){
        return switch (direction){
            case UP -> LEFT;
            case RIGHT ->  UP;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
        };
    }
}
