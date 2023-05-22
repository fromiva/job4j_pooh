package ru.job4j.pooh;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    Map<String, Map<String, Queue<String>>> queues = new ConcurrentHashMap<>();
    String subscriber = null;

    @Override
    public Response process(Request request) {
        String result = null;
        switch (request.httpRequestType()) {
            case "POST" -> {
                queues.putIfAbsent(request.sourceName(), new ConcurrentHashMap<>());
                queues.get(request.sourceName()).putIfAbsent(subscriber, new ConcurrentLinkedQueue<>());
                queues.get(request.sourceName()).get(subscriber).add(request.param());
            }
            case "GET" -> {
                subscriber = request.param();
                result = queues.getOrDefault(request.sourceName(), new ConcurrentHashMap<>()).getOrDefault(subscriber, new ConcurrentLinkedQueue<>()).poll();
            }
            default -> throw new IllegalStateException();
        }
        return new Response(result == null ? "" : result, "200");
    }
}
