package by.tms.eshop.repository;

import by.tms.eshop.model.User;

import java.util.Optional;

public interface JdbcUserRepository {

    Optional<User> getUserByLogin(String login);

    void addUser(User user);

    Optional<User> getVerifyUser(String login, String email);
}