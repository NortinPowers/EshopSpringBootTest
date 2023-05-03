package by.tms.eshop.service;

import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    void addSelectedProduct(Long userId, Long productId, CartDto cartDto);
//    void addSelectedProduct(Long userId, Long productId, boolean cart, boolean favorite);
//    void addProductToCart(Long userId, Long productId, boolean cart, boolean favorite);

    List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, boolean cart, boolean favorite);

    void deleteProduct(Long userId, Long productId, boolean cart, boolean favorite);

    void deleteCartProductsAfterBuy(Long userId);

    BigDecimal getProductsPrice(List<ImmutablePair<ProductDto, Integer>> productWithCount);

    List<ProductDto> getPurchasedProducts(Long userId, boolean cart, boolean favorite);
}