package by.tms.eshop.repository.impl;

import by.tms.eshop.repository.JdbcProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class JdbcProductCategoryRepositoryImpl implements JdbcProductCategoryRepository {

    private final SessionFactory sessionFactory;

    @Override
    public  List<String> getProductCategory() {
//        Session session = sessionFactory.getCurrentSession();
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("select category from ProductCategory pC", String.class).getResultList();
        }
    }
}
