package com.example.stage02.optional;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Receives one optional printer as Optional, nullable, and optional setter dependencies, plus a deferred provider.
 * The list and provider expose all matching printers rather than only one selected candidate.
 */
public class OptionalDependencyDemo {

    private final Optional<ReceiptPrinter> optionalPrinter;
    private final List<ReceiptPrinter> printers;
    private final ReceiptPrinter nullablePrinter;
    private final ObjectProvider<ReceiptPrinter> provider;
    private ReceiptPrinter setterPrinter;

    /**
     * Captures the same optional dependency through several Spring-supported injection contracts.
     */
    public OptionalDependencyDemo(Optional<ReceiptPrinter> optionalPrinter,
                                  List<ReceiptPrinter> printers,
                                  @Nullable ReceiptPrinter nullablePrinter,
                                  ObjectProvider<ReceiptPrinter> provider) {
        this.optionalPrinter = optionalPrinter;
        this.printers = List.copyOf(printers);
        this.nullablePrinter = nullablePrinter;
        this.provider = provider;
    }

    /** Spring calls this setter only when a printer candidate is available. */
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
