package by.tms.eshop.service;

import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.model.Product;

import java.util.List;

public interface OrderService {

    String createOrder(Long id);

    void saveProductInOrderConfigurations(String order, Product product);

    List<OrderDto> getOrdersById(Long id);

    boolean checkOrderNumber(String number);

    void saveUserOrder(Long userId, List<ProductDto> productsDto);
}