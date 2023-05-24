package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TopicServiceTest {
    @Test
    public void whenSubscribePutAndGet() {
        TopicService service = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        service.process(new Request("GET", "topic", "weather", paramForSubscriber1));
        service.process(new Request("POST", "topic", "weather", paramForPublisher));
        Response result1 = service.process(
                new Request("GET", "topic", "weather", paramForSubscriber1));
        assertThat(result1.text()).isEqualTo("temperature=18");
    }

    @Test
    public void whenSubscribePutAndGetByTwoUsers() {
        TopicService service = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        service.process(new Request("GET", "topic", "weather", paramForSubscriber1));
        service.process(new Request("GET", "topic", "weather", paramForSubscriber2));
        service.process(new Request("POST", "topic", "weather", paramForPublisher));
        Response result1 = service.process(
                new Request("GET", "topic", "weather", paramForSubscriber1));
        Response result2 = service.process(
                new Request("GET", "topic", "weather", paramForSubscriber2));
        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result2.text()).isEqualTo("temperature=18");
    }

    @Test
    public void whenSubscribePutAndGetByManyUsersAndTopics() {
        TopicService service = new TopicService();
        String paramForPublisher1 = "temperature=18";
        String paramForPublisher2 = "Something happens 1";
        String paramForPublisher3 = "Something happens 2";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        service.process(new Request("GET", "topic", "weather", paramForSubscriber1));
        service.process(new Request("GET", "topic", "news", paramForSubscriber2));
        service.process(new Request("POST", "topic", "weather", paramForPublisher1));
        service.process(new Request("POST", "topic", "news", paramForPublisher2));
        service.process(new Request("POST", "topic", "news", paramForPublisher3));
        Response result11 = service.process(
                new Request("GET", "topic", "weather", paramForSubscriber1));
        Response result12 = service.process(
                new Request("GET", "topic", "news", paramForSubscriber1));
        Response result13 = service.process(
                new Request("GET", "topic", "news", paramForSubscriber1));
        Response result21 = service.process(
                new Request("GET", "topic", "weather", paramForSubscriber2));
        Response result22 = service.process(
                new Request("GET", "topic", "news", paramForSubscriber2));
        Response result23 = service.process(
                new Request("GET", "topic", "news", paramForSubscriber2));
        assertThat(result11.text()).isEqualTo(paramForPublisher1);
        assertThat(result12.text()).isEqualTo(paramForPublisher2);
        assertThat(result13.text()).isEqualTo(paramForPublisher3);
        assertThat(result21.text()).isEqualTo(paramForPublisher1);
        assertThat(result22.text()).isEqualTo(paramForPublisher2);
        assertThat(result23.text()).isEqualTo(paramForPublisher3);
    }
}
