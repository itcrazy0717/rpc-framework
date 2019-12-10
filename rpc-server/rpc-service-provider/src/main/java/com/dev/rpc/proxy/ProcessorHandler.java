package com.dev.rpc.proxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Objects;

import com.dev.rpc.pojo.RpcRequest;

/**
 * @author: dengxin.chen
 * @date: 2019-12-10 09:20
 * @description: 具体处理事件类
 */
public class ProcessorHandler implements Runnable {

    private Socket socket;
    private Object service;

    public ProcessorHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            // 读取输入流中的具体请求参数
            // 具体类、方法、请求参数
            RpcRequest request = (RpcRequest) ois.readObject();
            Object result = invoke(request);

            // 将返回值写入流中
            oos.writeObject(result);
            oos.flush();

        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
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
