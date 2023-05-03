package by.tms.eshop.repository;

import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

public interface JdbcCartRepository {

    void addSelectedProduct(Long userId, Long productId, CartDto cartDto);
//    void addSelectedProduct(Long userId, Long productId, boolean cart, boolean favorite);

    void deleteProduct(Long userId, Long productId, CartDto cartDto);
//    void deleteProduct(Long userId, Long productId, boolean cart, boolean favorite);

    List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, CartDto cartDto);
//    List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, boolean cart, boolean favorite);

    boolean checkProduct(Long userId, Long productId, CartDto cartDto);
//    boolean checkProduct(Long userId, Long productId, boolean cart, boolean favorite);

    Integer getCartProductCount(Long userId, Long productId);

    void deleteCartProductsAfterBuy(Long userId);

    List<ProductDto> getPurchasedProducts(Long userId, CartDto cartDto);
//    List<ProductDto> getPurchasedProducts(Long userId, boolean cart, boolean favorite);
}