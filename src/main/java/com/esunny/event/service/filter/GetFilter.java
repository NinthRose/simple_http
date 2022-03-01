package com.esunny.event.service.filter;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetFilter extends Filter {

    private String key = "get_anonymous";

    public GetFilter setKey(String key) {
        this.key = key;
        return this;
    }

    @Override
    public String description() {
        return key;
    }

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        URI requestedUri = exchange.getRequestURI();
        String query = requestedUri.getRawQuery();
        parseQuery(query, parameters);
        exchange.setAttribute(key, parameters);
        chain.doFilter(exchange);
    }


    private void parseQuery(String query, Map<String, Object> parameters) {
        if (query != null) {
            String pairs[] = query.split("[&]");
            for (String pair : pairs) {
                String param[] = pair.split("[=]");
                if (param.length != 2) {
                    continue;
                }
                String key, value;
                try {
                    key = URLDecoder.decode(param[0], StandardCharsets.UTF_8.name());
                    value = URLDecoder.decode(param[1], StandardCharsets.UTF_8.name());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    continue;
                }

                if (parameters.containsKey(key)) {
                    Object obj = parameters.get(key);
                    if (obj instanceof List<?>) {
                        ((List<String>) obj).add(value);
                    } else if (obj instanceof String) {
                        List<String> values = new ArrayList<>();
                        values.add((String) obj);
                        values.add(value);
                        parameters.put(key, values);
                    }
                } else {
                    parameters.put(key, value);
                }
            }
        }
    }
}