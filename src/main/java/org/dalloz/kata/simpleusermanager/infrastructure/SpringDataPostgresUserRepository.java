package org.dalloz.kata.simpleusermanager.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataPostgresUserRepository extends JpaRepository<UserEntity, UUID> {
}
