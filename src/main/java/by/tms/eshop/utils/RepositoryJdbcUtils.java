package by.tms.eshop.utils;

import by.tms.eshop.dto.LocationDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.model.Cart;
import by.tms.eshop.model.Order;
import by.tms.eshop.model.OrderProduct;
import by.tms.eshop.model.Product;
import by.tms.eshop.model.ProductCategory;
import by.tms.eshop.model.User;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static by.tms.eshop.utils.Constants.ALL;
import static by.tms.eshop.utils.Constants.QueryParameter.CATEGORY;
import static by.tms.eshop.utils.Constants.QueryParameter.CONDITION;
import static by.tms.eshop.utils.Constants.QueryParameter.ID;
import static by.tms.eshop.utils.Constants.QueryParameter.INFO;
import static by.tms.eshop.utils.Constants.QueryParameter.MAX_PRICE;
import static by.tms.eshop.utils.Constants.QueryParameter.MIN_PRICE;
import static by.tms.eshop.utils.Constants.QueryParameter.NAME;
import static by.tms.eshop.utils.Constants.QueryParameter.PRICE;
import static by.tms.eshop.utils.Constants.QueryParameter.PRODUCT_ID;
import static by.tms.eshop.utils.Constants.QueryParameter.USER_ID;

@UtilityClass
public class RepositoryJdbcUtils {

    public static ProductDto getProductSimpleBuild(ResultSet resultSet) throws SQLException {
        Product product = Product.builder()
                .name(resultSet.getString(NAME))
                .info(resultSet.getString(INFO))
                .price(resultSet.getBigDecimal(PRICE))
                .productCategory(ProductCategory.builder()
                        .category(resultSet.getString(CATEGORY))
                        .build())
                .build();
        return DtoUtils.makeProductDtoModelTransfer(product);
    }

    public static ProductDto getProductDto(ResultSet resultSet) throws SQLException {
        ProductDto productDto = getProductSimpleBuild(resultSet);
        productDto.setId(resultSet.getLong(ID));
        productDto.setInfo(resultSet.getString(INFO));
        return productDto;
    }

    public static Integer getModifyCount(boolean up, Integer productCount) {
        return up ? ++productCount : --productCount;
    }

    public static boolean isProductNotIncluded(Long productId, List<ProductDto> products) {
        return products.stream()
                .filter(product -> Objects.equals(product.getId(), productId))
                .findAny()
                .isEmpty();
    }

    public static String getQueryDependType(String category, String query) {
        String fullQuery;
        if (!ALL.equals(category)) {
            fullQuery = query + " AND productCategory.category = '" + category + "' ORDER BY id";
        } else {
            fullQuery = query + " ORDER BY id";
        }
        return fullQuery;
    }

    public static Order getOrder(String order, Long id) {
        return Order.builder()
                .id(order)
                .date(LocalDate.now())
                .user(User.builder()
                        .id(id)
                        .build())
                .build();
    }

    public static OrderProduct getOrderProduct(String name, Product product) {
        return OrderProduct.builder()
                .order(Order.builder()
                        .id(name)
                        .build())
                .product(Product.builder()
                        .id(product.getId())
                        .build())
                .build();
    }

    public static Cart getCart(Long userId, Long productId, LocationDto locationDto) {
        return Cart.builder()
                .user(User.builder()
                        .id(userId)
                        .build())
                .product(Product.builder()
                        .id(productId)
                        .build())
                .favorite(locationDto.isFavorite())
                .cart(locationDto.isCart())
                .count(1)
                .build();
    }

    public static Cart getCurrentCart(Long userId, Long productId, String query, Session session) {
        return session.createQuery(query, Cart.class)
                .setParameter(USER_ID, userId)
                .setParameter(PRODUCT_ID, productId)
                .getSingleResult();
    }

    public static List<Cart> getCarts(Long userId, Long productId, String query, Session session) {
        return session.createQuery(query, Cart.class)
                .setParameter(USER_ID, userId)
                .setParameter(PRODUCT_ID, productId)
                .getResultList();
    }

    public static List<Product> getSearchProductsByCondition(String condition, String query, Session session) {
        return session.createQuery(query, Product.class)
                .setParameter(CONDITION, "%" + condition + "%")
                .getResultList();
    }

    public static List<Product> getSearchProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice, String query, Session session) {
        return session.createQuery(query, Product.class)
                .setParameter(MIN_PRICE, minPrice)
                .setParameter(MAX_PRICE, maxPrice)
                .getResultList();
    }
}