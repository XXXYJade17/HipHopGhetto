package com.xxxyjade.hiphopghetto.common.constant;

import java.util.HashMap;
import java.util.Map;

public class Number {
    private static final Map<Integer, String> numbers = new HashMap<>(){{
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

    public static String getStr(Integer num) {
        return numbers.get(num);
    }
}
