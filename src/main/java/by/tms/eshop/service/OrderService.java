package by.tms.eshop.service;

import by.tms.eshop.domain.Order;
import by.tms.eshop.domain.Product;
import by.tms.eshop.dto.ProductDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {

//    String createOrder(Long id);
    Long createOrder(Long id);

//    void saveProductInOrderConfigurations(String order, Product product);
    void saveProductInOrderConfigurations(Long id, List<Product> products);

//    List<OrderDto> getOrdersById(Long id);
    List<Order> getOrdersById(Long id);

    boolean checkOrderNumber(String number);

    @Transactional
    void saveUserOrder(Long userId, List<ProductDto> productsDto);
}