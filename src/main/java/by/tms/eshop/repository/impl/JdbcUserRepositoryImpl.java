package by.tms.eshop.repository.impl;

import by.tms.eshop.model.User;
import by.tms.eshop.repository.JdbcUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static by.tms.eshop.utils.Constants.QueryParameter.EMAIL;
import static by.tms.eshop.utils.Constants.QueryParameter.LOGIN;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional
public class JdbcUserRepositoryImpl implements JdbcUserRepository {

    private final SessionFactory sessionFactory;

    private static final String GET_USER_BY_LOGIN = "FROM User WHERE login = :login";
    private static final String GET_USER_BY_LOGIN_OR_EMAIL = "FROM User WHERE login = :login OR email = :email";

    @Override
    public Optional<User> getUserByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.of(session.createQuery(GET_USER_BY_LOGIN, User.class)
                .setParameter(LOGIN, login)
                .getSingleResult());
    }

    @Override
    public Optional<User> getVerifyUser(String login, String email) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.of(session.createQuery(GET_USER_BY_LOGIN_OR_EMAIL, User.class)
                .setParameter(LOGIN, login)
                .setParameter(EMAIL, email)
                .getResultList().get(0));
    }

    @Override
    public void addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }
}