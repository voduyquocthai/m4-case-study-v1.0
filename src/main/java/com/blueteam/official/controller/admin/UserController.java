package com.blueteam.official.controller.admin;

import com.blueteam.official.model.User;
import com.blueteam.official.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    private ModelAndView showAllUser(@PageableDefault Pageable pageable){
        Page<User> users = userService.findAll(pageable);
        return new ModelAndView("/admin/user/list","users",users);
    }

}
