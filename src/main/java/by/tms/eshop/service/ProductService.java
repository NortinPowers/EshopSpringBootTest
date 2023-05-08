package by.tms.eshop.service;

import by.tms.eshop.dto.ProductDto;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Set;

public interface ProductService {

    ModelAndView getProductsByCategory(String type);

    ModelAndView getProduct(Long id);

    String getProductTypeValue(Long productId);

    Set<ProductDto> getFoundedProducts(String searchCondition);

    Set<ProductDto> selectAllProductsByFilter(String type, BigDecimal minPrice, BigDecimal maxPrice);
}