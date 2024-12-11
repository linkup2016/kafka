package com.amare.producer;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WikimediaChangesProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${wikimedia.topic.name}")
    private String topicName;

    @Value("${wikimedia.recent-change-url}")
    private String url;
    public WikimediaChangesProducer (KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {

        EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topicName);

        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();
        eventSource.start();

        TimeUnit.MINUTES.sleep(10);
    }
}
