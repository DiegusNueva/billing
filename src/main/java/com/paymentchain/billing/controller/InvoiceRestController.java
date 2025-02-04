/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.billing.controller;

import com.paymentchain.billing.common.InvoiceRequestMapper;
import com.paymentchain.billing.common.InvoiceResponseMapper;
import com.paymentchain.billing.dto.InvoiceRequest;
import com.paymentchain.billing.dto.InvoiceResponse;
import com.paymentchain.billing.entities.Invoice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.paymentchain.billing.respository.InvoiceRepository;

import java.util.Optional;

import org.springframework.http.HttpStatus;


@Tag(name = "Billing API", description = "This API server all functionality for management invoices")
@RestController
@RequestMapping("/billing")
public class InvoiceRestController {

    @Autowired
    InvoiceRepository billingRepository;

    @Autowired
    InvoiceRequestMapper invoiceRequestMapperDto;

    @Autowired
    InvoiceResponseMapper invoiceResponseMapperDto;

    @Operation(description = "Return all invoices bundled into response", summary = "Return 204 if no data found")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Éxito")})
    @GetMapping()
    public List<InvoiceResponse> list() {

        List<Invoice> findAll = billingRepository.findAll();
        return invoiceResponseMapperDto.InvoiceListToInvoiceResposeList(findAll);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id) {
        Optional<Invoice> invoice = billingRepository.findById(id);
        if (invoice.isPresent()) {
            InvoiceResponse invoiceResponse = invoiceResponseMapperDto.InvoiceToInvoiceRespose(invoice.get());
            return new ResponseEntity<>(invoiceResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody InvoiceRequest input) {
        Optional<Invoice> entity = billingRepository.findById(Long.valueOf(id));
        if (!entity.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Invoice invoiceToUpdate = entity.get();
        Invoice updatedInvoice = invoiceRequestMapperDto.InvoiceRequestToInvoice(input);
        updatedInvoice.setId(invoiceToUpdate.getId());

        Invoice savedInvoice = billingRepository.save(updatedInvoice);


        InvoiceResponse response = invoiceResponseMapperDto.InvoiceToInvoiceRespose(savedInvoice);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody InvoiceRequest input) {
        Invoice invoiceRequestToInvoice = invoiceRequestMapperDto.InvoiceRequestToInvoice(input);
        Invoice save = billingRepository.save(invoiceRequestToInvoice);
        InvoiceResponse invoiceToInvoiceResponse = invoiceResponseMapperDto.InvoiceToInvoiceRespose(save);
        return ResponseEntity.ok(invoiceToInvoiceResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Optional<Invoice> entity = billingRepository.findById(Long.valueOf(id));
        if (!entity.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        InvoiceResponse invoiceResponse = invoiceResponseMapperDto.InvoiceToInvoiceRespose(entity.get());
        billingRepository.delete(entity.get());
        return ResponseEntity.ok(invoiceResponse);
    }


}
