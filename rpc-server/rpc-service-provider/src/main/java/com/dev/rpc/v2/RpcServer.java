package com.dev.rpc.v2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 12:22
 * @description:
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private Map<String, Object> serviceMap = new HashMap<>();

    private int port;

    public RpcServer(int port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // 一直接受请求
            while (true) {
                // BIO
                Socket socket = serverSocket.accept();
                System.out.println("请求,ip:" + socket.getInetAddress() + " port:" + socket.getPort());
                // 每个请求交个一个线程进行处理
                executorService.execute(new ProcessorHandlerV2(socket, serviceMap));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (!beans.isEmpty()) {
            for (Object bean : beans.values()) {
                // 获取注解
                RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
                // 获取接口定义
                String serviceName = rpcService.value().getName();
                String version = rpcService.version();
                String name = rpcService.name();
                if (!StringUtils.isEmpty(version)) {
                    serviceName += "-" + version;
                }
                if (!StringUtils.isEmpty(name)) {
                    serviceName += "-" + name;
                }
                serviceMap.put(serviceName, bean);
            }
        }
    }
}
