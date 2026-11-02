package com.example.stage02.optional;

/** A simple concrete printer used to show how optional injection behaves when a candidate exists. */
public class PaperReceiptPrinter implements ReceiptPrinter {

    @Override
    public String print() {
        return "paper receipt";
    }
}
