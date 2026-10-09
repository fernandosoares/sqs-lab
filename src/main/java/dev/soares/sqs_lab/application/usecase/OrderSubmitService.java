package dev.soares.sqs_lab.application.usecase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.soares.sqs_lab.application.port.in.OrderSubmitUseCase;
import dev.soares.sqs_lab.application.port.out.OrderPublisherPort;
import dev.soares.sqs_lab.application.usecase.command.OrderSubmitCommand;
import dev.soares.sqs_lab.domain.model.Order;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderSubmitService implements OrderSubmitUseCase {

  private final OrderPublisherPort orderPublisherPort;

  @Override
  public ResponseEntity<Void> submitOrder(OrderSubmitCommand command) {

    Order order = new Order(
      java.util.UUID.randomUUID().toString(),
      command.items(),
      command.amount()
    );

    orderPublisherPort.publish(order);
    return ResponseEntity.accepted().build();
  }
}
