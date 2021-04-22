package com.blueteam.official.service;

import com.blueteam.official.model.Category;
import com.blueteam.official.model.Product;
import com.blueteam.official.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService extends IGeneralService<Category> {
    Page<Category> findAllCategoryByNameUsingQuery(String name, Pageable pageable);
}
