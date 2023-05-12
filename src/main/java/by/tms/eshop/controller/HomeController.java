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
//    private final SessionFactory sessionFactory;

    @GetMapping(value = {"/","/eshop"})
    public ModelAndView redirectToEshopPage(ModelAndView modelAndView) {
//        Set<EntityType<?>> entities = sessionFactory.getMetamodel().getEntities();
//        for (EntityType<?> entity : entities) {
//            System.out.println(entity.getName());
//        }
        List<String> productCategories = productCategoryService.getProductCategories();
        modelAndView.addObject("productCategories", productCategories);
        modelAndView.setViewName(ESHOP);
        return modelAndView;
    }
}