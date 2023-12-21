package org.dalloz.kata.simpleusermanager.domain;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(UUID userId) {
        return this.userRepository.findById(userId);
    }
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User save(User user){
        verifyExistingMail(user);

        verifyExistingMailModification(user);

        return this.userRepository.save(user);
    }

    private void verifyExistingMailModification(User user) {
        if(user.getId() != null) {
            Optional<User> savedUser = this.userRepository.findById(user.getId());
            if (savedUser.isPresent()) {
                if (!Objects.equals(savedUser.get().getEmail(), user.getEmail())) {
                    throw new IllegalArgumentException("Mail modification is not allowed");
                }
            }
        }
    }

    private void verifyExistingMail(User user) {
        Optional<User> savedUser = this.userRepository.findByEmail(user.getEmail());

        if(savedUser.isPresent()) {
            if(!savedUser.get().getId().equals(user.getId())) {
                throw new IllegalArgumentException("The following email is already used : " + user.getEmail() );
            }
        }
    }

    public void delete(UUID userId) {
        this.userRepository.delete(userId);
    }
}
