package com.blueteam.official.controller.admin;

import com.blueteam.official.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class CartController {

    @Autowired
    private IProductService productService;

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
        return new ModelAndView("/client/shop/shopping-cart");
    }
}
