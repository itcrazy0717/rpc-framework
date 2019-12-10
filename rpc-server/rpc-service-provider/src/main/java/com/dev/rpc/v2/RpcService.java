package com.dev.rpc.v2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 12:18
 * @description:rpc注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {

    /**
     * 存储服务接口
     *
     * @return
     */
    Class<?> value();

    /**
     * 版本号
     *
     * @return
     */
    String version() default "";
}
