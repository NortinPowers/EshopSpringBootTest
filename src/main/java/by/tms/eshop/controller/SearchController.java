package by.tms.eshop.controller;

import by.tms.eshop.service.Facade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.tms.eshop.utils.Constants.MappingPath.REDIRECT_TO_SEARCH_RESULT_SAVE;
import static by.tms.eshop.utils.Constants.MappingPath.SEARCH_PATH;
import static by.tms.eshop.utils.Constants.RequestParameters.FILTER;
import static by.tms.eshop.utils.Constants.RequestParameters.SEARCH_CONDITION;
import static by.tms.eshop.utils.Constants.RequestParameters.SELECT;
import static by.tms.eshop.utils.ControllerUtils.removeUnsavedAttribute;
import static by.tms.eshop.utils.ControllerUtils.setFilterAttribute;

@RestController
@RequiredArgsConstructor
public class SearchController {

//    private final ProductService productService;
    private final Facade facade;

    @GetMapping("/search")
    public ModelAndView showSearchPage(HttpServletRequest request,
                                       HttpSession session,
                                       @RequestParam(required = false) String result,
                                       @RequestParam(required = false) String filter) {
//        HttpSession session = request.getSession();
        removeUnsavedAttribute(session, result);
        request.getServletContext().removeAttribute(FILTER);
        setFilterAttribute(request, filter);
        return new ModelAndView(SEARCH_PATH);
    }



    @PostMapping("/search-param")
    public ModelAndView showSearchPageByParam(HttpServletRequest request,
                                              HttpSession session,
                                              @RequestParam(name = SEARCH_CONDITION) String searchCondition) {
        request.getServletContext().removeAttribute(FILTER);
        facade.returnProductsBySearchCondition(session, searchCondition);
        return new ModelAndView(REDIRECT_TO_SEARCH_RESULT_SAVE);
    }

//    private void returnProductsByEmptyCondition(HttpSession session, String searchCondition) {
//        if (!searchCondition.isEmpty()) {
//            Set<ProductDto> products = productService.getFoundProducts(searchCondition);
////            HttpSession session = request.getSession();
//            session.setAttribute(FOUND_PRODUCTS, products);
//        }
//    }

    @PostMapping("/search-filter")
    public ModelAndView showSearchPageByFilter(HttpServletRequest request,
                                               @RequestParam(required = false, name = SELECT) String type) {
//        BigDecimal minPrice = getPrice(request, MIN_PRICE, BigDecimal.ZERO);
//        BigDecimal maxPrice = getPrice(request, MAX_PRICE, new BigDecimal(Long.MAX_VALUE));
        return new ModelAndView(facade.getSearchFilterResultPagePath(request, type));
//        return new ModelAndView(facade.getSearchFilterResultPagePath(request, minPrice, maxPrice, type));
    }

//    private String getSearchFilterResultPagePath(HttpServletRequest request, BigDecimal minPrice, BigDecimal maxPrice, String type) {
//        String path;
//        Set<ProductDto> products;
//        HttpSession session = request.getSession(false);
//        if (session.getAttribute(FOUND_PRODUCTS) != null) {
//            products = (Set<ProductDto>) session.getAttribute(FOUND_PRODUCTS);
//            products = applyPriceFilterOnProducts(minPrice, maxPrice, products);
//            products = applyTypeFilterOnProducts(type, products);
//            session.setAttribute(FILTER_FOUND_PRODUCTS, products);
//            path = REDIRECT_TO_SEARCH_FILTER_TRUE_RESULT_SAVE;
//        } else {
//            products = productService.selectAllProductsByFilter(type, minPrice, maxPrice);
//            session.setAttribute(FOUND_PRODUCTS, products);
//            path = REDIRECT_TO_SEARCH_RESULT_SAVE;
//        }
//        return path;
//    }
}