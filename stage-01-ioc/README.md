# Stage 1: IoC and your first context

The same small app is wired three ways, so you can compare them directly.

| Variant | Package | Who decides the wiring |
| --- | --- | --- |
| By hand | `manual` | Your `main` method, using `new` |
| `@Bean` methods | `javaconfig` | Your config class, with Spring calling it |
| Component scanning | `scan` | Spring, from annotations on the classes |

`manual` and `javaconfig` share the Spring-free classes in `plain`. `scan` has its own annotated copies, which are duplicated on purpose so you can compare the two side by side.

`factory` compares a bare `BeanFactory` with an `AnnotationConfigApplicationContext`.

## Run it

You need JDK 25 and Maven. From the repo root:

```bash
mvn -q test
mvn -q -pl stage-01-ioc compile exec:java -Dexec.mainClass=com.example.stage01.manual.ManualWiringApp
mvn -q -pl stage-01-ioc compile exec:java -Dexec.mainClass=com.example.stage01.javaconfig.JavaConfigApp
mvn -q -pl stage-01-ioc compile exec:java -Dexec.mainClass=com.example.stage01.scan.ScanApp
mvn -q -pl stage-01-ioc compile exec:java -Dexec.mainClass=com.example.stage01.factory.BeanFactoryVsContextApp
```

You can also run each `main` method from IntelliJ.

## Exercises

Predict the result of each one before you run it.

1. In `scan/Stove.java`, remove `@Component`. Read the whole exception message.
2. In `ScanConfig`, change the scan to `@ComponentScan("com.example.stage01.plain")`.
3. In `KitchenConfig`, rename `pantry()` to `larder()`. Does injection still work? What is the bean called now?
4. In `KitchenConfig`, add a second `Stove` `@Bean` method called `backupStove`. (Stage 2 covers the fixes.) Then run `mvn -q clean test -Dmaven.compiler.parameters=true`. Why does the same code work now? (Hint: look at the parameter names of `breakfastCook(...)`.)
5. In `scan/BreakfastCook.java`, add a second constructor that takes only `Pantry` and `Stove`. What does Spring need from you now? Then replace it with a no-argument constructor that calls `this(new Pantry(), new Stove(), Clock.systemUTC())`. Why do the tests pass, and who chose the dependencies? Finally, add a second constructor to `plain/BreakfastCook.java`. Why does nothing change?
6. In `KitchenConfig`, remove `@Configuration` and run the tests. Does anything change? (Stage 7 explains what you lost.)
7. In `JavaConfigApp`, identify each printed bean name. Which ones didn't you define?
8. In `ScanConfig`, change the scan to `@ComponentScan("com.example.stage01")`. What does it find outside `scan`, and what happens to the bean called `breakfastCook`?
9. In `BeanFactoryVsContextApp.bareFactory()`, call `factory.preInstantiateSingletons()` just before the `return`, and run the app. What moved in the output? Then, before that call, add `var processor = new AutowiredAnnotationBeanPostProcessor();`, `processor.setBeanFactory(factory);` and `factory.addBeanPostProcessor(processor);`. What is still different from a context?
10. In `BeanFactoryVsContextApp.refreshedContext()`, replace `AnnotationConfigApplicationContext` with `GenericApplicationContext`, including the return type. Which of the two differences remains?

## Questions to answer

- What did you stop controlling when you moved from `manual` to `scan`?
- `@Repository`, `@Service`, and `@Component` all made beans here. What is the difference? (Hint: open their source.)
- When would you choose `@Bean` over `@Component`?
- Why do you normally use `ApplicationContext` rather than a bare `BeanFactory`?
