package com.blueteam.official.controller.admin;

import com.blueteam.official.model.Role;
import com.blueteam.official.model.User;
import com.blueteam.official.model.UserForm;
import com.blueteam.official.service.IRoleService;
import com.blueteam.official.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Value(value = "${upload.path.avatar}")
    private String uploadFile;

    @ModelAttribute("admin")
    private User getUser(Principal principal){
        return userService.findUserByUserName(principal.getName());
    }

    @GetMapping("/list")
    private ModelAndView showAllUser(@PageableDefault(5) Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        return new ModelAndView("/admin/user/list", "users", users);
    }

    @GetMapping("/edit/{id}")
    private ModelAndView showFormEdit(@PathVariable("id") Long id) {
        ModelAndView modelAndView;
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            modelAndView = new ModelAndView("/admin/user/edit");
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

    @PostMapping("/edit")
    private ModelAndView edit(@ModelAttribute("userForm") UserForm userForm) {
        MultipartFile multipartFile = userForm.getImage();
        String filename = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(userForm.getImage().getBytes(), new File(this.uploadFile + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User(userForm.getId(), userForm.getUsername(), userForm.getPassword(), userForm.getEmail(), userForm.getAddress(), userForm.getPhoneNumber(), filename, userForm.getRole());
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/admin/user/edit");
        modelAndView.addObject("userForm", userForm);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            userService.remove(id);
            return "redirect:/users/list";
        } else {
            return "error-404";
        }
    }

    @GetMapping("/create")
    private ModelAndView createUser(){
        ModelAndView modelAndView = new ModelAndView("/admin/user/create");
        Iterable<Role> roles = roleService.findAll();
        modelAndView.addObject("userForm", new UserForm());
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }

    @PostMapping("/create")
    private ModelAndView create(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult){
        ModelAndView modelAndView;
        if (bindingResult.hasFieldErrors()) {
            modelAndView = new ModelAndView("/admin/user/create");
            return modelAndView;
        }
        MultipartFile multipartFile = userForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(userForm.getImage().getBytes(), new File(this.uploadFile + fileName));
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
        modelAndView = new ModelAndView("redirect:/users/list");
        return modelAndView;
    }

    @GetMapping("/search")
    private ModelAndView search(@RequestParam("key") String key,@PageableDefault(5) Pageable pageable){
        Page<User> userPage = userService.findAllByKey("%"+key+"%",pageable);
        return new ModelAndView("/admin/user/list","users",userPage);
    }
}
