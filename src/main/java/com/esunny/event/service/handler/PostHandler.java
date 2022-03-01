package com.esunny.event.service.handler;

import com.esunny.event.service.bean.PostData;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.lang.Thread.sleep;

public class PostHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            sleep(1000);
            List<Filter> fs = httpExchange.getHttpContext().getFilters();
            PostData d = null;
            String msg = "null";
            if (fs.size() > 0) {
                d = (PostData) httpExchange.getAttribute(fs.get(0).description());
                msg = new GsonBuilder().create().toJson(d);
                System.out.println(msg);
            }
            byte[] output = msg.getBytes(StandardCharsets.UTF_8);
            httpExchange.getResponseHeaders().add("Content-Type:", "text/html;charset=utf-8");
            //设置响应码和响应体长度，必须在getResponseBody方法之前调用！
            httpExchange.sendResponseHeaders(200, output.length);
            OutputStream out = httpExchange.getResponseBody();
            out.write(output);
            out.flush();
            out.close();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
