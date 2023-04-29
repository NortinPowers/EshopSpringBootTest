package by.tms.eshop.service;

import by.tms.eshop.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserByLogin(String login);

    void addUser(User user);

    Optional<User> getUserByEmail(String email);
}