package ru.job4j.pooh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoohServer {
    private final Map<String, Service> modes = new HashMap<>();

    public static void main(String[] args) {
        new PoohServer().start();
    }

    public void start() {
        modes.put("queue", new QueueService());
        modes.put("topic", new TopicService());
        ExecutorService pool = Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                pool.execute(getRunnable(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Runnable getRunnable(Socket socket) {
        return () -> {
            try (OutputStream out = socket.getOutputStream();
                 InputStream in = socket.getInputStream()) {
                byte[] buffer = new byte[1_000_000];
                int total = in.read(buffer);
                String content = new String(
                        Arrays.copyOfRange(buffer, 0, total), StandardCharsets.UTF_8);
                Request request = Request.of(content);
                Response response = modes.getOrDefault(
                        request.poohMode(),
                        r -> new Response("Wrong mode. Rejected...", "200")).process(request);
                String sep = "\r\n";
                String result = "HTTP/1.1 "
                        + response.status()
                        + sep
                        + sep
                        + response.text()
                        + sep;
                out.write(result.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
