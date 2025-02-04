package com.paymentchain.billing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "InvoiceRequest", description = "Represents the data required to create or update an invoice. This includes the customer's unique identifier, the invoice number, a description of the invoice, and the total amount to be billed.")
@Data
public class InvoiceRequest {

    @Schema(name = "customer", example = "2", defaultValue = "1", description = "The unique identifier of the customer who owns the invoice. This field is required for creating or updating an invoice and represents the relationship between the customer and the invoice.")
    private long customer;

    @Schema(name = "number", example = "3", defaultValue = "8", description = "The unique number assigned to the physical invoice. This field is required and helps in identifying the invoice in the real world.")
    private String number;

    @Schema(name = "detail", description = "Additional information about the invoice. This can include any relevant description, such as the services or goods billed, or any special instructions related to the invoice. This field is optional.")
    private String detail;

    @Schema(name = "amount", example = "100.50", description = "The total amount to be billed for the invoice. This field is required and should reflect the monetary value of the transaction.")
    private double amount;
}

