package com.xxxyjade.hiphopghetto.common.constant;

import java.util.HashMap;
import java.util.Map;

public class Number {
    private static final Map<Integer, String> numbers = new HashMap<>(){{
        put(1, "一");
        put(2, "二");
        put(3, "三");
        put(4, "四");
        put(5, "五");
        put(6, "六");
        put(7, "七");
        put(8, "八");
        put(9, "九");
        put(10, "十");
    }};

    public static String getStr(Integer num) {
        return numbers.get(num);
    }
}
