package ru.job4j.pooh;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final Map<String, Map<String, Queue<String>>> queues = new ConcurrentHashMap<>();

    @Override
    public Response process(Request request) {
        String result = null;
        switch (request.httpRequestType()) {
            case "POST" -> {
                for (String user : queues.keySet()) {
                    String source = request.sourceName();
                    queues.get(user).putIfAbsent(source, new ConcurrentLinkedQueue<>());
                    queues.get(user).get(source).offer(request.param());
                }
            }
            case "GET" -> {
                String user = request.param();
                queues.putIfAbsent(user, new ConcurrentHashMap<>());
                result = queues.get(user)
                        .getOrDefault(request.sourceName(), new ConcurrentLinkedQueue<>())
                        .poll();
            }
            default -> throw new IllegalStateException();
        }
        return new Response(result == null ? "" : result, "200");
    }
}
