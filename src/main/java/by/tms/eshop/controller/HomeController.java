package by.tms.eshop.controller;

import by.tms.eshop.model.ProductType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static by.tms.eshop.utils.Constants.MappingPath.ESHOP;

@RestController
public class HomeController {

    @GetMapping(value = {"/","/eshop"})
    public ModelAndView redirectToEshopPage(ModelAndView modelAndView) {
        //табл

        List<String> productTypes = Arrays.stream(ProductType.values())
                .map(ProductType::getValue)
                .toList();

        modelAndView.addObject("productTypes", productTypes);
        modelAndView.setViewName(ESHOP);
        return modelAndView;
    }
}