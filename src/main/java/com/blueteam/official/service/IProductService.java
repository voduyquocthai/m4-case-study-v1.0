package com.blueteam.official.service;

import com.blueteam.official.model.Product;
import com.blueteam.official.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService extends IGeneralService<Product> {
    Page<Product> findAllByNameContaining(String name, Pageable pageable);

    Page<Product> findAllProductByNameUsingQuery(String name, Pageable pageable);
}
