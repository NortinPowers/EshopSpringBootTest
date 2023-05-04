package by.tms.eshop.utils;

import by.tms.eshop.dto.ProductDto;
import jakarta.servlet.http.HttpSession;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Set;

import static by.tms.eshop.utils.Constants.Attributes.FOUND_PRODUCTS;
import static by.tms.eshop.utils.ControllerUtils.applyPriceFilterOnProducts;
import static by.tms.eshop.utils.ControllerUtils.applyTypeFilterOnProducts;

@UtilityClass
public class ServiceUtils {

    public static Set<ProductDto> getProductByFilter(HttpSession session, String type, BigDecimal minPrice, BigDecimal maxPrice) {
        Set<ProductDto> products;
        products = (Set<ProductDto>) session.getAttribute(FOUND_PRODUCTS);
        products = applyPriceFilterOnProducts(minPrice, maxPrice, products);
        products = applyTypeFilterOnProducts(type, products);
        return products;
    }
}