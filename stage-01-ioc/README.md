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

## The app

`BreakfastCook` takes food from a `Pantry`, heats it on a `Stove`, and uses a `Clock` to report when it was served:

```text
BreakfastCook
├── Pantry
├── Stove
└── Clock (from the JDK)
```

Every variant builds these same four objects and prints the same kind of line. Only the code that creates and connects them changes.

## Key terms

- **Inversion of control (IoC):** something other than your business code controls a job that your code would otherwise do itself. In this stage, that job is creating objects and connecting them.
- **Dependency injection (DI):** one way to apply IoC. A class declares what it needs, usually as constructor parameters, and whoever creates it supplies those things. `BreakfastCook` does this without importing anything from Spring.
- **Container:** the Spring object that creates and manages your objects. `BeanFactory` is its basic interface, and `ApplicationContext` builds on it.
- **Bean:** an object that the container creates, configures, and manages. A `Pantry` you create with `new` in `main` is not a bean, even though the class is the same.
- **Bean definition:** the container's recipe for a bean, such as its class, how to create it, and what it depends on. The container collects definitions first and creates objects later.

## What happens in each variant

### By hand: `ManualWiringApp`

`main` does all the work in order: it creates the `Pantry`, `Stove`, and `Clock`, then passes them to the `BreakfastCook` constructor. The dependencies must be created first, because the constructor call needs them. There is no container, so nothing here is a bean.

```text
hot baked beans, served at 11:08:03
```

The time in this and later output will differ when you run it.

### `@Bean` methods: `JavaConfigApp`

`new AnnotationConfigApplicationContext(KitchenConfig.class)` starts the container, and the container goes through these steps:

1. **Registers its own infrastructure.** This context adds definitions for the processors that understand annotations, and then a definition for `KitchenConfig`.
2. **Reads your configuration.** A processor finds the `@Bean` methods in `KitchenConfig` and adds a definition for each one. At this point, none of your objects exist yet.
3. **Prepares for bean creation.** The processors that act on each new bean, such as the one that handles `@Autowired`, are set up.
4. **Creates the singletons.** Spring calls your `@Bean` methods. The parameters of `breakfastCook(Pantry pantry, Stove stove, Clock clock)` tell Spring what to pass in, and it resolves each one by type.

Steps 2 to 4 are all part of `refresh()`, which this constructor calls for you. When the constructor returns, the app is already wired, so `getBean` only retrieves the existing `BreakfastCook`. Asking for it again returns the same object, because beans are singletons by default.

The app then prints every bean definition name:

```text
hot baked beans, served at 11:08:09
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
kitchenConfig
pantry
stove
clock
breakfastCook
```

The first four names belong to Spring:

| Bean name ends in | What it does |
| --- | --- |
| `internalConfigurationAnnotationProcessor` | Reads `@Configuration`, `@Bean`, and `@ComponentScan` (step 2) |
| `internalAutowiredAnnotationProcessor` | Performs `@Autowired` injection on new beans |
| `internalEventListenerProcessor` | Finds `@EventListener` methods |
| `internalEventListenerFactory` | Creates the listeners for those methods |

`kitchenConfig` is there because the configuration class is a bean too. Spring needs an instance of it to call its `@Bean` methods.

The classes in `plain` never mention Spring. All the Spring-specific wiring lives in `KitchenConfig`, which is also why this style works for `Clock`, a class you can't annotate.

### Component scanning: `ScanApp`

The startup steps are the same, but step 2 finds the beans differently. `@ComponentScan` on `ScanConfig` searches the `scan` package and its subpackages for classes annotated with `@Component` or a stereotype such as `@Service`, and adds a definition for each one. You no longer write a method that calls the constructor, so Spring picks one itself. `scan.BreakfastCook` has only one constructor, so Spring uses it and resolves its parameters by type.

```text
hot baked beans, served at 11:08:14
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
scanConfig
breakfastCook
pantry
stove
clock
```

There are two things to notice in this output:

- **`clock` comes last.** The scan runs while Spring is reading `ScanConfig`, and the definitions for its `@Bean` methods are added afterwards.
- **`breakfastCook` comes before its dependencies.** That's fine, because this is only the list of definitions. When Spring creates `breakfastCook`, it first creates whatever that bean still needs. This is the ordering work you did by hand in `ManualWiringApp`.

Compare the bean names with the class and method names in each variant. Exercise 3 tests the naming rule.

### `BeanFactory` and `ApplicationContext`: `BeanFactoryVsContextApp`

This demo registers the same `Probe` class in both containers. `Probe` prints a line from its constructor, and `isWired()` reports whether its `@Autowired` setter was called.

```text
BeanFactory: registering the definition
BeanFactory: asking for the bean
  -> Probe constructor called
BeanFactory: @Autowired honoured? false

ApplicationContext: refreshing
  -> Probe constructor called
ApplicationContext: asking for the bean
ApplicationContext: @Autowired honoured? true
```

Look at where the constructor line appears in each half:

- **The bare factory is lazy.** It creates `Probe` only when `getBean` asks for it.
- **The context is eager.** It creates `Probe` during `refresh()`, before anything asks for it. A broken configuration therefore fails at startup, not later when a bean is first requested.

The `@Autowired` line differs because annotations don't do anything on their own. `@Autowired` is only metadata, and a processor has to act on it. `AnnotationConfigApplicationContext` registers that processor, but a bare `DefaultListableBeanFactory` doesn't. The factory does have a `clock` available, so an unwired `Probe` can only mean that the annotation was ignored.

`Probe` uses setter injection because, by default, a bare factory can't supply constructor arguments. With constructor injection, the demo would fail before it could report anything.

## Why the code looks like this

- **`getBean` appears only in `main`.** A `main` method needs a starting point, because nothing injects into it. Everywhere else, classes declare their dependencies and receive them. That keeps them testable and free of Spring lookups.
- **The context is opened with try-with-resources.** Closing it shuts down the container and destroys its singletons. Stage 4 covers what your beans can do at that moment.
- **The clock is injected.** A class that called `LocalTime.now()` directly would print a different time on every run. Injecting a `Clock` lets a test supply a fixed time and check the exact output.

## What the tests show

| Test | What it proves |
| --- | --- |
| `BreakfastCookTest` | Plain DI needs no container: the test calls the constructor and passes in a fixed clock |
| `WiringTest.javaConfigWiresTheCook` | `KitchenConfig` produces a working `BreakfastCook` |
| `WiringTest.componentScanWiresTheCook` | `ScanConfig` produces a working `scan.BreakfastCook` |
| `WiringTest.beansAreSingletonsByDefault` | Two `getBean` calls return the same object |
| `WiringTest.scanOnlyCoversTheConfigPackage` | `KitchenConfig` is annotated, but it isn't found because it's outside the `scan` package |
| `BeanFactoryVsContextTest` | The lazy and eager creation, and the `@Autowired` difference, from the demo above |

The first two `WiringTest` tests need a predictable time, so `contextAtEightOClock` adds a `BeanPostProcessor` that swaps each `Clock` bean for a fixed one as it's created. This is an early look at the container's extension points, which Stage 6 covers. The config must still define a clock, so these tests fail if it stops doing so.

## Exercises

Predict the result of each one before you run it.

1. In `scan/Stove.java`, remove `@Component`. Read the whole exception message.
2. In `ScanConfig`, change the scan to `@ComponentScan("com.example.stage01.plain")`.
3. In `KitchenConfig`, rename `pantry()` to `larder()`. Does injection still work? What is the bean called now?
4. In `KitchenConfig`, add a second `Stove` `@Bean` method called `backupStove`. (Stage 2 covers the fixes.) Then run `mvn -q clean test -Dmaven.compiler.parameters=true`. Why does the same code work now? (Hint: look at the parameter names of `breakfastCook(...)`.)
5. In `scan/BreakfastCook.java`, add a second constructor that takes only `Pantry` and `Stove`. What does Spring need from you now? Then replace it with a no-argument constructor that calls `this(new Pantry(), new Stove(), Clock.systemUTC())`. Why does `componentScanWiresTheCook` fail now, and who chose the dependencies? Finally, add a second constructor to `plain/BreakfastCook.java`. Why does nothing change?
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
