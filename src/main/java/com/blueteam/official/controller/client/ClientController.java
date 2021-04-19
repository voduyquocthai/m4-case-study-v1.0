package com.blueteam.official.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/client")
public class ClientController {
    @GetMapping("/home")
    public ModelAndView showAll(){
        ModelAndView modelAndView = new ModelAndView("/client/index");
        return modelAndView;
    }
}
