package com.esunny.event.service.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class PostFilter<T> extends Filter {

    private String key = "post_anonymous";
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private Class<T> target;

    public PostFilter(Class<T> t) {
        target = t;
        key += "_";
        key += t;
    }

    @Override
    public String description() {
        return key;
    }

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        exchange.setAttribute(key, gson.fromJson(br, target));
        chain.doFilter(exchange);
    }
}