package com.blueteam.official.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class HomeController {
    @GetMapping("/index")
    public ModelAndView home() {
        return new ModelAndView("/client/index");
    }

}
