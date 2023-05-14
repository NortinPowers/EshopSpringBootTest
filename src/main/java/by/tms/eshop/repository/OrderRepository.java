package by.tms.eshop.repository;

import by.tms.eshop.domain.Order;
import by.tms.eshop.domain.Product;

import java.util.List;

public interface OrderRepository {

//    void createOrder(String order, Long id);
    Long createOrder(String order, Long id);

//    void saveProductInOrderConfigurations(String order, Product product);
    void saveProductInOrderConfigurations(Long id, List<Product> products);

//    List<OrderDto> getOrdersById(Long id);
    List<Order> getOrdersById(Long id);

    boolean checkOrderNumber(String number);
}