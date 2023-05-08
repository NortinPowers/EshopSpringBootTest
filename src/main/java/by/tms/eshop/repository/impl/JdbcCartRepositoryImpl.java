package by.tms.eshop.repository.impl;

import by.tms.eshop.dto.LocationDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.mapper.CartCountMapper;
import by.tms.eshop.model.Cart;
import by.tms.eshop.repository.JdbcCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static by.tms.eshop.utils.RepositoryJdbcUtils.getModifyCount;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getProductDto;
import static by.tms.eshop.utils.RepositoryJdbcUtils.isProductNotIncluded;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional
public class JdbcCartRepositoryImpl implements JdbcCartRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;


//    private static final String ADD_PRODUCT_TO_CART = "INSERT INTO Cart (user_id, product_id, cart, favorite) VALUES (:userId, :productId, :cart, :favorite)";
        private static final String ADD_PRODUCT_TO_CART = "insert into carts (user_id, product_id, cart, favorite) VALUES (?, ?, ?, ?)";
    private static final String GET_CART_PRODUCTS_BY_USER_ID = "select p.id, p.name, p.price, pt.type, p.info, c.count from carts c join products p on p.id = c.product_id join product_type pt on pt.id = p.product_category_id where c.user_id=? and c.cart=true";
    private static final String GET_FAVORITE_PRODUCTS_BY_USER_ID = "select p.id, p.name, p.price, pt.type, p.info, c.count from carts c join products p on p.id = c.product_id join product_type pt on pt.id = p.product_category_id where c.user_id=? and c.favorite=true";
//    private static final String DELETE_FAVORITE_PRODUCT = "DELETE FROM carts c WHERE c. user_id = :userId AND c.product_id = :productId AND c.favorite = true";
        private static final String DELETE_FAVORITE_PRODUCT = "delete from carts where user_id=? and product_id=? and favorite=true";
//    private static final String DELETE_CART_PRODUCT = "DELETE FROM Cart c WHERE c.user.id = :userId AND c.product.id = :productId AND c.cart = true";
        private static final String DELETE_CART_PRODUCT = "delete from carts where user_id=? and product_id=? and cart=true";
//    private static final String GET_CURRENT_PRODUCT_COUNT = "SELECT c.count FROM Cart c WHERE c.user.id = :userId AND c.product.id = :productId AND c.cart = true";
        private static final String GET_CURRENT_PRODUCT_COUNT = "select count from carts where user_id=? and product_id=? and cart=true";
//    private static final String UPDATE_CURRENT_PRODUCT_COUNT = "UPDATE carts c SET count = :count WHERE c.user_id = :userId AND c.product_id = :productId AND c.cart = true";
        private static final String UPDATE_CURRENT_PRODUCT_COUNT = "update carts set count = ? where user_id = ? and product_id = ? and cart = true";
//    private static final String DELETE_CART_PRODUCT_AFTER_BUY = "DELETE FROM carts c WHERE c.user_id = :userId AND c.cart = true";
    private static final String DELETE_CART_PRODUCT_AFTER_BUY = "delete from carts where user_id=? and cart=true";

    @Override
//    @Modifying(clearAutomatically = true)
    public void addSelectedProduct(Long userId, Long productId, LocationDto locationDto) {
        if (locationDto.isFavorite()) {
            if (checkProduct(userId, productId, locationDto)) {
                addProduct(userId, productId, locationDto);
            }
        } else {
            if (checkProduct(userId, productId, locationDto)) {
                addProduct(userId, productId, locationDto);
            } else {
                modifyProductCount(userId, productId, true);
            }
        }
    }

    @Override
    public void deleteProduct(Long userId, Long productId, LocationDto locationDto) {
        if (locationDto.isFavorite()) {
            deleteProductByMark(userId, productId, DELETE_FAVORITE_PRODUCT);
        } else {
            Integer productCount = getCartProductCount(userId, productId);
            if (productCount > 1) {
                modifyProductCount(userId, productId, false);
            } else {
                deleteProductByMark(userId, productId, DELETE_CART_PRODUCT);
            }
        }
    }

//    @Override
//    public void deleteProduct(Long userId, Long productId, LocationDto locationDto) {
////        try (Session session = sessionFactory.openSession()) {
//        if (locationDto.isFavorite()) {
////                session.createQuery(DELETE_FAVORITE_PRODUCT, Cart.class)
////                        .setParameter("userId", userId)
////                        .setParameter("productId", productId)
////                        .executeUpdate();
//            deleteProductByMark(userId, productId, DELETE_FAVORITE_PRODUCT);
//        } else {
//            Integer productCount = getCartProductCount(userId, productId);
//            if (productCount > 1) {
//                modifyProductCount(userId, productId, false);
//            } else {
////                    session.createQuery(DELETE_CART_PRODUCT, Cart.class)
////                            .setParameter("userId", userId)
////                            .setParameter("productId", productId)
////                            .executeUpdate();
//                deleteProductByMark(userId, productId, DELETE_CART_PRODUCT);
//            }
//        }
////        }
//    }

    @Override
    public List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, LocationDto locationDto) {
        String query = locationDto.isCart() ? GET_CART_PRODUCTS_BY_USER_ID : GET_FAVORITE_PRODUCTS_BY_USER_ID;
        return jdbcTemplate.query(query, (rs, i) -> new ImmutablePair<>(getProductDto(rs), rs.getInt("count")), userId);
    }

    @Override
    public boolean checkProduct(Long userId, Long productId, LocationDto locationDto) {
        List<ProductDto> productsDto = getProducts(userId, locationDto);
        return isProductNotIncluded(productId, productsDto);
    }
    @Override
    public Integer getCartProductCount(Long userId, Long productId) {
        return jdbcTemplate.query(GET_CURRENT_PRODUCT_COUNT, new CartCountMapper(),
                        userId, productId).stream()
                .findAny()
                .map(Cart::getCount)
                .orElse(0);
    }

//    @Override
//    public Integer getCartProductCount(Long userId, Long productId) {
//        try (Session session = sessionFactory.openSession()) {
//            return session.createQuery(GET_CURRENT_PRODUCT_COUNT, Cart.class)
//                    .setParameter("userId", userId)
//                    .setParameter("productId", productId)
//                    .uniqueResultOptional()
//                    .map(Cart::getCount)
//                    .orElse(0);
//        }
//    }

    @Override
    public void deleteCartProductsAfterBuy(Long userId) {
        jdbcTemplate.update(DELETE_CART_PRODUCT_AFTER_BUY, userId);
    }

//    @Override
//    public void deleteCartProductsAfterBuy(Long userId) {
//        try (Session session = sessionFactory.openSession()) {
//            session.createNativeQuery(DELETE_CART_PRODUCT_AFTER_BUY, Cart.class)
//                    .setParameter("userId", userId)
//                    .executeUpdate();
//        }
//    }

    @Override
    public List<ProductDto> getPurchasedProducts(Long userId, LocationDto locationDto) {
        List<ProductDto> products = new ArrayList<>();
        List<ImmutablePair<ProductDto, Integer>> productWithCount = getSelectedProducts(userId, locationDto);
        for (Pair<ProductDto, Integer> productIntegerPair : productWithCount) {
            Integer count = productIntegerPair.getRight();
            while (count > 0) {
                products.add(productIntegerPair.getLeft());
                count--;
            }
        }
        return products;
    }

    private void addProduct(Long userId, Long productId, LocationDto locationDto) {
        jdbcTemplate.update(ADD_PRODUCT_TO_CART, userId, productId, locationDto.isCart(), locationDto.isFavorite());
    }


//    private void addProduct(Long userId, Long productId, LocationDto locationDto) {
//        try (Session session = sessionFactory.openSession()) {
//            session.createNativeQuery(ADD_PRODUCT_TO_CART, Cart.class)
//                    .setParameter("userId", userId)
//                    .setParameter("productId", productId)
//                    .setParameter("cart", locationDto.isCart())
//                    .setParameter("favorite", locationDto.isFavorite())
//                    .executeUpdate();
//        }
//    }

    private void modifyProductCount(Long userId, Long productId, boolean up) {
        Integer productCount = getCartProductCount(userId, productId);
        productCount = getModifyCount(up, productCount);
        jdbcTemplate.update(UPDATE_CURRENT_PRODUCT_COUNT, productCount, userId, productId);
    }

//    private void modifyProductCount(Long userId, Long productId, boolean up) {
//        Integer productCount = getCartProductCount(userId, productId);
//        productCount = getModifyCount(up, productCount);
//        try (Session session = sessionFactory.openSession()) {
//            session.createNativeQuery(UPDATE_CURRENT_PRODUCT_COUNT, Cart.class)
//                    .setParameter("count", productCount)
//                    .setParameter("userId", userId)
//                    .setParameter("productId", productId)
//                    .executeUpdate();
//        }
//    }

    private List<ProductDto> getProducts(Long userId, LocationDto locationDto) {
        return getSelectedProducts(userId, locationDto).stream()
                .map(Pair::getLeft)
                .collect(Collectors.toList());
    }

        private void deleteProductByMark(Long userId, Long productId, String query) {
        jdbcTemplate.update(query, userId, productId);
    }
//    private void deleteProductByMark(Long userId, Long productId, String query) {
//        try (Session session = sessionFactory.openSession()) {
////        Session session = sessionFactory.getCurrentSession();
//            session.createNativeQuery(DELETE_FAVORITE_PRODUCT, Cart.class)
//                    .setParameter("userId", userId)
//                    .setParameter("productId", productId)
//                    .executeUpdate();
////        jdbcTemplate.update(query, userId, productId);
//        }
//    }
}