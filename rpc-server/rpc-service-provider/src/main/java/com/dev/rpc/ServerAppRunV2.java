package com.dev.rpc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dev.rpc.v2.SpringConfig;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 08:09
 * @description:
 */
public class ServerAppRunV2 {
    public static void main(String[] args) {
        System.out.println("服务启动......");

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        ((AnnotationConfigApplicationContext)applicationContext).start();
    }
}
