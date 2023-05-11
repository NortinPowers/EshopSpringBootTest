package by.tms.eshop.service.impl;

import by.tms.eshop.repository.JdbcProductCategoryRepository;
import by.tms.eshop.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final JdbcProductCategoryRepository jdbcProductCategoryRepository;

    @Override
    public  List<String> getProductCategories() {
        return jdbcProductCategoryRepository.getProductCategory();
    }
}
