package org.dalloz.kata.simpleusermanager.infrastructure;

import org.dalloz.kata.simpleusermanager.domain.User;
import org.dalloz.kata.simpleusermanager.domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostgresUserRepository implements UserRepository {

    private final SpringDataPostgresUserRepository userRepository;

    public PostgresUserRepository(SpringDataPostgresUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Optional<User> findById(UUID userId) {
        return this.userRepository.findById(userId).map(UserEntity::toUser);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserEntity::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public UUID save(User user) {
        return userRepository.save(new UserEntity(user)).getId();
    }

    @Override
    public void delete(UUID userId) {
        userRepository.deleteById(userId);
    }
}
