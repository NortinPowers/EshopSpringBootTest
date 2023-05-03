package by.tms.eshop.service.impl;

import by.tms.eshop.dto.CartDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.repository.JdbcCartRepository;
import by.tms.eshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final JdbcCartRepository jdbcCartRepository;

    @Override
    public void addSelectedProduct(Long userId, Long productId, CartDto cartDto) {
//    public void addSelectedProduct(Long userId, Long productId, boolean cart, boolean favorite) {
        jdbcCartRepository.addSelectedProduct(userId, productId, cartDto);
//        jdbcCartRepository.addSelectedProduct(userId, productId, cart, favorite);
//        jdbcCartRepository.addProductToCart(userId, productId, cart, favorite);
    }

    @Override
    public List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, CartDto cartDto) {
//    public List<ImmutablePair<ProductDto, Integer>> getProductsFromCart(Long userId, boolean cart, boolean favorite) {
        return jdbcCartRepository.getSelectedProducts(userId, cartDto);
//        return jdbcCartRepository.getSelectedProducts(userId, cart, favorite);
//        return jdbcCartRepository.getProductsFromCart(userId, cart, favorite);
    }

    @Override
    public void deleteProduct(Long userId, Long productId, CartDto cartDto) {
//    public void deleteProduct(Long userId, Long productId, boolean cart, boolean favorite) {
        jdbcCartRepository.deleteProduct(userId, productId, cartDto);
//        jdbcCartRepository.deleteProduct(userId, productId, cart, favorite);
    }

    @Override
    public void deleteCartProductsAfterBuy(Long userId) {
        jdbcCartRepository.deleteCartProductsAfterBuy(userId);
    }

    @Override
    public BigDecimal getProductsPrice(List<ImmutablePair<ProductDto, Integer>> productWithCount) {
        BigDecimal fullPrice = BigDecimal.ZERO;
        for (ImmutablePair<ProductDto, Integer> product : productWithCount) {
            BigDecimal totalPrice = product.getLeft().getPrice().multiply(new BigDecimal(product.getRight()));
            fullPrice = fullPrice.add(totalPrice);
        }
        return fullPrice;
    }

    @Override
    public List<ProductDto> getPurchasedProducts(Long userId, CartDto cartDto) {
//    public List<ProductDto> getPurchasedProducts(Long userId, boolean cart, boolean favorite) {
        return jdbcCartRepository.getPurchasedProducts(userId, cartDto);
//        return jdbcCartRepository.getPurchasedProducts(userId, cart, favorite);
    }
}