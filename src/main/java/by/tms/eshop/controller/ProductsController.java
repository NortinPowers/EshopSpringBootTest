package by.tms.eshop.controller;

import by.tms.eshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.tms.eshop.utils.Constants.QueryParameter.CATEGORY;
import static by.tms.eshop.utils.Constants.RequestParameters.ID;

@RestController
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

//    @GetMapping("/products-page")
//    public ModelAndView showProductsPage(@RequestParam(CATEGORY) String category) {
//        return productService.getProductsByCategory(category);
//    }

    @GetMapping("/products-page")
    public ModelAndView showProductsPage(@RequestParam(CATEGORY) String category,
                                         @PageableDefault(sort = "id") Pageable pageable) {
        return productService.getProductsByCategory(category, pageable);
    }

//    @GetMapping("/products-page")
//    public ModelAndView showProductsPage(@RequestParam(CATEGORY) String category,
//                                         @RequestParam(required = false, defaultValue = "0") int page,
//                                         @RequestParam(required = false, defaultValue = "5") int size) {
//        return productService.getProductsByCategory(category, PageRequest.of(page, size));
//    }

    @GetMapping("/product/{id}")
    public ModelAndView showProductPage(@PathVariable(ID) Long id) {
        return productService.getProduct(id);
    }
}