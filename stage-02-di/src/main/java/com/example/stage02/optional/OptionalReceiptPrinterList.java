package com.example.stage02.optional;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OptionalReceiptPrinterList {

    @Autowired(required = false)
    private List<ReceiptPrinter> printers;

    public List<ReceiptPrinter> printers() {
        return printers;
    }
}
