package com.example.stage02.optional;

public class PaperReceiptPrinter implements ReceiptPrinter {

    @Override
    public String print() {
        return "paper receipt";
    }
}
