package com.dev.rpc.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 08:32
 * @description:Rpc请求封装
 */
@Data
public class RpcRequest implements Serializable {

    /**
     * 具体请求类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数集合
     */
    private Object[] parameters;

    /**
     * 版本号
     */
    private String version;

    /**
     * 服务名，解决一个接口多个实现的问题
     */
    private String serviceName;
}
