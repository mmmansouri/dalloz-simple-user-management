package org.dalloz.kata.simpleusermanager;

import org.dalloz.kata.simpleusermanager.domain.User;
import org.dalloz.kata.simpleusermanager.infrastructure.PostgresUserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class SimpleUserManagerApplicationIT {


	public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:15-alpine")
			.withDatabaseName("integration-user-db")
			.withUsername("sa")
			.withPassword("sa");

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


	@Test
	public void givenValidUser_whenSave_shouldSuccess() {

		//GIVEN
		User user = new User("Mohamed","MANSOURI","mmansouri@gmail.com");

		//WHEN
		UUID uuid =userRepository.save(user);

		//THEN
		assertThat(uuid).isNotNull();
	}


	@Test
	public void givenValidUser_whenDelete_shouldSuccess() {

		//GIVEN
		User user = new User("Mohamed","MANSOURI","mmansouri@gmail.com");
		UUID uuid =userRepository.save(user);

		//WHEN
		userRepository.delete(uuid);
		Optional<User> result = userRepository.findById(uuid);

		//THEN
		assertThat(result.isEmpty()).isTrue();
	}


	@Test
	public void givenValidUser_whenUpdate_shouldSuccess() {

		//GIVEN
		User user = new User("Mohamed","MANSOURI","mmansouri@gmail.com");
		UUID uuid = userRepository.save(user);

		//WHEN
		user.setFirstname("Momo");
		user.setLastname("NOLASTNAME");
		user.setEmail("xxx@gmail.com");

		uuid = userRepository.save(user); //updating the user

		Optional<User> result = userRepository.findById(uuid);

		//THEN
		assertThat(result.isEmpty()).isFalse();
		assertThat(result.get().getId().equals(uuid)).isTrue();
		assertThat(result.get().equals(user)).isFalse();

	}
}
