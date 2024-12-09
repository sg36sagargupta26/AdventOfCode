package org.example.util;

public class Helper {
    public static byte[][] deepCopy(byte[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
    }
}
