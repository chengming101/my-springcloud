//package com.security.test.consumer.config;
//
//import com.security.test.consumer.config.KafkaSender;
//import org.springframework.stereotype.Component;
//
//@Component("KafkaSenderTask ")
//public class KafkaSenderTask implements ITask {
//    /**
//     * params 可为空
//     * @param params   参数，多参数使用JSON数据
//     */
//    @Override
//    public void run(String params){
//        KafkaSender kafkaSender = new KafkaSender();
//        kafkaSender.sendMsg("M","工单消息内容");
//    }
//}