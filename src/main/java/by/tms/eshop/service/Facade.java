package by.tms.eshop.service;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.utils.DtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_FAVORITES;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCTS_PAGE_TYPE_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCT_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.RequestParameters.FAVORITE;
import static by.tms.eshop.utils.Constants.RequestParameters.PRODUCT_PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH;
import static by.tms.eshop.utils.Constants.RequestParameters.TRUE;

@Component
@RequiredArgsConstructor
public class Facade {

    private final CartService cartService;
    private final OrderService orderService;
    private final ProductService productService;

    public void carriesPurchase(Long userId) {
        List<ProductDto> productsDto = cartService.getPurchasedProducts(userId, DtoUtils.selectCart());
//        List<ProductDto> productsDto = cartService.getPurchasedProducts(userId, true, false);
        orderService.saveUserOrder(userId, productsDto);
        cartService.deleteCartProductsAfterBuy(userId);
    }

    public String getPathFromAddCartByParameters(Long productId, String shopFlag, String location) {
        String path;
        if (Objects.equals(shopFlag, TRUE)) {
            path = REDIRECT_TO_CART;
        } else if (Objects.equals(location, FAVORITE)) {
            path = REDIRECT_TO_FAVORITES;
        } else if (Objects.equals(location, SEARCH)) {
            path = REDIRECT_TO_SEARCH_RESULT_SAVE;
        } else if (Objects.equals(location, PRODUCT_PAGE)) {
            path = REDIRECT_TO_PRODUCT_WITH_PARAM + productId;
        } else {
            String productType = productService.getProductTypeValue(productId);
            path = REDIRECT_TO_PRODUCTS_PAGE_TYPE_WITH_PARAM + productType;
        }
        return path;
    }
}
