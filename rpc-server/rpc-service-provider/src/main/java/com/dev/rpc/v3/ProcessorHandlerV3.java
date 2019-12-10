package com.dev.rpc.v3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

import org.springframework.util.StringUtils;

import com.dev.rpc.pojo.RpcRequest;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 09:20
 * @description: 基于netty的处理对象
 */
public class ProcessorHandlerV3 extends SimpleChannelInboundHandler<RpcRequest> {

    private Map<String, Object> serviceMap;

    public ProcessorHandlerV3(Map<String, Object> serviceMap) {
        this.serviceMap = serviceMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chx, RpcRequest request) throws Exception {
        System.out.println("读取到客户端请求数据：" + request.toString());
        Object result = invoke(request);
        if (result != null) {
            chx.writeAndFlush(result).addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 通过反射调用本地方法
     *
     * @param request
     * @return
     */
    private Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 断言
        Objects.requireNonNull(request.getClassName());
        Objects.requireNonNull(request.getMethodName());
        String serviceName = request.getClassName();
        String version = request.getVersion();
        // 请求数据中的服务别名
        String name = request.getServiceName();
        if (!StringUtils.isEmpty(version)) {
            serviceName += "-" + version;
        }
        if (!StringUtils.isEmpty(name)) {
            serviceName += "-" + name;
        }
        Object service = serviceMap.get(serviceName);
        if (service == null) {
            throw new IllegalArgumentException("found not serviceName:" + serviceName);
        }
        // 客户端请求参数
        Object[] args = request.getParameters();
        Class<?>[] types = null;
        if (args != null) {
            types = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                types[i] = args[i].getClass();
            }
        }
        // 根据请求，获取对应类
        Class clazz = Class.forName(request.getClassName());
        // 获取对应方法
        Method method = clazz.getMethod(request.getMethodName(), types);
        // 进行反射调用
        Object result = method.invoke(service, args);
        return result;
    }


}
