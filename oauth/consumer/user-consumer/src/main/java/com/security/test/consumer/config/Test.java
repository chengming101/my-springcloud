package com.security.test.consumer.config;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class Test extends FastdfsConfig {

    static String tt() {
        return "234";
    }

    public static void main(String[] args) {

        Assert.hasText("", "不能为空");
        Test test = new Test();
        String oo = FastdfsConfig.tt();
        Test.tt();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        list.parallelStream().forEach(t -> {
            System.out.println("=====" + t);
            select(t);
        });

//        Yaml yaml = new Yaml();
//        InputStream in = Test.class.getClassLoader().getResourceAsStream("bootstrap.yml");
//        Map map = yaml.loadAs(in, Map.class);
//        System.out.println(map.keySet());
    }

    private static void select(Integer t) {
        Long endTime = System.currentTimeMillis() + 2000;
        while (true) {
            if (System.currentTimeMillis() <= endTime) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("====成功" + Thread.currentThread().getName() + "===" + t);
                break;
            }
        }
    }

    @Override
    public String yy() {
        return "999";
    }
}
