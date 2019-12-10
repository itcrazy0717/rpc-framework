package com.dev.rpc.v3;

import com.dev.rpc.pojo.User;
import com.dev.rpc.service.HelloService;
import com.dev.rpc.v2.RpcService;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 08:29
 * @description:接口实现
 */
@RpcService(value = HelloService.class, version = "v3.0")
public class HelloServiceImplV3 implements HelloService {

    @Override
    public String sayHello(String content) {
        System.out.println("request in sayHello,parameter:" + content);
        return "Say Hello:" + content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("request in saveUser,parameter:" + user.toString());
        return "SUCCESS";
    }

    @Override
    public String getMsg() {
        System.out.println("request in getMsg");
        return "hello rpc";
    }

    @Override
    public void execute() {
        System.out.println("execute method");
    }
}
