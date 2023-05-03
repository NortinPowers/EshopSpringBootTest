package by.tms.eshop.service.impl;

import by.tms.eshop.model.User;
import by.tms.eshop.repository.JdbcUserRepository;
import by.tms.eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JdbcUserRepository jdbcUserRepository;

    @Override
    public Optional<User> getUserByLogin(String login) {
        return jdbcUserRepository.getUserByLogin(login);
    }

    @Override
    public void addUser(User user) {
        jdbcUserRepository.addUser(user);
    }

    @Override
    public Optional<User> getVerifyUser(String login, String email) {
        return jdbcUserRepository.getVerifyUser(login, email);
    }
}