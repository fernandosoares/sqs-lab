package dev.soares.sqs_lab.application.port.in;

import org.springframework.http.ResponseEntity;

import dev.soares.sqs_lab.application.usecase.command.OrderSubmitCommand;

public interface OrderSubmitUseCase {
  ResponseEntity<Void> submitOrder(OrderSubmitCommand command);
}
