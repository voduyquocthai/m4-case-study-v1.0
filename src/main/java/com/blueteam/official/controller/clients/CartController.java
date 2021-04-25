package com.blueteam.official.controller.clients;

import com.blueteam.official.model.CartItem;
import com.blueteam.official.model.Product;
import com.blueteam.official.model.User;
import com.blueteam.official.service.ICartService;
import com.blueteam.official.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private IUserService userService;

    @Autowired
    private ICartService cartItemService;

    @ModelAttribute("user")
    public User getCurrentUser(Principal principal) {
        return userService.findUserByUserName(principal.getName());
    }



    @GetMapping("/show/{username}")
    public ModelAndView showCart(@PathVariable("username") String username) {
        User user = userService.findUserByUserName(username);
        Iterable<CartItem> cartItems = cartItemService.findAllByUser(user);
        ModelAndView modelAndView = new ModelAndView("client/shop/shopping-cart");
        double sum = 0.0;
        for (CartItem cartItem : cartItems) {
            sum += (cartItem.getProduct().getPrice() * cartItem.getQuantity());
        }
        modelAndView.addObject("cartItems", cartItems);
        modelAndView.addObject("sum", sum);
        modelAndView.addObject("username", username);
        return modelAndView;

    }

    @GetMapping("/add/{username}/{productId}")
    public String addItem(@PathVariable("username") String username, @PathVariable("productId") Long productId) {
        User user = userService.findUserByUserName(username);
        CartItem cartItem = new CartItem();
        Optional<Product> product = cartItemService.findByProductId(productId);
        if (product.isPresent()) {
            cartItem.setUser(user);
            cartItem.setProduct(product.get());
            CartItem existedCartItem = cartItemService.findCartItem(user, product.get());
            if (existedCartItem == null) {
                cartItem.setQuantity(1);
            } else {
                cartItem.setId(existedCartItem.getId());
                cartItem.setQuantity(existedCartItem.getQuantity() + 1);
            }
            cartItemService.save(cartItem);
        } else {
            return null;
        }
        return "redirect:/guests/shop";
    }

    @GetMapping("/update/{username}/{cartItemId}")
    public String updateItem(@PathVariable("cartItemId") Long cartItemId, @RequestParam("action") String action, @PathVariable("username") String username) {
        Optional<CartItem> cartItemToUpdate = cartItemService.findById(cartItemId);
        if (cartItemToUpdate.isPresent()) {
            if (action.equals("decrease")) {
                if (cartItemToUpdate.get().getQuantity() == 1) {
                    cartItemService.deleteCartItemByUser(cartItemId);
                } else {
                    cartItemToUpdate.get().setQuantity(cartItemToUpdate.get().getQuantity() - 1);
                }
            } else if (action.equals("increase")) {
                cartItemToUpdate.get().setQuantity(cartItemToUpdate.get().getQuantity() + 1);
            }
            cartItemService.save(cartItemToUpdate.get());
        }

        return "redirect:/cart/show/{username}";
    }

    @GetMapping("/delete/{cartItemId}/{username}")
    public String deleteItem(@PathVariable("cartItemId") Long id, @PathVariable("username") String username) {
        cartItemService.deleteCartItemByUser(id);
        return "redirect:/cart/show/{username}";
    }
}

