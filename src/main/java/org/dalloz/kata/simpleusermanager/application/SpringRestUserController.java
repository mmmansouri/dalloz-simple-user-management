package org.dalloz.kata.simpleusermanager.application;

import org.dalloz.kata.simpleusermanager.domain.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class SpringRestUserController {

    private final UserService userService;

    public SpringRestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = "application/json")
    public List<UserWebDto> getUsers() {
        return userService.findAll()
                .stream()
                .map(e->new UserWebDto(e.getId(), e.getFirstname(), e.getLastname(), e.getEmail()))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces ="application/json" )
    public UserWebDto getUserById(@PathVariable UUID id) {
        return userService.findById(id).map(UserWebDto::new).orElse(null);
    }

    @PutMapping(produces = "application/json")
    public UserWebDto createOrUpdateUser(@RequestBody UserWebDto userWebDto) {
        return new UserWebDto(userService.save(userWebDto.toUser()));
    }

    @DeleteMapping(value = "/{id}", produces ="application/json" )
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }
}
