package by.tms.eshop.service;

import by.tms.eshop.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class Facade {

    private final CartService cartService;
    private final OrderService orderService;

    public void carriesPurchase(Long userId) {
        List<ProductDto> productsDto = cartService.getPurchasedProducts(userId, true, false);
        orderService.saveUserOrder(userId, productsDto);
        cartService.deleteCartProductsAfterBuy(userId);
    }
}
