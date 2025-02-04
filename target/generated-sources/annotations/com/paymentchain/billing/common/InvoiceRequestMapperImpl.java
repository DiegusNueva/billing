package com.paymentchain.billing.common;

import com.paymentchain.billing.dto.InvoiceRequest;
import com.paymentchain.billing.entities.Invoice;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-16T12:25:33+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 22.0.2 (Eclipse Adoptium)"
)
@Component
public class InvoiceRequestMapperImpl implements InvoiceRequestMapper {

    @Override
    public Invoice InvoiceRequestToInvoice(InvoiceRequest source) {
        if ( source == null ) {
            return null;
        }

        Invoice invoice = new Invoice();

        invoice.setCustomerId( source.getCustomer() );
        invoice.setNumber( source.getNumber() );
        invoice.setDetail( source.getDetail() );
        invoice.setAmount( source.getAmount() );

        return invoice;
    }

    @Override
    public List<Invoice> InvoiceRequestListToInvoiceList(List<InvoiceRequest> source) {
        if ( source == null ) {
            return null;
        }

        List<Invoice> list = new ArrayList<Invoice>( source.size() );
        for ( InvoiceRequest invoiceRequest : source ) {
            list.add( InvoiceRequestToInvoice( invoiceRequest ) );
        }

        return list;
    }

    @Override
    public InvoiceRequest InvoiceToInvoiceRequest(Invoice source) {
        if ( source == null ) {
            return null;
        }

        InvoiceRequest invoiceRequest = new InvoiceRequest();

        invoiceRequest.setCustomer( source.getCustomerId() );
        invoiceRequest.setNumber( source.getNumber() );
        invoiceRequest.setDetail( source.getDetail() );
        invoiceRequest.setAmount( source.getAmount() );

        return invoiceRequest;
    }

    @Override
    public List<InvoiceRequest> InvoiceListToInvoiceRequestList(List<Invoice> source) {
        if ( source == null ) {
            return null;
        }

        List<InvoiceRequest> list = new ArrayList<InvoiceRequest>( source.size() );
        for ( Invoice invoice : source ) {
            list.add( InvoiceToInvoiceRequest( invoice ) );
        }

        return list;
    }
}
