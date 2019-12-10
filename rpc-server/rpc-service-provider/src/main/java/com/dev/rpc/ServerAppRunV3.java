package com.dev.rpc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dev.rpc.v3.SpringNettyConfig;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 08:09
 * @description:
 */
public class ServerAppRunV3 {
    public static void main(String[] args) {
        System.out.println("服务启动......");

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringNettyConfig.class);
        ((AnnotationConfigApplicationContext) applicationContext).start();
    }
}
