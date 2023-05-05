package by.tms.eshop.service.impl;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.repository.JdbcProductRepository;
import by.tms.eshop.service.ProductService;
import by.tms.eshop.utils.Constants.Attributes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Set;

import static by.tms.eshop.utils.Constants.MappingPath.PRODUCT;
import static by.tms.eshop.utils.Constants.MappingPath.PRODUCTS;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final JdbcProductRepository jdbcProductRepository;

    @Override
    public ModelAndView getProductsByType(String type) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(Attributes.PRODUCTS, jdbcProductRepository.getProductsByType(type));
        return new ModelAndView(PRODUCTS, modelMap);
    }

    @Override
    public ModelAndView getProduct(Long id) {
        ModelMap modelMap = new ModelMap(Attributes.PRODUCT, jdbcProductRepository.getProduct(id));
        return new ModelAndView(PRODUCT, modelMap);
    }

    @Override
    public String getProductTypeValue(Long id) {
        return jdbcProductRepository.getProductTypeValue(id);
    }

    @Override
    public Set<ProductDto> getFoundedProducts(String searchCondition) {
        return jdbcProductRepository.getFoundedProducts(searchCondition);
    }

    @Override
    public Set<ProductDto> selectAllProductsByFilter(String type, BigDecimal minPrice, BigDecimal maxPrice) {
        return jdbcProductRepository.selectAllProductsByFilter(type, minPrice, maxPrice);
    }
}