package com.amare;

import com.amare.producer.WikimediaChangesProducer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerApplication  {

    @Autowired
    private WikimediaChangesProducer wikimediaChangesProducer;
    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @PostConstruct
    public void sendMessage() throws InterruptedException {
        wikimediaChangesProducer.sendMessage();
    }
}
