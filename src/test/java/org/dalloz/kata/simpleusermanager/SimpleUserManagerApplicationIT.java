package org.dalloz.kata.simpleusermanager;

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


class SimpleUserManagerApplicationIT extends SpringConfigurationIT{

	@Test
	public void givenValidUser_whenSave_shouldSuccess() {

		//GIVEN
		User user = new User("Mohamed","MANSOURI","mmansouri@gmail.com");

		//WHEN
		user =userRepository.save(user);

		//THEN
		assertThat(user.getId()).isNotNull();
	}


	@Test
	public void givenValidUser_whenDelete_shouldSuccess() {

		//GIVEN
		User user = new User("Mohamed","MANSOURI","mmansouri@gmail.com");
		user = userRepository.save(user);

		//WHEN
		userRepository.delete(user.getId());
		Optional<User> result = userRepository.findById(user.getId());

		//THEN
		assertThat(result.isEmpty()).isTrue();
	}


	@Test
	public void givenValidUser_whenUpdate_shouldSuccess() {

		//GIVEN
		User user = new User("Mohamed","MANSOURI","mmansouri@gmail.com");
		user = userRepository.save(user);

		//WHEN
		user.setFirstname("Momo");
		user.setLastname("NOLASTNAME");
		user.setEmail("xxx@gmail.com");

		user = userRepository.save(user); //updating the user

		Optional<User> result = userRepository.findById(user.getId());

		//THEN
		assertThat(result.isEmpty()).isFalse();
		assertThat(result.get().getId().equals(user.getId())).isTrue();
		assertThat(result.get().equals(user)).isFalse();

	}
}
