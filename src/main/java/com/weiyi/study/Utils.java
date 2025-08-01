package com.weiyi.study;

public class Utils {
    public static void showArrayItems(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println(); // Move to next line after printing all items
    }

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};
        showArrayItems(numbers);
    }
}