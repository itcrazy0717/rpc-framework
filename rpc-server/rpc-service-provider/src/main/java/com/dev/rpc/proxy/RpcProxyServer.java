package com.dev.rpc.proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 09:16
 * @description:
 */
public class RpcProxyServer {

    // 为了简化，这里直接使用cached线程池
    private ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 对外暴露服务
     *
     * @param service 具体服务对象
     * @param port    端口号
     */
    public void publisher(Object service, int port) {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // 一直接受请求
            while (true) {
                // BIO
                Socket socket = serverSocket.accept();
                System.out.println("请求,ip:" + socket.getInetAddress() + " port:" + socket.getPort());
                // 每个请求交个一个线程进行处理
                executorService.execute(new ProcessorHandler(socket, service));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
