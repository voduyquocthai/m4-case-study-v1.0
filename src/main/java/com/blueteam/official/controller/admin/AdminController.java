package com.blueteam.official.controller.admin;

import com.blueteam.official.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public ModelAndView showAll(){
        ModelAndView modelAndView = new ModelAndView("/admin/index");
        return modelAndView;
    }
}
