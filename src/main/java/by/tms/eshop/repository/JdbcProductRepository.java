package by.tms.eshop.repository;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface JdbcProductRepository {

//    List<ProductDto> getProductsByCategory(String type);
//    List<ProductDto> getProductsByCategory(String category);
    List<Product> getProductsByCategory(String category);

    String getProductTypeValue(Long id);

    Set<ProductDto> getFoundedProducts(String searchCondition);

    ProductDto getProduct(Long id);

    Set<ProductDto> selectAllProductsByFilter(String type, BigDecimal minPrice, BigDecimal maxPrice);
}