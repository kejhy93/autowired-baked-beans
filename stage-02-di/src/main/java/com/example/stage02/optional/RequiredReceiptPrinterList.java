package com.example.stage02.optional;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RequiredReceiptPrinterList {

    @Autowired
    private List<ReceiptPrinter> printers;

    public List<ReceiptPrinter> printers() {
        return printers;
    }
}
