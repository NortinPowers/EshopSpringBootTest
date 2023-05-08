package by.tms.eshop.controller;

import by.tms.eshop.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static by.tms.eshop.utils.Constants.MappingPath.ESHOP;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final ProductCategoryService productCategoryService;

    @GetMapping(value = {"/","/eshop"})
    public ModelAndView redirectToEshopPage(ModelAndView modelAndView) {

//        List<String> productTypes = Arrays.stream(ProductType.values())
//                .map(ProductType::getValue)
//                .toList();

        List<String> productCategories = productCategoryService.getProductCategories();

        modelAndView.addObject("productCategories", productCategories);
//        modelAndView.addObject("productTypes", productTypes);
        modelAndView.setViewName(ESHOP);
        return modelAndView;
    }
}