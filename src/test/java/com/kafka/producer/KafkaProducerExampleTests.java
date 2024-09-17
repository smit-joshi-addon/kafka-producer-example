package com.kafka.producer;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.kafka.producer.dto.Customer;
import com.kafka.producer.service.KafkaMessagePublisher;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
class KafkaProducerExampleTests {

	@Container
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
	
	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.producer.bootstrap-servers", kafka::getBootstrapServers);
	}
	
	@Autowired
	private KafkaMessagePublisher publisher;
	
	@Test
	public void testsSendEvetsToTopic() {
		publisher.sendEvetsToTopic(new Customer(263,"Test","test@gmail.com","9090909090"));
		
		Awaitility.await().pollInterval(Duration.ofSeconds(3)).atMost(10,TimeUnit.SECONDS).untilAsserted(() -> {
			// assert statements
		});
	}
	
	
	
	@Test
	void contextLoads() {
	}

}
