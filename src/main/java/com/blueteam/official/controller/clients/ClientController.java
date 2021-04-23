package com.blueteam.official.controller.clients;

import com.blueteam.official.model.Role;
import com.blueteam.official.model.User;
import com.blueteam.official.model.UserForm;
import com.blueteam.official.service.IRoleService;
import com.blueteam.official.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;



    @Value(value = "C:\\Users\\thait\\OneDrive\\Desktop\\m4-case-study-v1.0\\src\\main\\resources\\static\\img\\user-avatar\\")
    private String uploadFile;

    @GetMapping("/userDetail")
    private ModelAndView getUserDetail(Principal principal) {
        if (principal != null) {
            User user = userService.findUserByUserName(principal.getName());
            return new ModelAndView("/client/user-detail", "user", user);
        } else return new ModelAndView("redirect:/login");
    }

    @GetMapping("/edit-profile/{id}")
    private ModelAndView showFormEditProfile(@PathVariable("id") Long id) {
        ModelAndView modelAndView;
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            modelAndView = new ModelAndView("/client/edit-profile");
            User user = userOptional.get();
            UserForm userForm = new UserForm(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getAddress(), null, user.getPhoneNumber(), user.getRole());
            List<Role> roles = (List<Role>) roleService.findAll();
            modelAndView.addObject("userForm", userForm);
            modelAndView.addObject("roles", roles);
            return modelAndView;
        } else {
            return new ModelAndView("error-404");
        }
    }

    @PostMapping("/edit-profile")
    private ModelAndView editProfile(@ModelAttribute("userForm") UserForm userForm) {
        MultipartFile multipartFile = userForm.getImage();
        String filename = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(userForm.getImage().getBytes(), new File(this.uploadFile + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User(userForm.getId(), userForm.getUsername(), userForm.getPassword(), userForm.getEmail(), userForm.getAddress(), userForm.getPhoneNumber(), filename, userForm.getRole());

        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("redirect:/clients/userDetail");
        modelAndView.addObject("userForm", userForm);
        return modelAndView;
    }

}
