package org.dalloz.kata.simpleusermanager.application;

import org.dalloz.kata.simpleusermanager.domain.User;

import java.util.UUID;

public class UserWebDto {

    private UUID id;
    private String firstname;
    private String lastname;
    private String email;

    public UserWebDto() {}
    public UserWebDto(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public UserWebDto(UUID id, String firstname, String lastname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public UserWebDto(User user){
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public User toUser() {
        return new User(this.getId(), this.getFirstname(), this.getLastname(), this.getEmail());
    }
}
