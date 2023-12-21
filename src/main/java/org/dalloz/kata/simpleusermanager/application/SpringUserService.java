package org.dalloz.kata.simpleusermanager.application;

import org.dalloz.kata.simpleusermanager.domain.UserRepository;
import org.dalloz.kata.simpleusermanager.domain.UserService;
import org.springframework.stereotype.Component;

@Component
public class SpringUserService extends UserService {
    public SpringUserService(UserRepository userRepository) {
        super(userRepository);
    }
}
