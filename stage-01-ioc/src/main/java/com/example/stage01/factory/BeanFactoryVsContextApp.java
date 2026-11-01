package com.example.stage01.factory;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Clock;

/**
 * Shows the two differences that matter most between a bare BeanFactory and an
 * AnnotationConfigApplicationContext: when singletons are created, and whether annotations
 * are processed. Eager creation comes from being an ApplicationContext, while the annotation
 * support comes from the processors that this particular context registers.
 */
public class BeanFactoryVsContextApp {

    public static void main(String[] args) {
        System.out.println("BeanFactory: registering the definition");
        DefaultListableBeanFactory factory = bareFactory();
        System.out.println("BeanFactory: asking for the bean");
        Probe fromFactory = factory.getBean(Probe.class);
        System.out.println("BeanFactory: @Autowired honoured? " + fromFactory.isWired());

        System.out.println();

        System.out.println("ApplicationContext: refreshing");
        try (var context = refreshedContext()) {
            System.out.println("ApplicationContext: asking for the bean");
            Probe fromContext = context.getBean(Probe.class);
            System.out.println("ApplicationContext: @Autowired honoured? " + fromContext.isWired());
        }
    }

    static DefaultListableBeanFactory bareFactory() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        // Available on purpose, so an unwired Probe can only mean @Autowired was ignored
        factory.registerSingleton("clock", Clock.systemUTC());
        factory.registerBeanDefinition("probe", new RootBeanDefinition(Probe.class));
        return factory;
    }

    static AnnotationConfigApplicationContext refreshedContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBean("clock", Clock.class, Clock::systemUTC);
        context.registerBean("probe", Probe.class);
        context.refresh();
        return context;
    }
}
