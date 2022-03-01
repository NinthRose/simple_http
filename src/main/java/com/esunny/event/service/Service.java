package com.esunny.event.service;

import com.esunny.event.service.bean.PostData;
import com.esunny.event.service.filter.GetFilter;
import com.esunny.event.service.filter.PostFilter;
import com.esunny.event.service.handler.GetHandler;
import com.esunny.event.service.handler.PostHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Service {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8321), 0);  //允许排队的最大TCP连接数, 0:系统默认值

        HttpContext context = server.createContext("/get", new GetHandler());
        HttpContext post = server.createContext("/post", new PostHandler());

        context.getFilters().add(new GetFilter());
        post.getFilters().add(new PostFilter<>(PostData.class));

        //设置服务器的线程池对象
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
    }
}
