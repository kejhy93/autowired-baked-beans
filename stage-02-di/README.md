# Stage 2: Dependency injection

This stage starts with three `PaymentProcessor` beans, all assignable to the same interface. Follow the examples to see how Spring narrows candidates for a single dependency, while still making every candidate available to collections and maps.

## Run it

You need JDK 25 and Maven. From the repository root:

```bash
mvn -q -pl stage-02-di test
mvn -q -pl stage-02-di compile exec:java -Dexec.mainClass=com.example.stage02.DependencyInjectionApp
```

The sample app prints:

```text
Default processor: card
Bank rail: bank transfer
All processors: card, bank transfer, voucher
Runtime route: paid by voucher
```

To run the whole repository build, use `mvn -B package`.

## The payment app

`PaymentProcessor` has three implementations:

| Bean name | Implementation | Candidate rule |
| --- | --- | --- |
| `cardPaymentProcessor` | `CardPaymentProcessor` | `@Primary`, `@PaymentRail("card")`, first in collection order |
| `bankTransferPaymentProcessor` | `BankTransferPaymentProcessor` | `@PaymentRail("bank")`, second in collection order |
| `voucherPaymentProcessor` | `VoucherPaymentProcessor` | `@Fallback`, third in collection order |

`PaymentConfig` wires a `PaymentRouter` using five forms of dependency: an unqualified `PaymentProcessor`, two qualifier forms, `List<PaymentProcessor>`, and `Map<String, PaymentProcessor>`. `@Primary` supplies the default for a single unqualified dependency. The qualifiers narrow the candidates first, so the bank processor can be selected even though the card processor is primary. The custom `@PaymentRail` annotation is itself meta-annotated with `@Qualifier`.

The map keys are the bean names. `PaymentRouter.route(...)` uses that map to choose an implementation at runtime. `@Primary` and `@Fallback` affect single-bean resolution, not collection membership: all three implementations remain in the list and map. `@Order` sets the list order.

`@Fallback` is not another default marker. It is considered only when no regular candidate is available. `PaymentConfig.FallbackOnly` imports just the voucher implementation to demonstrate that case. If two candidates are marked `@Primary`, Spring reports an ambiguity rather than choosing one; `MultiplePrimaryTest` captures the exception and its candidate names.

## Injection styles

`injection/ConstructorInjectedCheckout`, `SetterInjectedCheckout`, and `FieldInjectedCheckout` each receive a `PaymentProcessor` in a different way. `InjectionStylesTest` creates and exercises all three without starting Spring.

Constructor injection makes the dependency explicit at creation time and allows a `final` field. A caller cannot create the object in an incomplete state, and a unit test can pass a dependency directly. Setter injection is useful when a dependency is optional or can be reconfigured. Field injection hides the dependency from the constructor and makes a plain unit test reach into the field; production code normally uses package-private or private fields plus Spring's annotation processing.

## Resolving candidates

Spring starts with beans assignable to the requested type, then narrows the candidates:

1. A qualifier such as `@Qualifier("bankTransferPaymentProcessor")` or `@PaymentRail("bank")` filters the candidates by semantic qualifier metadata. A bean name can be used as a qualifier value; it is still a filter, not a general bean lookup.
2. `@Primary` marks the preferred regular candidate when a single unqualified bean is needed.
3. `@Fallback` marks a candidate used when no regular candidate is available.
4. If no qualifier, primary, or fallback indicator resolves a non-unique dependency, Spring may match the injection point's parameter name to a bean name. That depends on compiler metadata and should not be the first choice for important wiring.

The `ParameterNameMatchingTest` intentionally has two candidates and no primary. By default, the test observes a `NoUniqueBeanDefinitionException` listing both beans. Compile with parameter names to make the method parameter `bankTransferPaymentProcessor` match that bean:

```bash
mvn -q -pl stage-02-di clean test
mvn -q -pl stage-02-di clean test -Dmaven.compiler.parameters=true
```

Both runs pass because the test checks which behavior the compiler option enables. The second command is a clean build so the classes are recompiled with `-parameters`.

`GenericRepositoryResolutionTest` also demonstrates generic type matching: Spring injects `Repository<User>` when a `Repository<PurchaseOrder>` is present too. The generic argument is part of the dependency type, so the candidates are not ambiguous.

## Collections, optional dependencies, and deferred lookup

An injected `List<PaymentProcessor>` contains all matching processors in `@Order` order. An injected `Map<String, PaymentProcessor>` contains all of them under their bean names. Collections are not reduced to the primary bean.

`OptionalDependencyDemo` demonstrates four optional forms with a `ReceiptPrinter`:

- `Optional<ReceiptPrinter>` is empty when none exists.
- A `@Nullable ReceiptPrinter` parameter is `null` when none exists.
- An `@Autowired(required = false)` setter is not called when none exists.
- `ObjectProvider<ReceiptPrinter>` can be kept and queried later with `getIfAvailable()`, `getIfUnique()`, or `stream()`.

The provider postpones candidate resolution until a lookup method is called. `PaymentLookupTest` contrasts a missing dependency—which would fail a direct `getBean(PaymentProcessor.class)` lookup—with a provider that lets the application start and safely returns no candidate.

`CollectionInjectionTest` covers the collection edge case: a required `@Autowired List<ReceiptPrinter>` field fails when there are no matching beans, while the same field with `required = false` remains unset. A `List<ReceiptPrinter>` argument to a `@Bean` method is a different case and receives an empty list when there are no candidates; `OptionalDependenciesTest` checks this behavior.

## What the tests show

| Test | What it proves |
| --- | --- |
| `CandidateResolutionTest` | Primary, bean-name and custom qualifiers, ordered list, name-keyed map, and runtime routing |
| `QualifierFailureTest` | A qualifier with no matching candidate fails context startup |
| `MultiplePrimaryTest` | Two `@Primary` candidates remain ambiguous; the exception names both |
| `ParameterNameMatchingTest` | `-parameters` enables matching an injection parameter to its bean name |
| `GenericRepositoryResolutionTest` | Generic arguments distinguish repository candidates |
| `OptionalDependenciesTest` | `Optional`, `@Nullable`, optional setter, empty/populated list, and `ObjectProvider` |
| `CollectionInjectionTest` | Required and non-required autowired lists with no matching beans |
| `PaymentLookupTest` | Direct missing lookup versus deferred `ObjectProvider` lookup |
| `InjectionStylesTest` | The three injection styles can be tested by constructing the classes directly |

## Experiments

Predict each result before running the test or changing the example.

1. Mark `BankTransferPaymentProcessor` with `@Primary` too. Start `PaymentConfig`: the unqualified dependency is ambiguous because there are two primary beans.
2. In `RequiredReceiptPrinterList`, remove the only `ReceiptPrinter` bean. The required field fails during context startup. Repeat with `OptionalReceiptPrinterList`: startup succeeds and the field remains `null`.
3. Add another `PaymentProcessor` to `PaymentConfig`. The injected list and map include it; `@Primary` does not remove it.
4. Change the string in the bean-name `@Qualifier` on `PaymentConfig.paymentRouter` to a name that does not exist. Startup fails because no candidate matches that qualifier.
5. Change the repository test to inject `Repository<PurchaseOrder>`. Then try a raw `Repository` dependency with both implementations; the generic information no longer narrows the candidates.
6. Remove `-Dmaven.compiler.parameters=true` from the second parameter-name command, and compare the failure details with the successful name match.
7. Remove `@Primary` from the card implementation while both regular processors remain. `@Fallback` does not resolve the ambiguity; the voucher is selected only in the fallback-only configuration.

## Key terms

- **Injection point:** a constructor parameter, setter parameter, or field where Spring supplies a dependency.
- **Candidate:** a bean assignable to the requested type before qualifiers and other resolution rules narrow the set.
- **`@Primary`:** preference among otherwise suitable candidates for one dependency.
- **`@Fallback`:** a candidate used when no regular candidate is available.
- **Qualifier:** metadata that narrows candidates by a bean name or a semantic label. A qualifier need not be globally unique by itself.
- **Parameter-name matching:** a fallback that uses a retained constructor or method parameter name to match a bean name; it requires the compiler's `-parameters` option.
- **`ObjectProvider<T>`:** a handle for querying candidates later, including safe optional and unique lookups and a stream of all matches.

## Done when you can explain

- Why constructor injection makes required dependencies explicit and plain unit tests simple.
- Why setter injection can fit an optional or reconfigurable dependency, and why field injection hides dependencies.
- How type matching, qualifiers, primary/fallback status, and parameter names affect a single dependency.
- Why two primary candidates are ambiguous, and why a fallback is not chosen while regular candidates exist.
- Why `@Primary` does not change the contents or ordering of a collection injection.
- What names appear as keys in `Map<String, T>`.
- What `ObjectProvider<T>` can do that `Optional<T>` cannot, and when the lookup occurs.
- Why parameter-name matching can break when compiler configuration changes.
