package org.dalloz.kata.simpleusermanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dalloz.kata.simpleusermanager.domain.User;
import org.dalloz.kata.simpleusermanager.infrastructure.PostgresUserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Testcontainers
class SpringConfigurationIT {


    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:15-alpine")
            .withDatabaseName("integration-user-db")
            .withUsername("sa")
            .withPassword("sa");

    public static final ObjectMapper objectMapper = new ObjectMapper();

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }


    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Autowired
    PostgresUserRepository userRepository;

    @Test
    void contextLoads() {
    }

}
