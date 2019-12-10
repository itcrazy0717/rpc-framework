package com.dev.rpc.service;

import com.dev.rpc.pojo.User;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 08:29
 * @description:
 */
public interface HelloService {

    /**
     * hello测试方法
     *
     * @param content 内容
     * @return
     */
    String sayHello(String content);

    /**
     * 保存用户
     *
     * @param user 用户对象
     * @return
     */
    String saveUser(User user);

    /**
     * 获取信息
     *
     * @return
     */
    String getMsg();

    /**
     * 执行方法
     */
    void execute();

}
