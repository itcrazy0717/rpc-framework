package com.dev.rpc.invocationhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.dev.rpc.pojo.RpcRequest;
import com.dev.rpc.transport.RpcNettyTransport;
import com.google.common.base.Strings;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 10:41
 * @description:
 */
public class RemoteInvocationHandler implements InvocationHandler {

    private String host;
    private int port;
    private String version;
    private String serviceName;

    public RemoteInvocationHandler(String host, int port, String version, String serviceName) {
        this.host = host;
        this.port = port;
        this.version = version;
        this.serviceName = serviceName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进入代理，进行请求封装");
        // 封装请求数据
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);
        // 增加版本号
        if (!Strings.isNullOrEmpty(version)) {
            request.setVersion(version);
        }
        if (!Strings.isNullOrEmpty(serviceName)) {
            request.setServiceName(serviceName);
        }
        // RpcNetTransport netTransport = new RpcNetTransport(host, port);
        // 使用netty进行连接
        RpcNettyTransport netTransport = new RpcNettyTransport(host, port);
        return netTransport.send(request);
    }
}
