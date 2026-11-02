package com.example.stage02.optional;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class OptionalDependencyDemo {

    private final Optional<ReceiptPrinter> optionalPrinter;
    private final List<ReceiptPrinter> printers;
    private final ReceiptPrinter nullablePrinter;
    private final ObjectProvider<ReceiptPrinter> provider;
    private ReceiptPrinter setterPrinter;

    public OptionalDependencyDemo(Optional<ReceiptPrinter> optionalPrinter,
                                  List<ReceiptPrinter> printers,
                                  @Nullable ReceiptPrinter nullablePrinter,
                                  ObjectProvider<ReceiptPrinter> provider) {
        this.optionalPrinter = optionalPrinter;
        this.printers = List.copyOf(printers);
        this.nullablePrinter = nullablePrinter;
        this.provider = provider;
    }

    @Autowired(required = false)
    public void setReceiptPrinter(ReceiptPrinter setterPrinter) {
        this.setterPrinter = setterPrinter;
    }

    public Optional<ReceiptPrinter> optionalPrinter() {
        return optionalPrinter;
    }

    public List<ReceiptPrinter> printers() {
        return printers;
    }

    public ReceiptPrinter nullablePrinter() {
        return nullablePrinter;
    }

    public ReceiptPrinter setterPrinter() {
        return setterPrinter;
    }

    public ObjectProvider<ReceiptPrinter> provider() {
        return provider;
    }
}
