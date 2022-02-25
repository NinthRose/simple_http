package com.esunny.event.service;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Service {
    public static String REQUEST = "key";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8321), 0);  //允许排队的最大TCP连接数, 0:系统默认值
        HttpContext context = server.createContext("/test", new ServiceHandler());
        context.getAttributes();
        ParameterFilter filter = new ParameterFilter();
        filter.setKey(REQUEST);
        context.getFilters().add(filter);

        //设置服务器的线程池对象
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
    }
}
