package kafka_streamer.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        // Start the Producer
        Thread producerThread = new Thread(() -> {
            try {
                com.kafkaimplementation.kafka-producer.KafkaProducerApplication.main(args);
            } catch (Exception e) {
                System.err.println("Failed to start Producer: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // Start the Consumer
        Thread consumerThread = new Thread(() -> {
            try {
                KafkaConsumerApplication.main(args);
            } catch (Exception e) {
                System.err.println("Failed to start Consumer: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // Start both threads
        producerThread.start();
        consumerThread.start();

        // Optionally wait for threads to finish (if needed)
        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            System.err.println("Error waiting for threads to finish: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


