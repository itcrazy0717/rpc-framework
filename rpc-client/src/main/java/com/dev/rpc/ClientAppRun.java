package com.dev.rpc;

import com.dev.rpc.service.HelloService;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 08:09
 * @description:
 */
public class ClientAppRun {
    public static void main(String[] args) {
        HelloService helloService = RpcProxyClient.clientProxy(HelloService.class, "localhost", 8080);
        String result = helloService.sayHello("hello rpc");
        System.out.println(result);
        String msg = helloService.getMsg();
        System.out.println(msg);
        helloService.execute();
    }
}
