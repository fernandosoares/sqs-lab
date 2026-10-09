package dev.soares.sqs_lab.infrastructure.adapter.out.sqs;

import org.springframework.stereotype.Component;

import dev.soares.sqs_lab.application.port.out.OrderPublisherPort;
import dev.soares.sqs_lab.domain.model.Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Component 
public class OrderPublisher implements OrderPublisherPort {

  @Override
  public void publish(Order order) {
    // Implement the logic to publish the order to SQS here
    log.info("M=publish, order={}", order.toString());
    // Simulate publishing to SQS
    log.info("M=publish, order published to SQS successfully: {}", order.toString());
  }

}
