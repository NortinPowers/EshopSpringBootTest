package by.tms.eshop.repository.impl;

import by.tms.eshop.model.Product;
import by.tms.eshop.repository.JdbcProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static by.tms.eshop.utils.Constants.HibernateQueryParameter.CONDITION;
import static by.tms.eshop.utils.Constants.HibernateQueryParameter.MAX_PRICE;
import static by.tms.eshop.utils.Constants.HibernateQueryParameter.MIN_PRICE;
import static by.tms.eshop.utils.Constants.RequestParameters.CATEGORY;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getQueryDependType;

@Slf4j
@Repository
@RequiredArgsConstructor
//@Transactional
public class JdbcProductRepositoryImpl implements JdbcProductRepository {

    //    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    //type -> category

    //    private static final String GET_PRODUCTS_BY_TYPE = "select p.id, p.name, pt.type, p.info, p.price from products p join product_type pt on pt.id = p.product_type_id where pt.type=?";
//    private static final String GET_PRODUCTS_BY_TYPE = "select p.id, p.name, pt.type, p.info, p.price from products p join product_type pt on pt.id = p.product_category_id where pt.type=?";
    private static final String GET_PRODUCTS_BY_CATEGORY = "FROM Product WHERE productCategory.category = :category";
//    private static final String GET_PRODUCTS_BY_CATEGORY = "FROM Product p WHERE p.productCategory.category = :category";
    //              WORK to ProductDto!!!
    //    private static final String GET_PRODUCTS_BY_CATEGORY = "select p.id, p.name, p.productCategory.category, p.info, p.price from Product p where p.productCategory.category = :category";
    private static final String GET_PRODUCT_CATEGORY = "SELECT productCategory.category FROM Product WHERE id = :id";
//    private static final String GET_PRODUCT_CATEGORY = "SELECT p.productCategory.category FROM Product p WHERE p.id = :id";
    //    private static final String GET_PRODUCT_TYPE = "select pt.type from products p join product_type pt on pt.id = p.product_category_id where p.id=?";
    //    private static final String GET_PRODUCT_TYPE = "select pt.type from products p join product_type pt on pt.id = p.product_type_id where p.id=?";
    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_NAME = "FROM Product WHERE LOWER (name) LIKE LOWER (:condition)";
//    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_NAME = "FROM Product p WHERE LOWER (p.name) LIKE LOWER (:condition)";
    //    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_NAME = "select p.id, p.name, pt.type, p.info, p.price from products p join product_type pt on pt.id = p.product_category_id where lower(name) like lower(?)";
    //    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_NAME = "select p.id, p.name, pt.type, p.info, p.price from products p join product_type pt on pt.id = p.product_type_id where lower(name) like lower(?)";
    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_INFO = "FROM Product WHERE LOWER (info) LIKE LOWER (:condition)";
//    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_INFO = "FROM Product p WHERE LOWER (p.info) LIKE LOWER (:condition)";
    //    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_INFO = "select p.id, p.name, pt.type, p.info, p.price from products p join product_type pt on pt.id = p.product_category_id where lower(info) like lower(?)";
    //    private static final String GET_PRODUCTS_BY_SEARCH_CONDITION_IN_INFO = "select p.id, p.name, pt.type, p.info, p.price from products p join product_type pt on pt.id = p.product_type_id where lower(info) like lower(?)";
//    private static final String GET_PRODUCT = "select p.id, p.name, pt.type, p.info, p.price from products p join product_type pt on pt.id = p.product_category_id where p.id=?";
    //    private static final String GET_PRODUCT = "select p.id, p.name, pt.type, p.info, p.price from products p join product_type pt on pt.id = p.product_type_id where p.id=?";
    private static final String SELECT_ALL_PRODUCTS_BY_FILTER = "SELECT p FROM Product p WHERE p.price >= :minPrice AND p.price <= :maxPrice";
//    private static final String SELECT_ALL_PRODUCTS_BY_FILTER = "select p.id, p.name, pt.type, p.info, p.price from products p join product_type pt on pt.id = p.product_type_id where p.price>=? and p.price<=?";

//                           OLD
//    @Override
//    public List<ProductDto> getProductsByCategory(String type) {
//        return jdbcTemplate.query(GET_PRODUCTS_BY_TYPE, new ProductDtoMapper(), type);
//    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        Session session = sessionFactory.getCurrentSession();
//        try (Session session = sessionFactory.openSession()) {
        return session.createQuery(GET_PRODUCTS_BY_CATEGORY, Product.class)
                .setParameter(CATEGORY, category)
                .getResultList();
//        }
    }

//                          WORK!!
//    @Override
//    public List<ProductDto> getProductsByCategory(String category) {
//        try (Session session = sessionFactory.openSession()) {
//            List<Product> products = session.createQuery(
//                            "SELECT p FROM Product p WHERE p.productCategory.category = :category", Product.class)
//                    .setParameter("category", category)
//                    .getResultList();
//            List<ProductDto> productDtos = new ArrayList<>();
//            for (Product product : products) {
//                productDtos.add(makeProductDtoModelTransfer(product));
//            }
//            return productDtos;
//        }
//    }

//                         WORK to ProductDto!!!
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

//                          OLD
//    @Override
//    public ProductDto getProduct(Long id) {
//        return jdbcTemplate.query(GET_PRODUCT, new ProductDtoMapper(), id).stream()
//                .findAny()
//                .orElse(null);
//    }

    @Override
    public Product getProduct(Long id) {
        Session session = sessionFactory.getCurrentSession();
//        try (Session session = sessionFactory.openSession()) {
        return session.get(Product.class, id);
//        }
    }

//    @Override
//    public String getProductTypeValue(Long id) {
//        return jdbcTemplate.query(GET_PRODUCT_TYPE, new ProductTypeDtoMapper(), id).stream()
//                .findAny()
//                .map(ProductDto::getCategory)
//                .orElse(null);
//    }

    @Override
    public String getProductCategoryValue(Long id) {
        Session session = sessionFactory.getCurrentSession();
//        try (Session session = sessionFactory.openSession()) {
        return session.createQuery(GET_PRODUCT_CATEGORY, String.class)
                .setParameter("id", id)
                .getSingleResult();
//        }
    }

//    @Override
//    public Set<ProductDto> getFoundedProducts(String searchCondition) {
//        String condition = "%" + searchCondition + "%";
//        List<ProductDto> productNameSearch = jdbcTemplate.query(GET_PRODUCTS_BY_SEARCH_CONDITION_IN_NAME, new ProductDtoMapper(), condition);
//        List<ProductDto> productInfoSearch = jdbcTemplate.query(GET_PRODUCTS_BY_SEARCH_CONDITION_IN_INFO, new ProductDtoMapper(), condition);
//        Set<ProductDto> products = new LinkedHashSet<>(productNameSearch);
//        products.addAll(productInfoSearch);
//        return products;
//    }

    @Override
    public Set<Product> getFoundedProducts(String searchCondition) {
        String condition = "%" + searchCondition + "%";
//        String condition = String.format("%%%s%%", searchCondition);
        Session session = sessionFactory.getCurrentSession();
//        try (Session session = sessionFactory.openSession()) {
        List<Product> productNameSearch = session.createQuery(GET_PRODUCTS_BY_SEARCH_CONDITION_IN_NAME, Product.class)
                .setParameter(CONDITION, condition)
                .getResultList();
        List<Product> productInfoSearch = session.createQuery(GET_PRODUCTS_BY_SEARCH_CONDITION_IN_INFO, Product.class)
                .setParameter(CONDITION, condition)
                .getResultList();
        Set<Product> products = new LinkedHashSet<>(productNameSearch);
        products.addAll(productInfoSearch);
        return products;
//        }
    }

//    @Override
//    public Set<ProductDto> selectAllProductsByFilter(String type, BigDecimal minPrice, BigDecimal maxPrice) {
//        String query = getQueryDependType(type, SELECT_ALL_PRODUCTS_BY_FILTER);
//        List<ProductDto> searchProducts = jdbcTemplate.query(query, new ProductDtoMapper(), minPrice, maxPrice);
//        return new LinkedHashSet<>(searchProducts);
//    }

    @Override
    public Set<Product> selectAllProductsByFilter(String category, BigDecimal minPrice, BigDecimal maxPrice) {
        String query = getQueryDependType(category, SELECT_ALL_PRODUCTS_BY_FILTER);
        Session session = sessionFactory.getCurrentSession();
//        try (Session session = sessionFactory.openSession()) {
        List<Product> products = session.createQuery(query, Product.class)
                .setParameter(MIN_PRICE, minPrice)
                .setParameter(MAX_PRICE, maxPrice)
                .getResultList();
        return new LinkedHashSet<>(products);
    }
//    }
}