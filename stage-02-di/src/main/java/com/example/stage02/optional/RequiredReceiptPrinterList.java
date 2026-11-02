package com.example.stage02.optional;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/** Contrasts with {@link OptionalReceiptPrinterList}: this required field makes refresh fail with no candidates. */
public class RequiredReceiptPrinterList {

    /** This required injection point has no matching bean in {@link RequiredListConfig}. */
    @Autowired
    private List<ReceiptPrinter> printers;

    public List<ReceiptPrinter> printers() {
        return printers;
    }
}
