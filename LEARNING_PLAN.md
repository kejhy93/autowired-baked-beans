# Spring beans and context learning plan

Each stage has an experiment you can build in this repo, one Maven module per stage. Use plain `spring-context` until Stage 7, because Spring Boot hides much of what you're trying to learn. At a few hours a week, the plan should take about 8–10 weeks.

## Basics

### Stage 1: IoC and your first context (week 1)
- **Learn:** inversion of control vs. dependency injection, what a bean is, `BeanFactory` vs. `ApplicationContext`, and `AnnotationConfigApplicationContext`.
- **Learn:** `@Component` and its stereotypes, `@ComponentScan`, `@Configuration`, and `@Bean`.
- **Build:** wire a small app by hand with `new`, then rewrite it with Spring and compare the two.

### Stage 2: Dependency injection (week 2)
- **Learn:** constructor, setter, and field injection, and why constructor injection is preferred.
- **Learn:** resolving multiple candidates with `@Primary`, `@Qualifier`, and `@Fallback`, including matching by parameter name.
- **Learn:** injecting `List<T>` and `Map<String, T>`, optional dependencies, and `ObjectProvider<T>`.
- **Build:** create a payment interface with three implementations, then trigger `NoUniqueBeanDefinitionException` and fix it each way.

### Stage 3: Scopes (week 3)
- **Learn:** singleton, prototype, and the web scopes (request and session).
- **Learn:** the prototype-into-singleton problem and its fixes: `ObjectProvider`, `@Lookup`, and scoped proxies.
- **Build:** log identity hash codes to show the prototype being "frozen" inside a singleton, then apply each fix.

## Intermediate

### Stage 4: Bean lifecycle (week 4)
- **Learn:** the order of instantiation, property population, `Aware` callbacks, `@PostConstruct`, `afterPropertiesSet`, and custom init methods.
- **Learn:** the shutdown callbacks, `SmartInitializingSingleton`, and `SmartLifecycle`.
- **Build:** create one bean that implements every callback and logs each one, predict the order, then check your prediction.

### Stage 5: Context features (week 5)
- **Learn:** `Environment`, `PropertySource`, `@Value`, and `@Profile`.
- **Learn:** `@Conditional` and writing a custom `Condition`.
- **Learn:** `@EventListener`, async events, and context events such as `ContextRefreshedEvent`.
- **Learn:** `ResourceLoader` and parent/child context hierarchies.
- **Build:** add a custom `Condition` that registers a bean based on a property, plus a parent/child context that shows which beans are visible where.

## Advanced

### Stage 6: Container extension points (weeks 6–7)
This is the most important stage, because most of Spring's "magic" is built on these mechanisms.
- **Learn:** `BeanDefinition` and the difference between a bean's definition and its instance.
- **Learn:** `BeanFactoryPostProcessor` and `BeanDefinitionRegistryPostProcessor`.
- **Learn:** `BeanPostProcessor`, including why these beans are created early and why `@Bean` methods for post-processors are often `static`.
- **Learn:** `FactoryBean` and the `&beanName` lookup.
- **Learn:** `@Import`, `ImportSelector`, `DeferredImportSelector`, `ImportBeanDefinitionRegistrar`, and `BeanRegistrar` (new in Spring Framework 7).
- **Build:**
  - A custom `@Timed` annotation that a `BeanPostProcessor` handles.
  - An `@EnableX` annotation.
  - An `@HttpClient`-style interface scanner that uses `FactoryBean`, as Feign does.

### Stage 7: Proxies and Spring Boot auto-configuration (week 8)
- **Learn:** JDK vs. CGLIB proxies, and the auto-proxy creator that wraps beans during post-processing.
- **Learn:** the self-invocation trap with `@Transactional` and `@Async`.
- **Learn:** `@Configuration(proxyBeanMethods = false)` and how it changes `@Bean` method calls.
- **Learn:** `@AutoConfiguration`, the `AutoConfiguration.imports` file, and `@ConditionalOnMissingBean`.
- **Build:** write your own Boot starter and test it with `ApplicationContextRunner`, and reproduce the self-invocation bug.

### Stage 8: Internals, testing, and performance (weeks 9–10)
- **Learn:** read `AbstractApplicationContext.refresh()` step by step in a debugger.
- **Learn:** `doCreateBean` in `AbstractAutowireCapableBeanFactory`.
- **Learn:** circular references, the three-level singleton cache, and why Boot prohibits them by default.
- **Learn:** test context caching, what invalidates it, and `@MockitoBean`.
- **Learn:** startup performance: lazy initialization, background bean initialization, `ApplicationStartup`, AOT processing, and GraalVM native image limitations.
- **Build:** map every `refresh()` step to the callback logs from Stage 4, then measure how lazy and background initialization change the startup time of an app with slow beans.

## Check your understanding
By the end, you should be able to answer these without looking them up:
1. Why doesn't `@Transactional` work when a method calls another method in the same class?
2. Why might a `BeanPostProcessor` fail to process some beans?
3. What happens when one `@Bean` method calls another, with and without `proxyBeanMethods`?
4. Why does a `@Scope("prototype")` bean sometimes behave like a singleton?
5. How does a Boot starter back off when you define your own bean?
6. In what order do the `refresh()` steps run, and where in that order are beans instantiated?

## Resources
- **Main reference:** the [Core Technologies](https://docs.spring.io/spring-framework/reference/core.html) section of the Spring Framework docs, especially "The IoC Container". Read the matching part as you start each stage.
- **Source code:** in IntelliJ, set breakpoints in Spring's own classes, which helps most in Stages 6 and 8.
- **Auto-configuration examples:** the Spring Boot source, which shows real uses of these patterns.

Your Java 25 and Maven 3.9 setup is fine for this. Start with Spring Framework 7 so you can try `BeanRegistrar`, and begin with the Stage 1 hand-wired app.
