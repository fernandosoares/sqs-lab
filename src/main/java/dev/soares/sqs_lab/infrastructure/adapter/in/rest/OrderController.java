package dev.soares.sqs_lab.infrastructure.adapter.in.rest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.soares.sqs_lab.application.port.in.OrderSubmitUseCase;
import dev.soares.sqs_lab.application.usecase.command.OrderSubmitCommand;
import dev.soares.sqs_lab.infrastructure.adapter.in.rest.dto.OrderSubmitRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor 
@Slf4j 
@RestController 
@RequestMapping("/orders")
public class OrderController {

  private final OrderSubmitUseCase orderSubmitUseCase;

  @ResponseStatus (HttpStatus.ACCEPTED)
  @PostMapping("/submit")
  public void submitOrder(@Valid @RequestBody OrderSubmitRequest orderRequest) {
    log.info("M=submitOrder, orderRequest={}", orderRequest);

    OrderSubmitCommand command = new OrderSubmitCommand(
      orderRequest.items(),
      orderRequest.amount()
    );
    log.info("M=submitOrder, command={}", command);

    orderSubmitUseCase.submitOrder(command);
    log.info("M=submitOrder, order submitted successfully");
  }
}
