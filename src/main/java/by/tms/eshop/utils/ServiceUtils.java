package by.tms.eshop.utils;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.model.Product;
import jakarta.servlet.http.HttpSession;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static by.tms.eshop.utils.Constants.Attributes.FOUND_PRODUCTS;
import static by.tms.eshop.utils.ControllerUtils.applyPriceFilterOnProducts;
import static by.tms.eshop.utils.ControllerUtils.applyTypeFilterOnProducts;
import static by.tms.eshop.utils.DtoUtils.makeProductDtoModelTransfer;

@UtilityClass
public class ServiceUtils {

    public static Set<ProductDto> getProductByFilter(HttpSession session, String type, BigDecimal minPrice, BigDecimal maxPrice) {
        Set<ProductDto> products;
        products = (Set<ProductDto>) session.getAttribute(FOUND_PRODUCTS);
        products = applyPriceFilterOnProducts(minPrice, maxPrice, products);
        products = applyTypeFilterOnProducts(type, products);
        return products;
    }

    public static List<ProductDto> getProductDtoList(List<Product> productsByCategory) {
        List<ProductDto> products = new ArrayList<>();
        for (Product product : productsByCategory) {
            products.add(makeProductDtoModelTransfer(product));
        }
        return products;
    }
}