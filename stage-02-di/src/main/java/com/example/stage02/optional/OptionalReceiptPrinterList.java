package com.example.stage02.optional;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/** Keeps an optional collection unset when no receipt printers are registered. */
public class OptionalReceiptPrinterList {

    /** With {@code required = false}, Spring skips injection instead of failing on an empty candidate set. */
    @Autowired(required = false)
    private List<ReceiptPrinter> printers;

    public List<ReceiptPrinter> printers() {
        return printers;
    }
}
