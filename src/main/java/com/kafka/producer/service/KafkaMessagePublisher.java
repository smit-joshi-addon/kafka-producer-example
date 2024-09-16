package com.kafka.producer.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.kafka.producer.dto.Customer;

@Service
public class KafkaMessagePublisher {

	@Autowired
	private KafkaTemplate<String, Object> template;

	public void sendMessageToTopic(String message) {
		// if the topic does not exists then spring boot will create the topic for us by
		// using default kafka/config/server.properties
		CompletableFuture<SendResult<String, Object>> future = template.send("customTopic", message);
		future.whenComplete((result, ex) -> {
			if (ex == null) {
				System.out.println(
						"Send message = [" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
			} else {
				System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
			}
		});
	}

	public void sendEvetsToTopic(Customer customer) {
		try {
			CompletableFuture<SendResult<String, Object>> future = template.send("customTopic", customer);
			future.whenComplete((result, ex) -> {
				if (ex == null) {
					System.out.println("Send message = [" + customer + "] with offset=["
							+ result.getRecordMetadata().offset() + "]");
				} else {
					System.out.println("Unable to send message=[" + customer + "] due to : " + ex.getMessage());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
