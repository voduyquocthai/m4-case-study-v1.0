package com.blueteam.official.controller.admin;

import com.blueteam.official.model.User;
import com.blueteam.official.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IUserService userService;

    @GetMapping("")
    public ModelAndView showAll(Principal principal){
        User admin = userService.findUserByUserName(principal.getName());
        return new ModelAndView("/admin/index","admin",admin);
    }
}
