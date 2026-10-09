package dev.soares.sqs_lab.application.port.out;
import dev.soares.sqs_lab.domain.model.Order;

public interface OrderPublisherPort {
  public void publish(Order order);
}
