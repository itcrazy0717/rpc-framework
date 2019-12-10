package com.dev.rpc.invocationhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.dev.rpc.pojo.RpcRequest;
import com.dev.rpc.transport.RpcNetTransport;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 10:41
 * @description:
 */
public class RemoteInvocationHandler implements InvocationHandler {

    private String host;
    private int port;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
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
        request.setVersion("v1.0");
        RpcNetTransport netTransport = new RpcNetTransport(host, port);
        Object result = netTransport.send(request);
        return result;
    }
}
