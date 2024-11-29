package amare;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaService {

    @KafkaListener(topics = "wikimedia_latest", groupId = "myGroup")
    public void consume(String eventMessage) {
        log.info(String.format("Event message received -> %s", eventMessage));
    }
}
