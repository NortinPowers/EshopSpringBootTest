package by.tms.eshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.tms.eshop.utils.Constants.MappingPath.ESHOP;

@RestController
public class HomeController {

    @GetMapping(value = {"/","/eshop"})
    public ModelAndView redirectToEshopPage() {
        return new ModelAndView(ESHOP);
    }

}