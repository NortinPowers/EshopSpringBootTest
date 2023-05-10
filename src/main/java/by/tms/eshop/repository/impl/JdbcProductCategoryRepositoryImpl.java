package by.tms.eshop.repository.impl;

import by.tms.eshop.repository.JdbcProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
//@Transactional
public class JdbcProductCategoryRepositoryImpl implements JdbcProductCategoryRepository {

    @Autowired
    private  SessionFactory sessionFactory;
//    private  final SessionFactory sessionFactory;

    private static final String GET_CATEGORY = "select category from ProductCategory pC";

//    @Transactional
    @Override
    public List<String> getProductCategory() {
        Session session = sessionFactory.getCurrentSession();
//        try (Session session = sessionFactory.openSession()) {
        return session.createQuery(GET_CATEGORY, String.class).getResultList();
//            return session.createQuery("select category from ProductCategory pC", String.class).getResultList();
//        }
    }
}
