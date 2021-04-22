package com.blueteam.official.service.Impl;


import com.blueteam.official.model.CartItem;
import com.blueteam.official.model.Product;
import com.blueteam.official.model.User;
import com.blueteam.official.repository.ICartItemRepository;
import com.blueteam.official.repository.IProductRepository;
import com.blueteam.official.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private ICartItemRepository cartItemRepository;
    @Override
    public Iterable<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public Iterable<CartItem> findAllByUser(User user) {
        return cartItemRepository.findAllByUser(user);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Page<CartItem> findAll(Pageable pageable) {
        return cartItemRepository.findAll(pageable);
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public Optional<Product> findByProductId(Long productId) {
        return cartItemRepository.findByProductId(productId);
    }

    @Override
    public void remove(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public CartItem findCartItem(User user, Product product) {
        return cartItemRepository.findCartItem(user, product);
    }

    @Override
    @Transactional
    public void deleteCartItemByUser(Long id) {
        cartItemRepository.deleteCartItemByUser(id);
    }

}
