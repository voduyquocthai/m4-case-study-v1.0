package com.blueteam.official.controller.admin;

import com.blueteam.official.model.CartItem;
import com.blueteam.official.model.Product;
import com.blueteam.official.service.ICartService;
import com.blueteam.official.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class CartController {

    @Autowired
    private ICartService cartService;
    @Autowired
    private IProductService productService;

    @ModelAttribute("product")
    public Page<Product> listProduct(Pageable pageable) {
        return  productService.findAll(pageable);
    }

    @GetMapping("shop")
    public ModelAndView shop() {
        return new ModelAndView("/client/shop/shop");
    }

    @GetMapping("shop-details")
    public ModelAndView shopdetails() {
        return new ModelAndView("/client/shop/shop-details");
    }

    @GetMapping("/shoppingcart")
    public ModelAndView cart() {
        ModelAndView modelAndView = new ModelAndView("/client/shop/shopping-cart");
        Iterable<CartItem> cartItems = cartService.findAll();
        modelAndView.addObject("cartItems",cartItems);
        return modelAndView;
    }




}
