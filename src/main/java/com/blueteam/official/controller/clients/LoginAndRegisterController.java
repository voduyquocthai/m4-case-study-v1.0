package com.blueteam.official.controller.clients;

import com.blueteam.official.model.Role;
import com.blueteam.official.model.User;
import com.blueteam.official.model.UserForm;
import com.blueteam.official.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value(value = "C:\\Users\\thait\\OneDrive\\Desktop\\case_module_4\\src\\main\\resources\\static\\client\\img\\user-avatar\\")
    private String fileUpload;

    @GetMapping("/login")
    public ModelAndView showFormLogin() {
        return new ModelAndView("/client/sign-in","user",new User());
    }

    @PostMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        ModelAndView modelAndView;
        String message = "Username or password aren't incorrect";
        if (bindingResult.hasFieldErrors()) {
            modelAndView = new ModelAndView("/client/sign-in");
            return modelAndView;
        } else {
            if (checkUser(user)) {
                return new ModelAndView("/client/index");
            } else {
                modelAndView = new ModelAndView("/client/sign-in");
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
    public ModelAndView logout() {
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

    @GetMapping("/home")
    public String home(){
        return "/client/index";
    }
}