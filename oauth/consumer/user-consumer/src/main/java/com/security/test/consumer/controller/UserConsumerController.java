package com.security.test.consumer.controller;


import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.security.api.service.UserClientService;
import com.security.test.consumer.config.KafkaSender;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.consul.config.ConsulConfigProperties;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/consumer")
public class UserConsumerController {

    //    @Autowired
    KafkaSender kafkaSender;
    //    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    RestTemplate restTemplate;
    //    @Autowired
    RedisProperties redisProperties;
    //    @Autowired
    RequestRateLimiter requestRateLimiter;
    @Autowired
    FastFileStorageClient fastFileStorageClient;
    @Autowired
    FdfsWebServer fdfsWebServer;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private ConsulDiscoveryProperties consulDiscoveryProperties;
    @Autowired
    private ConsulConfigProperties consulConfigProperties;

    @GetMapping
    public void tes4t(HttpServletResponse response) {
        File file = new File("F:\\oauth\\consumer\\user-provider\\spring.log");
        StorePath path = null;
        try {
            path = fastFileStorageClient.uploadFile(new FileInputStream(file), file.length(), file.getName().substring(file.getName().lastIndexOf(".") + 1), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        log.info("=====full路径====" + path.getFullPath());
        log.info("=====路径====" + path.getPath());
        byte[] data = fastFileStorageClient.downloadFile(path.getGroup(), path
                .getPath(), new DownloadByteArray());
        response.setCharacterEncoding("UTF-8");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("fdfs.log", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            IOUtils.write(data, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return "test";
    }

    @GetMapping("/test")
    public String test() {
        if (1 > 0) ;
//            throw new RuntimeException("有异常了。请注意。。。");
        List<String> services = discoveryClient.getServices();
        List<ServiceInstance> list = discoveryClient.getInstances("user-consumer");
        if (list != null && list.size() > 0) {
//            return list.get(0).getUri().toString();
            //restTemplate bean 必须加负载均衡注解@LoadBalanced,此时会默认去客户端获取注册列表，否则根据服务名不能调用
            return restTemplate.getForObject("http://user-provider" + "/test", String.class);
        }
        System.out.println(services);
        return "tt";
//        return "jj";
    }

    @GetMapping("/user")
    public String tesusert(String id) {
//        System.out.println(userClientService.test1());
//        if (requestRateLimiter.overLimitWhenIncremented(id))
//            return "稍后再试一下";
//         return userClientService.test();
        kafkaSender.sendMsg("test34563", "helloll！");
        Map map = new HashMap<>();
        map.size();
        int[] nums;
        return "success";
    }


}
