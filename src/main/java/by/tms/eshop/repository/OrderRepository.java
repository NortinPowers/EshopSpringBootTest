package by.tms.eshop.repository;

import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.model.Product;

import java.util.List;

public interface OrderRepository {

    void createOrder(String order, Long id);

    void saveProductInOrderConfigurations(String order, Product product);

    List<OrderDto> getOrdersById(Long id);

    boolean checkOrderNumber(String number);
}