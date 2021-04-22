package com.blueteam.official.service;
import com.blueteam.official.model.CartItem;
import com.blueteam.official.model.Product;
import com.blueteam.official.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ICartService extends IGeneralService<CartItem> {
    Iterable<CartItem> findAll();

    Iterable<CartItem> findAllByUser(User user);

    CartItem save(CartItem cartItem);

    Page<CartItem> findAll(Pageable pageable);

    Optional<CartItem> findById(Long id);

    Optional<Product> findByProductId(Long productId);

    void remove(Long id);

    CartItem findCartItem(User user, Product product);

    void deleteCartItemByUser(Long id);

}
