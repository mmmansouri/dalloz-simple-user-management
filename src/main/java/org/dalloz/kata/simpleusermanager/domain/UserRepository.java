package org.dalloz.kata.simpleusermanager.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID userId);
    List<User> findAll();

    UUID save(User user);

    void delete(UUID userId);
}
