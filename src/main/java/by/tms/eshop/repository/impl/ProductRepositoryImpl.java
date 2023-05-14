package by.tms.eshop.repository.impl;

import by.tms.eshop.domain.Product;
import by.tms.eshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static by.tms.eshop.utils.Constants.QueryParameter.ID;
import static by.tms.eshop.utils.Constants.RequestParameters.CATEGORY;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getQueryDependType;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getSearchProductsByCondition;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getSearchProductsByPrice;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final SessionFactory sessionFactory;

    private static final String GET_PRODUCTS_BY_CATEGORY = "FROM Product WHERE productCategory.category = :category";
    private static final String GET_PRODUCT_CATEGORY = "SELECT productCategory.category FROM Product WHERE id = :id";
    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_NAME = "FROM Product WHERE LOWER (name) LIKE LOWER ('%' || :condition || '%')";
    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_INFO = "FROM Product WHERE LOWER (info) LIKE LOWER ('%' || :condition || '%')";
    private static final String SELECT_ALL_PRODUCTS_BY_FILTER = "FROM Product WHERE price >= :minPrice AND price <= :maxPrice";

    @Override
    public List<Product> getProductsByCategory(String category) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(GET_PRODUCTS_BY_CATEGORY, Product.class)
                .setParameter(CATEGORY, category)
                .getResultList();
    }

//                         work for ProductDto
//                         (оставил себе для примера, потом удалю)
//
//    @Override
//    public List<ProductDto> getProductsByCategory(String category) {
//     try (Session session = sessionFactory.openSession()) {
//            Query<Object[]> query = session.createQuery(GET_PRODUCTS_BY_CATEGORY);
//            query.setParameter("category", category);
//            List<Object[]> results = query.getResultList();
//            List<ProductDto> productList = new ArrayList<>();
//            for (Object[] row : results) {
//                ProductDto product = ProductDto.builder().build();
//                product.setId((Long) row[0]);
//                product.setName((String) row[1]);
//                product.setCategory((String) row[2]);
//                product.setInfo((String) row[3]);
//                product.setPrice((BigDecimal) row[4]);
//                productList.add(product);
//            }
//            return productList;
//    }
//}

    @Override
    public Product getProduct(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Product.class, id);
    }

    @Override
    public String getProductCategoryValue(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(GET_PRODUCT_CATEGORY, String.class)
                .setParameter(ID, id)
                .getSingleResult();
    }

    @Override
    public Set<Product> getFoundedProducts(String condition) {
        Session session = sessionFactory.getCurrentSession();
        Set<Product> products = new LinkedHashSet<>(getSearchProductsByCondition(condition, GET_PRODUCTS_BY_SEARCH_CONDITION_IN_NAME, session));
        products.addAll(getSearchProductsByCondition(condition, GET_PRODUCTS_BY_SEARCH_CONDITION_IN_INFO, session));
        return products;
    }

    @Override
    public Set<Product> selectAllProductsByFilter(String category, BigDecimal minPrice, BigDecimal maxPrice) {
        String query = getQueryDependType(category, SELECT_ALL_PRODUCTS_BY_FILTER);
        Session session = sessionFactory.getCurrentSession();
        return new LinkedHashSet<>(getSearchProductsByPrice(minPrice, maxPrice, query, session));
    }
}