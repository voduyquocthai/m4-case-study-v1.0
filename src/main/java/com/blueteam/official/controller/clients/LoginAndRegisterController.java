package com.blueteam.official.controller.clients;


import com.blueteam.official.model.Product;
import com.blueteam.official.model.Role;
import com.blueteam.official.model.User;
import com.blueteam.official.model.UserForm;
import com.blueteam.official.service.IProductService;
import com.blueteam.official.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
public class LoginAndRegisterController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IProductService productService;

    @Value(value = "${upload.path.avatar}")
    private String fileUpload;

    @GetMapping("/login")
    public ModelAndView showFormLogin() {
        return new ModelAndView("/client/sign-in","user",new User());
    }

    @PostMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        ModelAndView modelAndView;
        if (bindingResult.hasFieldErrors()) {
            modelAndView = new ModelAndView("/client/sign-in");
            return modelAndView;
        } else {
            if (checkUser(user)) {
                return new ModelAndView("/client/index");
            } else {
                modelAndView = new ModelAndView("/client/sign-in");
                String message = "Username or password aren't incorrect";
                modelAndView.addObject("message", message);
                return modelAndView;
            }
        }
    }

    private boolean checkUser(User user) {
        User user1 = userService.findUserByUserName(user.getUsername());
        if (user1 != null) {
            if (user.getPassword().equals(user1.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("/client/sign-up");
        modelAndView.addObject("userForm", new UserForm());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult) {
        ModelAndView modelAndView;
        if (bindingResult.hasFieldErrors()) {
            modelAndView = new ModelAndView("/client/sign-up");
            return modelAndView;
        }
        if (checkAccount(userForm.getUsername(),userForm.getEmail())){
            String message = "Username or email existed!!";
            return  new ModelAndView("/client/sign-up", "message",message);
        }
        MultipartFile multipartFile = userForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(userForm.getImage().getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        user.setEmail(userForm.getEmail());
        user.setAvatarUrl(fileName);
        user.setAddress(userForm.getAddress());
        user.setPhoneNumber(userForm.getPhoneNumber());
        Role appRole = new Role();
        appRole.setId(1L);
        user.setRole(appRole);
        userService.save(user);
        modelAndView = new ModelAndView("/client/sign-in","user",new User());
        return modelAndView;
    }

    private boolean checkAccount(String username, String email){
        User  user = userService.findUserByUserName(username);
        User  user1 = userService.findUserByEmail(email);
        if (user !=  null || user1 !=  null){
            return true;
        }
        return false;
    }

    @GetMapping("/home")
    public ModelAndView home(@PageableDefault(8) Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("/client/index");
        Page<Product> products = productService.findAll(pageable);
        modelAndView.addObject("products", products);
        return modelAndView;
    }

}
