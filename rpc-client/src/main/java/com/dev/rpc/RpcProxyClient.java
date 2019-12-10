package com.dev.rpc;

import java.lang.reflect.Proxy;

import com.dev.rpc.invocationhandler.RemoteInvocationHandler;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 10:37
 * @description:rpc客户端请求
 */
public class RpcProxyClient {

    /**
     * rpc请求客户端
     * 通过jdk动态代理实现
     *
     * @param classInterface 接口
     * @param host           请求地址
     * @param port           端口
     * @param <T>
     * @return
     */
    public static <T> T clientProxy(final Class<T> classInterface,
                                    final String host,
                                    final int port,
                                    String version,
                                    String serviceName) {
        return (T) Proxy.newProxyInstance(classInterface.getClassLoader(),
                                          new Class[]{classInterface}, 
                                          new RemoteInvocationHandler(host, port, version, serviceName));
    }

}
