package ru.job4j.pooh;

import java.util.HashMap;
import java.util.Map;

public record Request(
        String httpRequestType,
        String poohMode,
        String sourceName,
        String param) {
    public static Request of(String content) {
        String sep = "\\r\\n";
        Map<String, String> params = new HashMap<>();
        String[] elements = content.split(sep, 2);
        params.putAll(parseRequest(elements[0]));
        params.putAll(parseUrl(params.get("Url")));
        String body = parseBody(elements[1].split(sep + sep, 2)[1]);
        return new Request(
                params.get("Method"),
                params.get("Mode"),
                params.get("Topic"),
                "GET".equals(params.get("Method")) ? params.get("User") : body);
    }

    private static Map<String, String> parseRequest(String line) {
        Map<String, String> result = new HashMap<>();
        String[] parts = line.split(" ");
        result.put("Method", parts[0]);
        result.put("Url", parts[1]);
        result.put("Protocol", parts[2]);
        return result;
    }

    private static Map<String, String> parseUrl(String line) {
        Map<String, String> result = new HashMap<>();
        String[] parts = line.split("/");
        result.put("Mode", parts[1]);
        result.put("Topic", parts[2]);
        result.put("User", parts.length > 3 ? parts[3] : "");
        return result;
    }

    private static String parseBody(String line) {
        return line.strip();
    }
}
