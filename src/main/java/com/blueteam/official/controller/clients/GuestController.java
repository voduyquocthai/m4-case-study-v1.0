package com.blueteam.official.controller.clients;

import com.blueteam.official.model.CartItem;
import com.blueteam.official.model.Product;
import com.blueteam.official.model.User;
import com.blueteam.official.service.ICartService;
import com.blueteam.official.service.IProductService;
import com.blueteam.official.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/guests")

public class GuestController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProductService productService;

    @Autowired
    private ICartService cartItemService;


    @GetMapping("/forgot-password")
    private ModelAndView showFormGetPass(){
        return new ModelAndView("/client/forgot-pass");
    }

    private User checkEmail(String email){
        return userService.findUserByEmail(email);
    }

    @PostMapping("/forgot-password")
    private ModelAndView forgotPass(@RequestParam(value = "email") String email){
        User user = checkEmail(email);
        if (user != null){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Get Password");
            message.setText("Your password: " + user.getPassword());
            mailSender.send(message);
            String notify = "Please check your email";
            return new ModelAndView("/client/forgot-pass","notify",notify);
        }
        String notify1 = "Email address incorrect!!";
        return new ModelAndView("/client/forgot-pass","notify1",notify1);
    }

    @GetMapping("/shop")
    public ModelAndView showProductListForCustomer(Principal principal, @PageableDefault(size = 10) Pageable pageable, @RequestParam("q")Optional<String> name) {
        Page<Product> products;
        if (name.isPresent()) {
            String query = "%" + name.get() + "%";
            products = productService.findAllProductByNameUsingQuery(query, pageable);
        } else {
            products = productService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/client/shop/shop");
        modelAndView.addObject("products", products);
        if (principal != null) {
            modelAndView.addObject("username", principal.getName());
        }
        return modelAndView;
    }

    @GetMapping("/error-403")
    private String error(){
        return "error-403";
    }

    @GetMapping("/shop-details")
    public ModelAndView shopDetail(){
        return new ModelAndView("/client/shop/shop-details");
    }

    @GetMapping("/shopping-cart")
    public ModelAndView shopCart(){
        return new ModelAndView("/client/shop/shopping-cart");
    }

    @GetMapping("/about")
    public String about(){
        return "/client/about";
    }

    @GetMapping("/blogdetail")
    public String blogDetails(){
        return "/client/blog-details";
    }

    @GetMapping("/blog")
    public String blog(){
        return "/client/blog";
    }

    @GetMapping("/contact")
    public String contact(){
        return "/client/contact";
    }
}
