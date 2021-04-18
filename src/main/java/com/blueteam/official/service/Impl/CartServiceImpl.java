package com.blueteam.official.service.Impl;


import com.blueteam.official.model.CartItem;
import com.blueteam.official.model.Product;
import com.blueteam.official.repository.ICartItemRepository;
import com.blueteam.official.repository.IProductRepository;
import com.blueteam.official.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private ICartItemRepository cartItemRepository;
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Iterable<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public Page<CartItem> findAll(Pageable pageable) {
        return cartItemRepository.findAll(pageable);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        cartItemRepository.deleteById(id);
    }


}
