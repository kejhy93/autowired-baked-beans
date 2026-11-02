package com.example.stage02.payment;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Qualifies a payment processor by business rail rather than by its implementation class.
 * The {@link Qualifier} meta-annotation makes Spring use the value to narrow type-matched candidates.
 */
@Target({FIELD, METHOD, PARAMETER, TYPE})
@Retention(RUNTIME)
@Documented
@Qualifier
public @interface PaymentRail {

    String value();
}
