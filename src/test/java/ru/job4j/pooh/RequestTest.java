package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest {
    @Test
    void whenQueueModePostMethod() {
        String sep = "\r\n";
        String content = "POST /queue/weather HTTP/1.1" + sep
                + "Host: localhost:9000" + sep
                + "User-Agent: curl/7.72.0" + sep
                + "Accept: */*" + sep
                + "Content-Length: 14" + sep
                + "Content-Type: application/x-www-form-urlencoded" + sep
                + "" + sep
                + "temperature=18" + sep;
        Request request = Request.of(content);
        assertThat(request.httpRequestType()).isEqualTo("POST");
        assertThat(request.poohMode()).isEqualTo("queue");
        assertThat(request.sourceName()).isEqualTo("weather");
        assertThat(request.param()).isEqualTo("temperature=18");
    }

    @Test
    void whenQueueModeGetMethod() {
        String sep = "\r\n";
        String content = "GET /queue/weather HTTP/1.1" + sep
                + "Host: localhost:9000" + sep
                + "User-Agent: curl/7.72.0" + sep
                + "Accept: */*" + sep + sep + sep;
        Request request = Request.of(content);
        assertThat(request.httpRequestType()).isEqualTo("GET");
        assertThat(request.poohMode()).isEqualTo("queue");
        assertThat(request.sourceName()).isEqualTo("weather");
        assertThat(request.param()).isEqualTo("");
    }

    @Test
    void whenTopicModePostMethod() {
        String sep = "\r\n";
        String content = "POST /topic/weather HTTP/1.1" + sep
                + "Host: localhost:9000" + sep
                + "User-Agent: curl/7.72.0" + sep
                + "Accept: */*" + sep
                + "Content-Length: 14" + sep
                + "Content-Type: application/x-www-form-urlencoded" + sep
                + "" + sep
                + "temperature=18" + sep;
        Request request = Request.of(content);
        assertThat(request.httpRequestType()).isEqualTo("POST");
        assertThat(request.poohMode()).isEqualTo("topic");
        assertThat(request.sourceName()).isEqualTo("weather");
        assertThat(request.param()).isEqualTo("temperature=18");
    }

    @Test
    void whenTopicModeGetMethod() {
        String sep = "\r\n";
        String content = "GET /topic/weather/client407 HTTP/1.1" + sep
                + "Host: localhost:9000" + sep
                + "User-Agent: curl/7.72.0" + sep
                + "Accept: */*" + sep + sep + sep;
        Request request = Request.of(content);
        assertThat(request.httpRequestType()).isEqualTo("GET");
        assertThat(request.poohMode()).isEqualTo("topic");
        assertThat(request.sourceName()).isEqualTo("weather");
        assertThat(request.param()).isEqualTo("client407");
    }
}
