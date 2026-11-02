package com.example.stage02.repository;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Demonstrates that generic arguments distinguish two beans implementing the same repository interface. */
class GenericRepositoryResolutionTest {

    @Test
    void genericArgumentNarrowsRepositoryCandidatesByType() {
        try (var context = new AnnotationConfigApplicationContext(RepositoryConfig.class)) {
            RepositoryClient client = context.getBean(RepositoryClient.class);

            assertEquals(new User("Ada"), client.userRepository().find());
        }
    }

    @Configuration(proxyBeanMethods = false)
    @Import({UserRepository.class, PurchaseOrderRepository.class})
    static class RepositoryConfig {

        @Bean
        RepositoryClient repositoryClient(Repository<User> userRepository) {
            return new RepositoryClient(userRepository);
        }
    }

    record User(String name) {
    }

    record PurchaseOrder(String id) {
    }

    interface Repository<T> {
        T find();
    }

    static class UserRepository implements Repository<User> {
        @Override
        public User find() {
            return new User("Ada");
        }
    }

    static class PurchaseOrderRepository implements Repository<PurchaseOrder> {
        @Override
        public PurchaseOrder find() {
            return new PurchaseOrder("order-1");
        }
    }

    record RepositoryClient(Repository<User> userRepository) {
    }
}
