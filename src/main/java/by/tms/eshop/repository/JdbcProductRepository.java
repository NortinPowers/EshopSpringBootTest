package by.tms.eshop.repository;

import by.tms.eshop.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface JdbcProductRepository {

    List<Product> getProductsByCategory(String category);

    String getProductCategoryValue(Long id);

    Set<Product> getFoundedProducts(String searchCondition);

    Product getProduct(Long id);

    Set<Product> selectAllProductsByFilter(String type, BigDecimal minPrice, BigDecimal maxPrice);
}