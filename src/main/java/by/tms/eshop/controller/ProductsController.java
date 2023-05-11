package by.tms.eshop.controller;

import by.tms.eshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.tms.eshop.utils.Constants.RequestParameters.CATEGORY;
import static by.tms.eshop.utils.Constants.RequestParameters.ID;

@RestController
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/products-page")
    public ModelAndView showProductsPage(@RequestParam(CATEGORY) String category) {
        return productService.getProductsByCategory(category);
    }

    @GetMapping("/product/{id}")
    public ModelAndView showProductPage(@PathVariable(ID) Long id) {
        return productService.getProduct(id);
    }
}