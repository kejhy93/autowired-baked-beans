# Stage 1: IoC and your first context

The same small app is wired three ways, so you can compare them directly.

| Variant | Package | Who decides the wiring |
| --- | --- | --- |
| By hand | `manual` | Your `main` method, using `new` |
| `@Bean` methods | `javaconfig` | Your config class, with Spring calling it |
| Component scanning | `scan` | Spring, from annotations on the classes |

`factory` compares a bare `BeanFactory` with an `ApplicationContext`.

## Run it

From the repo root:

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
4. In `KitchenConfig`, add a second `Stove` `@Bean` method called `backupStove`. (Stage 2 covers the fixes.)
5. In `BreakfastCook`, add a second constructor. What does Spring need from you now?
6. In `KitchenConfig`, remove `@Configuration` and run the tests. Does anything change? (Stage 7 explains what you lost.)
7. In `JavaConfigApp`, identify each printed bean name. Which ones didn't you define?

## Questions to answer

- What did you stop controlling when you moved from `manual` to `scan`?
- `@Repository`, `@Service`, and `@Component` all made beans here. What is the difference? (Hint: open their source.)
- When would you choose `@Bean` over `@Component`?
- Why do you normally use `ApplicationContext` rather than a bare `BeanFactory`?
