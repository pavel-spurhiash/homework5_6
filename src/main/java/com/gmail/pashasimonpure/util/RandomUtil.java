package com.gmail.pashasimonpure.util;

import java.util.Random;

public class RandomUtil {

    private static final Random random = new Random();

    public static int getRandomIntBetween(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static int getRandomInt() {
        return random.nextInt();
    }

    public static int getRandomIntAbs() {
        return Math.abs(random.nextInt());
    }

    public static boolean getRandomBoolean() {
        if (getRandomIntBetween(0, 1) == 0) {
            return false;
        }
        return true;
    }

}