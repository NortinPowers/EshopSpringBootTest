package by.tms.eshop.service.impl;

import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.model.Product;
import by.tms.eshop.repository.JdbcOrderRepository;
import by.tms.eshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.tms.eshop.utils.ControllerUtils.createOrderNumber;
import static by.tms.eshop.utils.DtoUtils.getProductsFromDto;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final JdbcOrderRepository jdbcOrderRepository;

    @Override
    public String createOrder(Long id) {
        String orderNumber = generateOrderNumber(id);
        jdbcOrderRepository.createOrder(orderNumber, id);
        return orderNumber;
    }

    @Override
    public void saveProductInOrderConfigurations(String order, Product product) {
        jdbcOrderRepository.saveProductInOrderConfigurations(order, product);
    }

    @Override
    public List<OrderDto> getOrdersById(Long id) {
        return jdbcOrderRepository.getOrdersById(id);
    }

    @Override
    public boolean checkOrderNumber(String number) {
        return jdbcOrderRepository.checkOrderNumber(number);
    }

    @Override
    public void saveUserOrder(Long userId, List<ProductDto> productsDto) {
        String order = createOrder(userId);
        List<Product> products = getProductsFromDto(productsDto);
        products.forEach(product -> saveProductInOrderConfigurations(order, product));
    }

    private String generateOrderNumber(Long id) {
        String orderNumber = "";
        while (checkOrderNumber(orderNumber) || StringUtils.isEmpty(orderNumber)) {
            orderNumber = createOrderNumber(id);
        }
        return orderNumber;
    }
}