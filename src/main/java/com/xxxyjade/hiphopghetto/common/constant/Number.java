package com.xxxyjade.hiphopghetto.common.constant;

import java.util.HashMap;
import java.util.Map;

public class Number {
    private static final Map<Integer, String> inToStr = new HashMap<>(){{
        put(1, "one");
        put(2, "two");
        put(3, "three");
        put(4, "four");
        put(5, "five");
        put(6, "six");
        put(7, "seven");
        put(8, "eight");
        put(9, "nine");
        put(10, "ten");
    }};

    private static final Map<String, Integer> strToInt = new HashMap<>(){{
        put("one", 1);
        put("two", 2);
        put("three", 3);
        put("four", 4);
        put("five", 5);
        put("six", 6);
        put("seven", 7);
        put("eight", 8);
        put("nine", 9);
        put("ten", 10);
    }};

    public static String getStr(Integer num) {
        return inToStr.get(num);
    }

    public static Integer getInt(String num) {
        return strToInt.get(num);
    }
}
