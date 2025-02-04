package com.paymentchain.billing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "InvoiceResponse", description = "Represents the details of an invoice in the system, including the unique invoice ID, the customer ID associated with the invoice, the invoice number, description, and the total amount of the invoice.")
@Data
public class InvoiceResponse {

    @Schema(name = "invoiceId", example = "2", defaultValue = "1", description = "The unique identifier of the invoice in the database.")
    private long invoiceId;

    @Schema(name = "customer", example = "2", defaultValue = "1", description = "The unique ID of the customer who owns the invoice.")
    private long customer;

    @Schema(name = "number", example = "3", defaultValue = "8", description = "The unique invoice number assigned to the physical invoice.")
    private String number;

    @Schema(name = "detail", description = "A description or additional details about the invoice.")
    private String detail;

    @Schema(name = "amount", example = "100.50", description = "The total amount of the invoice.")
    private double amount;

}
