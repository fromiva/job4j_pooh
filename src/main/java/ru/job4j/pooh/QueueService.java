package ru.job4j.pooh;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final Map<String, Queue<String>> queues = new ConcurrentHashMap<>();

    @Override
    public Response process(Request request) {
        String result = null;
        switch (request.httpRequestType()) {
            case "POST" -> {
                queues.putIfAbsent(request.sourceName(), new ConcurrentLinkedQueue<>());
                queues.get(request.sourceName()).add(request.param());
            }
            case "GET" -> {
                result = queues.getOrDefault(
                        request.sourceName(), new ConcurrentLinkedQueue<>()
                ).poll();
            }
            default -> throw new IllegalStateException();
        }
        return new Response(result == null ? "" : result, "200");
    }
}
