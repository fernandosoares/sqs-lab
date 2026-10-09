package dev.soares.sqs_lab.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record OrderSubmitRequest(
    @NotEmpty (message = "Items must not be blank") List<String> items,
    @Positive(message = "Amount must be positive") BigDecimal amount
) {}
