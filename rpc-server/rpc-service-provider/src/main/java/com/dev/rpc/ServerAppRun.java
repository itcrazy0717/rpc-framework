package com.dev.rpc;

import com.dev.rpc.proxy.RpcProxyServer;
import com.dev.rpc.service.HelloService;
import com.dev.rpc.serviceimpl.HelloServiceImpl;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 08:09
 * @description:
 */
public class ServerAppRun {
    public static void main(String[] args) {
        System.out.println("服务启动......");
        HelloService service = new HelloServiceImpl();
        RpcProxyServer proxyServer = new RpcProxyServer();
        // 将服务进行发布，监听客户端请求
        proxyServer.publisher(service, 8080);
    }
}
