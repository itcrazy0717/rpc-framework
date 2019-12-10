package com.dev.rpc.v2;

import com.dev.rpc.pojo.User;
import com.dev.rpc.service.HelloService;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 21:27
 * @description:
 */
@RpcService(value = HelloService.class, version = "v1.0", name = "DuplicateHelloService")
public class DuplicateHelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String content) {
        return "ok";
    }

    @Override
    public String saveUser(User user) {
        return "ok";
    }

    @Override
    public String getMsg() {
        return "ok";
    }

    @Override
    public void execute() {
        System.out.println("ok");
    }
}
