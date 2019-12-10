package com.dev.rpc.v3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 12:21
 * @description:netty版本rpc
 */
@Configuration
@ComponentScan(basePackages = "com.dev.rpc.v3")
public class SpringNettyConfig {

    @Bean
    public RpcNettyServer rpcNettyServer() {
        // 这里将端口固定，当然可进行动态配置
        return new RpcNettyServer(8080);
    }
}
