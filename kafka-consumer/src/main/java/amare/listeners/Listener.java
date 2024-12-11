package amare.listeners;

import amare.dto.Event;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;
@Slf4j
@Component
public class Listener {

    @KafkaListener(topics = "${wikimedia.topic.name}", groupId = "${spring.kafka.consumer.group.id}")
    public void consume(@Payload final String eventMessage, @Headers Map<String, Object> headers) {
        try {
            // Instantiate ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            /*Added this line to ignore the "$schema" field which threw an error because it couldn't be parsed*/
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // Deserialize JSON string into an Event object
            Event event = objectMapper.readValue(eventMessage, Event.class);

            // Log or process the event object
            log.info(String.format("Event message received -> %s", event.getWiki()));

            headers.forEach((key, value) -> {
                log.info(String.format("Header: %s, Value: %s", key, value));
            });
        } catch (Exception e) {
            log.error("Error deserializing message", e);
        }
    }
}
