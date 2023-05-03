package by.tms.eshop.service;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.dto.UserDto;
import by.tms.eshop.dto.UserValidationDto;
import by.tms.eshop.model.User;
import by.tms.eshop.utils.DtoUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static by.tms.eshop.utils.Constants.Attributes.FILTER_FOUND_PRODUCTS;
import static by.tms.eshop.utils.Constants.Attributes.FOUND_PRODUCTS;
import static by.tms.eshop.utils.Constants.ErrorMessage.RECHECK_DATA;
import static by.tms.eshop.utils.Constants.MappingPath.ESHOP;
import static by.tms.eshop.utils.Constants.MappingPath.LOGIN;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_CART;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_FAVORITES;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCTS_PAGE_TYPE_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_PRODUCT_WITH_PARAM;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.RequestParameters.FAVORITE;
import static by.tms.eshop.utils.Constants.RequestParameters.MAX_PRICE;
import static by.tms.eshop.utils.Constants.RequestParameters.MIN_PRICE;
import static by.tms.eshop.utils.Constants.RequestParameters.PRODUCT_PAGE;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH;
import static by.tms.eshop.utils.Constants.RequestParameters.TRUE;
import static by.tms.eshop.utils.ControllerUtils.getPrice;
import static by.tms.eshop.utils.ControllerUtils.isVerifyUser;
import static by.tms.eshop.utils.ControllerUtils.saveUserSession;
import static by.tms.eshop.utils.DtoUtils.makeUserDtoModelTransfer;
import static by.tms.eshop.utils.DtoUtils.makeUserModelTransfer;
import static by.tms.eshop.utils.ServiceUtils.getProductByFilter;

@Component
@RequiredArgsConstructor
public class Facade {

    private final CartService cartService;
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;


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

    public String getSearchFilterResultPagePath(HttpServletRequest request, String type) {
//    public String getSearchFilterResultPagePath(HttpServletRequest request, BigDecimal minPrice, BigDecimal maxPrice, String type) {
        BigDecimal minPrice = getPrice(request, MIN_PRICE, BigDecimal.ZERO);
        BigDecimal maxPrice = getPrice(request, MAX_PRICE, new BigDecimal(Long.MAX_VALUE));
        String path;
//        Set<ProductDto> products;
        HttpSession session = request.getSession(false);
        if (session.getAttribute(FOUND_PRODUCTS) != null) {
//            products = getProductByFilter(session, type, minPrice, maxPrice);
            session.setAttribute(FILTER_FOUND_PRODUCTS, getProductByFilter(session, type, minPrice, maxPrice));
//            session.setAttribute(FILTER_FOUND_PRODUCTS, products);
            path = REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE;
        } else {
//            products = productService.selectAllProductsByFilter(type, minPrice, maxPrice);
            session.setAttribute(FOUND_PRODUCTS, productService.selectAllProductsByFilter(type, minPrice, maxPrice));
//            session.setAttribute(FOUND_PRODUCTS, products);
            path = REDIRECT_TO_SEARCH_RESULT_SAVE;
        }
        return path;
    }

    public void returnProductsBySearchCondition(HttpSession session, String searchCondition) {
        if (!searchCondition.isEmpty()) {
            Set<ProductDto> products = productService.getFoundedProducts(searchCondition);
//            HttpSession session = request.getSession();
            session.setAttribute(FOUND_PRODUCTS, products);
        }
    }

    public void createAndLoginUser(HttpServletRequest request, UserValidationDto user) {
        User userEntity = makeUserModelTransfer(user);
        userService.addUser(userEntity);
        saveUserSession(request, makeUserDtoModelTransfer(userEntity));
    }

    public void checkLoginUser(HttpServletRequest request, UserValidationDto user, ModelAndView modelAndView) {
        Optional<User> incomingUser = userService.getUserByLogin(user.getLogin());
        if (incomingUser.isPresent() && isVerifyUser(incomingUser.get(), user.getPassword())) {
            UserDto userDto = makeUserDtoModelTransfer(incomingUser.get());
            saveUserSession(request, userDto);
            modelAndView.setViewName(ESHOP);
        } else {
            modelAndView.addObject("loginError", RECHECK_DATA);
            modelAndView.setViewName(LOGIN);
        }
    }

//    private static Set<ProductDto> getProductByFilter(HttpSession session, String type, BigDecimal minPrice, BigDecimal maxPrice) {
//        Set<ProductDto> products;
//        products = (Set<ProductDto>) session.getAttribute(FOUND_PRODUCTS);
//        products = applyPriceFilterOnProducts(minPrice, maxPrice, products);
//        products = applyTypeFilterOnProducts(type, products);
//        return products;
//    }
}
