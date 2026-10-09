package dev.soares.sqs_lab.application.usecase.command;

import java.math.BigDecimal;
import java.util.List;

public record OrderSubmitCommand(
  List<String> items,
  BigDecimal amount
) {}
