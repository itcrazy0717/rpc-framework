package com.dev.rpc.v3;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import com.dev.rpc.v2.RpcService;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 22:21
 * @description:
 */
public class RpcNettyServer implements ApplicationContextAware, InitializingBean {

    private Map<String, Object> serviceMap = new HashMap<>();

    private int port;

    public RpcNettyServer(int port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 使用netty来做网络通信，在v2版本中使用的是BIO
        // 接受客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        // 处理已经接受的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                 .channel(NioServerSocketChannel.class)
                 .childHandler(new ChannelInitializer<SocketChannel>() {
                     @Override
                     protected void initChannel(SocketChannel socketChannel) throws Exception {
                         socketChannel.pipeline()
                                      // 先解码
                                      .addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                                      // 再编码
                                      .addLast(new ObjectEncoder())
                                      // 进入具体处理逻辑
                                      .addLast(new ProcessorHandlerV3(serviceMap));
                     }
                 });
        bootstrap.bind(port).sync();
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
                // 解决一个接口多个实现的问题，但是客户端必须传入name属性才行
                if (!StringUtils.isEmpty(name)) {
                    serviceName += "-" + name;
                }
                serviceMap.put(serviceName, bean);
            }
        }
    }
}
