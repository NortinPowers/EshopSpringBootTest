package by.tms.eshop.controller;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.service.CartService;
import by.tms.eshop.service.Facade;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static by.tms.eshop.utils.Constants.Attributes.CART_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.FULL_PRICE;
import static by.tms.eshop.utils.Constants.BUY;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.SHOPPING_CART;
import static by.tms.eshop.utils.Constants.MappingPath.SUCCESS_BUY;
import static by.tms.eshop.utils.Constants.RequestParameters.ID;
import static by.tms.eshop.utils.Constants.RequestParameters.LOCATION;
import static by.tms.eshop.utils.Constants.RequestParameters.SHOP;
import static by.tms.eshop.utils.ControllerUtils.getUserId;
import static by.tms.eshop.utils.DtoUtils.selectCart;

@RestController
@RequiredArgsConstructor
public class CartController {

//    private final ProductService productService;
    private final CartService cartService;
//    private final OrderService orderService;
    private final Facade facade;

    @GetMapping("/cart")
    public ModelAndView showCardPage(HttpSession session, ModelAndView modelAndView) {
        Long userId = getUserId(session);
        List<ImmutablePair<ProductDto, Integer>> cartProducts = cartService.getSelectedProducts(userId, selectCart());
//        List<ImmutablePair<ProductDto, Integer>> cartProducts = cartService.getSelectedProducts(userId, true, false);
        modelAndView.addObject(CART_PRODUCTS, cartProducts);
//        modelAndView.addObject(CART_PRODUCTS, cartService.getProductsFromCart(userId, true, false));
        modelAndView.addObject(FULL_PRICE, cartService.getProductsPrice(cartProducts));
        modelAndView.setViewName(SHOPPING_CART);
        return modelAndView;
    }

    @PostMapping("/cart-processing")
    public ModelAndView showCardProcessingPage(HttpSession session,
                                               @RequestParam String buy,
                                               ModelAndView modelAndView) {
//        Long userId = getUserId(session);
        if (buy.equalsIgnoreCase(BUY)) {
//            String orderNumber = "";
//            while (orderService.checkOrderNumber(orderNumber) || "".equals(orderNumber)) {
//                orderNumber = createOrderNumber(userId);
//            }
//            orderService.createOrder(orderNumber, userId);
//            orderService.createOrder(userId);
//            products.forEach(product -> orderService.saveProductInOrderConfigurations(finalOrderNumber, product));
            facade.carriesPurchase(getUserId(session));
//            facade.carriesPurchase(userId);
            modelAndView.setViewName(SUCCESS_BUY);
        } else {
            modelAndView.setViewName(REDIRECT_TO_CART);
        }
        return modelAndView;
    }

//    private void carriesPurchase(Long userId) {
//        List<ProductDto> productsDto = cartService.getPurchasedProducts(userId, true, false);
//        orderService.saveUserOrder(userId, productsDto);
//        cartService.deleteCartProductsAfterBuy(userId);
//    }

//    private void saveUserOrder(Long userId, List<ProductDto> productsDto) {
//        String order = orderService.createOrder(userId);
//        List<Product> products = getProductsFromDto(productsDto);
////            String finalOrderNumber = orderNumber;
//        products.forEach(product -> orderService.saveProductInOrderConfigurations(order, product));
//    }

    @GetMapping("/add-cart")
    public ModelAndView AddProductToCart(HttpSession session,
                                         @RequestParam(name = ID) Long productId,
                                         @RequestParam(name = SHOP) String shopFlag,
                                         @RequestParam(name = LOCATION) String location) {
//        Long userId = getUserId(session);
        cartService.addSelectedProduct(getUserId(session), productId, selectCart());
//        cartService.addSelectedProduct(userId, productId, selectCart());
//        cartService.addSelectedProduct(userId, productId, true, false);
        return new ModelAndView(facade.getPathFromAddCartByParameters(productId, shopFlag, location));
    }

    @GetMapping("/delete-cart")
    public ModelAndView deleteProductFromCart(HttpSession session,
                                              @RequestParam(name = ID) Long productId) {
//        cartService.deleteProduct(getUserId(session), productId, true, false);
        cartService.deleteProduct(getUserId(session), productId, selectCart());
        return new ModelAndView(REDIRECT_TO_CART);
    }

//    private String getPathFromAddCartByParameters(Long productId, String shopFlag, String location) {
//        String path;
//        if (Objects.equals(shopFlag, TRUE)) {
//            path = REDIRECT_TO_CART;
//        } else if (Objects.equals(location, FAVORITE)) {
//            path = REDIRECT_TO_FAVORITES;
//        } else if (Objects.equals(location, SEARCH)) {
//            path = REDIRECT_TO_SEARCH_RESULT_SAVE;
//        } else if (Objects.equals(location, PRODUCT_PAGE)) {
//            path = REDIRECT_TO_PRODUCT_WITH_PARAM + productId;
//        } else {
//            String productType = productService.getProductTypeValue(productId);
//            path = REDIRECT_TO_PRODUCTS_PAGE_TYPE_WITH_PARAM + productType;
//        }
//        return path;
//    }
}