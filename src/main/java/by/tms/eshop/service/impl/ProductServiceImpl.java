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
import static by.tms.eshop.utils.DtoUtils.makeProductDtoModelTransfer;
import static by.tms.eshop.utils.ServiceUtils.getProductDtoList;
import static by.tms.eshop.utils.ServiceUtils.getProductDtoSet;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final JdbcProductRepository jdbcProductRepository;

    @Override
    public ModelAndView getProductsByCategory(String category) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(Attributes.PRODUCTS, getProductDtoList(jdbcProductRepository.getProductsByCategory(category)));
        return new ModelAndView(PRODUCTS, modelMap);
    }

    @Override
    public ModelAndView getProduct(Long id) {
        ModelMap modelMap = new ModelMap(Attributes.PRODUCT, makeProductDtoModelTransfer(jdbcProductRepository.getProduct(id)));
        return new ModelAndView(PRODUCT, modelMap);
    }

    @Override
    public String getProductCategoryValue(Long id) {
        return jdbcProductRepository.getProductCategoryValue(id);
    }

    @Override
    public Set<ProductDto> getFoundedProducts(String searchCondition) {
        return getProductDtoSet(jdbcProductRepository.getFoundedProducts(searchCondition));
    }

    @Override
    public Set<ProductDto> selectAllProductsByFilter(String type, BigDecimal minPrice, BigDecimal maxPrice) {
        return getProductDtoSet(jdbcProductRepository.selectAllProductsByFilter(type, minPrice, maxPrice));
    }
}