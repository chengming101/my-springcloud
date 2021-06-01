package com.security.test.consumer.config;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GsonTest {
    private static Calendar e;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.NOVEMBER, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        e = calendar;
    }

    public static void main(String[] args) {
        Long ll = System.currentTimeMillis();
        List<Map<String, String>> mapList = new ArrayList<>();
        Map map = new HashMap();
        map.put("id", "1");
        map.put("name", "葵花宝典");
        Map map2 = new HashMap();
        map2.put("id", "2");
        map2.put("name", "九阴真经");
        mapList.add(map);
        mapList.add(map2);
        Gson gson = new Gson();
        System.out.println(gson.toJson(mapList));
        new KafkaSender().sendMsg("test", "您好！");
    }
}
//打印结果：[{"name":"葵花宝典","id":"1"},{"name":"九阴真经","id":"2"}]