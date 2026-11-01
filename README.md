# autowired-baked-beans

```text
          )   )   )
         (   (   (
     _______________________
    (_______________________)
    |                       |
    |      @Autowired       |
    |      BAKED BEANS      |
    |                       |
    |  net wt: 1 singleton  |
    |_______________________|
    (_______________________)
```

[![Java CI with Maven](https://github.com/kejhy93/autowired-baked-beans/actions/workflows/maven.yml/badge.svg)](https://github.com/kejhy93/autowired-baked-beans/actions/workflows/maven.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

![Alt](https://repobeats.axiom.co/api/embed/707511dfe59a6a22c756f55201b3c12352ba9634.svg "Repobeats analytics image")

Experiments and examples exploring Spring Framework beans: dependency injection, scopes, lifecycle callbacks, conditional beans and BeanPostProcessors.

The repo teaches these topics one stage at a time, with one Maven module per stage. It uses plain `spring-context` until Stage 7, because Spring Boot hides much of what you're trying to learn.

## Start here

- **[Interactive guide](https://kejhy93.github.io/autowired-baked-beans/):** walks through Stage 1 on one page, with a code viewer and the exercises.
- **[Stage 1 README](stage-01-ioc/README.md):** the full guide to the first stage, including its commands, sample output, and exercises.
- **[Learning plan](LEARNING_PLAN.md):** what each stage covers and what you build in it.

## Run it

You need JDK 25 and Maven. From the repo root, run the tests and then the first example:

```bash
mvn -q test
mvn -q -pl stage-01-ioc compile exec:java -Dexec.mainClass=com.example.stage01.manual.ManualWiringApp
```

## Stages

| Stage | Topic | Status |
| --- | --- | --- |
| 1 | [IoC and your first context](stage-01-ioc/README.md) | Available |
| 2 | Dependency injection | Planned |
| 3 | Scopes | Planned |
| 4 | Bean lifecycle | Planned |
| 5 | Context features | Planned |
| 6 | Container extension points | Planned |
| 7 | Proxies and Spring Boot auto-configuration | Planned |
| 8 | Internals, testing, and performance | Planned |

## License

[MIT](LICENSE)
