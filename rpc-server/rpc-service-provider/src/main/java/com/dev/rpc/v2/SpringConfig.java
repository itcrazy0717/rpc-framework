package com.dev.rpc.v2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 12:21
 * @description:
 */
@Configuration
@ComponentScan(basePackages = "com.dev.rpc.v2")
public class SpringConfig {

    @Bean
    public RpcServer rpcServer() {
        // 这里将端口固定，当然可进行动态配置
        return new RpcServer(8080);
    }
}
