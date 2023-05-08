package by.tms.eshop.repository.impl;

import by.tms.eshop.model.User;
import by.tms.eshop.repository.JdbcUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.tms.eshop.utils.Constants.HibernateQueryParameter.EMAIL;
import static by.tms.eshop.utils.Constants.HibernateQueryParameter.LOGIN;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional
public class JdbcUserRepositoryImpl implements JdbcUserRepository {

//    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    private static final String GET_USER_BY_LOGIN = "FROM User u WHERE u.login = :login";
    //    private static final String GET_USER_BY_LOGIN = "select * from users where login=?";
    private static final String GET_USER_BY_LOGIN_OR_EMAIL = "FROM User u WHERE u.login = :login OR email = :email";
    //    private static final String GET_USER_BY_LOGIN_OR_EMAIL = "select * from users where login=? or email=?";
//    private static final String ADD_USER = "insert into users (login, password, name, surname, email, birthday) values (?, ?, ?, ?, ?, ?)";
//    private static final String ADD_USER = "insert into users (login, password, name, surname, email, birthday) values (?, ?, ?, ?, ?, ?)";

//    @Override
//    public Optional<User> getUserByLogin(String login) {
//        return jdbcTemplate.query(GET_USER_BY_LOGIN, new UserMapper(), login).stream()
//                .findAny();
//    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.of(session.createQuery(GET_USER_BY_LOGIN, User.class)
                    .setParameter(LOGIN, login)
                    .getSingleResult());
        }
    }

//    @Override
//    public Optional<User> getVerifyUser(String login, String email) {
//        return jdbcTemplate.query(GET_USER_BY_LOGIN_OR_EMAIL, new UserMapper(), login, email).stream()
//                .findAny();
//    }
    @Override
    public Optional<User> getVerifyUser(String login, String email) {
        try (Session session = sessionFactory.openSession()) {
            List<User> resultList = session.createQuery(GET_USER_BY_LOGIN_OR_EMAIL, User.class)
                    .setParameter(LOGIN, login)
                    .setParameter(EMAIL, email)
                    .getResultList();
            return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
//            return Optional.of(session.createQuery(GET_USER_BY_LOGIN_OR_EMAIL, User.class)
//                    .setParameter(LOGIN, login)
//                    .setParameter(EMAIL, email)
//                    .getSingleResult());
        }
    }

//    @Override
//    public void addUser(User user) {
//        jdbcTemplate.update(ADD_USER, user.getLogin(), user.getPassword(), user.getName(), user.getSurname(),
//                user.getEmail(), user.getBirthday());
//    }

    @Override
    public void addUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.persist(user);
        }
    }
}