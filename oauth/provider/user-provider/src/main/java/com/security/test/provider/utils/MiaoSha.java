package com.security.test.provider.utils;

import com.security.test.provider.domain.Storage;
import com.security.test.provider.domain.User;
import com.security.test.provider.domain.UserOrder;
import com.security.test.provider.service.OrderService;
import com.security.test.provider.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MiaoSha {

    private static int sum;
    @Value("${spring.redis.port:9999}")
    String port;
    @Autowired
    OrderService orderService;
    @Autowired
    StorageService storageService;
    @Autowired
    RedisUtils redisUtils;

    public List<String> miaosha(String id) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(User.builder().id("user_" + i).build());
        }
        List<String> list = new ArrayList<>();
        users.parallelStream().forEach(user -> {
                    String u = qiang(user, id);
                    if (u != null)
                        list.add(u);
                }
        );
        return list;
    }

    @Transactional
    public String qiang(User user, String id) {
        long time = System.currentTimeMillis() + 10 * 1000;
        Storage storage = storageService.sum(id);
        int sum = storage.getStatus();
        redisUtils.set("{order}kucun_" + id, String.valueOf(sum));
        if (sum > 0)
            while (System.currentTimeMillis() <= time) {
                //Boolean包装类表达式中使用，避免sonar扫描
                if (Boolean.TRUE.equals(redisUtils.setNx("{order}miaosha_" + id, user.getId(), null))) {
                    long stTime = System.currentTimeMillis();
                    try {
                        sum = Integer.parseInt(redisUtils.get("{order}kucun_" + id));
                        log.info("当前库存为{}", sum);
                        if (sum < 1) {
                            log.info("暂无库存");
                            break;
                        }
                        log.info("当前用户为{}", user.getId());
                        //模拟下单、减库存操作时间
                        boolean b = storageService.updateStorageById(storage.getId(), sum - 1);
                        orderService.insert(UserOrder.builder().orderId(user.getId()).address("").name(storage.getStartTime() + "-" + storage.getEndTime()).build());
                        redisUtils.set("{order}kucun_" + id, String.valueOf(sum - 1));
                        log.info("当前用户{},抢单成功", user.getId());

                        return user.getId();
                    } finally {
                        //释放锁
                        String lua = "if redis.call('get','{order}miaosha_1')==" + "'" +
                                user.getId() + "'" +
                                " then return redis.call('del','{order}miaosha_1')" + " else return 0 end";
                        log.info("lua========" + lua);
//                        redisUtils.execute(lua);
                        if (user.getId().equals(redisUtils.get("{order}miaosha_1")))
                            redisUtils.remove("{order}miaosha_1");
                        log.info("====耗时==" + (System.currentTimeMillis() - stTime));
                    }
                } else {
//                log.info("当前用户{},抢单失败，等待重试", user.getId());
                }
            }
        else {
//            redisUtils.remove("kucun_" + id);
        }
        return null;
    }
}