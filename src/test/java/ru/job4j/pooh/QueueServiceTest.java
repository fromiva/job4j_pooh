package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueueServiceTest {
    @Test
    void whenPostThenGetQueue() {
        QueueService service = new QueueService();
        String expected = "temperature=18";
        service.process(new Request("POST", "queue", "weather", expected));
        Response actual = service.process(new Request("GET", "queue", "weather", null));
        assertThat(actual.text()).isEqualTo(expected);
    }

    @Test
    void whenPostTwoTimesThenGetQueueTwoTimes() {
        QueueService service = new QueueService();
        String expected1 = "temperature=18";
        String expected2 = "temperature=-1";
        service.process(new Request("POST", "queue", "weather", expected1));
        service.process(new Request("POST", "queue", "weather", expected2));
        Response actual1 = service.process(new Request("GET", "queue", "weather", null));
        Response actual2 = service.process(new Request("GET", "queue", "weather", null));
        assertThat(actual1.text()).isEqualTo(expected1);
        assertThat(actual2.text()).isEqualTo(expected2);
    }

    @Test
    void whenPostDifferentTopicsThenGetQueue() {
        QueueService service = new QueueService();
        String expected1 = "temperature=18";
        String expected2 = "Something happens";
        service.process(new Request("POST", "queue", "weather", expected1));
        service.process(new Request("POST", "queue", "news", expected2));
        Response actual1 = service.process(new Request("GET", "queue", "weather", null));
        Response actual2 = service.process(new Request("GET", "queue", "news", null));
        assertThat(actual1.text()).isEqualTo(expected1);
        assertThat(actual2.text()).isEqualTo(expected2);
    }

    @Test
    void whenPostDifferentManyTimesTopicsThenGetQueue() {
        QueueService service = new QueueService();
        String expected1 = "temperature=18";
        String expected2 = "Something happens 1";
        String expected3 = "Something happens 2";
        String expected4 = "Something happens 3";
        service.process(new Request("POST", "queue", "weather", expected1));
        service.process(new Request("POST", "queue", "news", expected2));
        service.process(new Request("POST", "queue", "news", expected3));
        service.process(new Request("POST", "queue", "news", expected4));
        Response actual1 = service.process(new Request("GET", "queue", "weather", null));
        Response actual2 = service.process(new Request("GET", "queue", "news", null));
        Response actual3 = service.process(new Request("GET", "queue", "news", null));
        Response actual4 = service.process(new Request("GET", "queue", "news", null));
        assertThat(actual1.text()).isEqualTo(expected1);
        assertThat(actual2.text()).isEqualTo(expected2);
        assertThat(actual3.text()).isEqualTo(expected3);
        assertThat(actual4.text()).isEqualTo(expected4);
    }

    @Test
    void whenNoPostThenGetEmpty() {
        QueueService service = new QueueService();
        String expected = "";
        Response actual = service.process(new Request("GET", "queue", "weather", null));
        assertThat(actual.text()).isEqualTo(expected);
    }
}
