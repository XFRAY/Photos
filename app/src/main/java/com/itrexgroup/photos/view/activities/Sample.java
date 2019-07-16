package com.itrexgroup.photos.view.activities;

public class Sample {


    public static void main(String[] args) {
        int value = 5;
        if (isEven(value)) {
            value += 2;
        } else {
            value += 1;
        }
        System.out.println(value);
    }

    private static boolean isEven(int value) {
        return value % 2 == 0;
    }
}
