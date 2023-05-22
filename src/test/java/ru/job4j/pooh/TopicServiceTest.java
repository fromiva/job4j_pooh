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
        Response result1 = service.process(new Request("GET", "topic", "weather", paramForSubscriber1));
        assertThat(result1.text()).isEqualTo("temperature=18");
    }

    @Test
    public void whenSubscribePutAndGetByTwoUsers() {
        TopicService service = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        service.process(new Request("GET", "topic", "weather", paramForSubscriber1));
        service.process(new Request("POST", "topic", "weather", paramForPublisher));
        Response result1 = service.process(new Request("GET", "topic", "weather", paramForSubscriber1));
        Response result2 = service.process(new Request("GET", "topic", "weather", paramForSubscriber2));
        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result2.text()).isEqualTo("");
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
        service.process(new Request("POST", "topic", "weather", paramForPublisher1));
        service.process(new Request("GET", "topic", "news", paramForSubscriber2));
        service.process(new Request("POST", "topic", "news", paramForPublisher2));
        service.process(new Request("POST", "topic", "news", paramForPublisher3));
        Response result1 = service.process(new Request("GET", "topic", "weather", paramForSubscriber1));
        Response result2 = service.process(new Request("GET", "topic", "weather", paramForSubscriber2));
        Response result3 = service.process(new Request("GET", "topic", "news", paramForSubscriber2));
        Response result4 = service.process(new Request("GET", "topic", "news", paramForSubscriber2));
        assertThat(result1.text()).isEqualTo(paramForPublisher1);
        assertThat(result2.text()).isEqualTo("");
        assertThat(result3.text()).isEqualTo(paramForPublisher2);
        assertThat(result4.text()).isEqualTo(paramForPublisher3);
    }
}
