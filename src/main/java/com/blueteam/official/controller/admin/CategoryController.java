package com.blueteam.official.controller.admin;

import com.blueteam.official.model.Category;
import com.blueteam.official.model.User;
import com.blueteam.official.service.ICategoryService;
import com.blueteam.official.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    @Autowired
    private IUserService userService;

    @ModelAttribute("admin")
    private User getUser(Principal principal){
        return userService.findUserByUserName(principal.getName());
    }
    @GetMapping("/list")
    public ModelAndView showAll(@RequestParam("name") Optional<String> name, @PageableDefault(size = 5) Pageable pageable){
        Page<Category> categories;
        if(name.isPresent()){
            String query = "%" + name.get() + "%";
            categories = categoryService.findAllCategoryByNameUsingQuery(query, pageable);
        } else {
            categories =  categoryService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/admin/category/list");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showFormCreate() {
        ModelAndView modelAndView = new ModelAndView("/admin/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createCategory(@ModelAttribute("category") Category category){
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/admin/category/create");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id){
        Optional<Category> categoryOptional = categoryService.findById(id);
        ModelAndView modelAndView;
        if(categoryOptional.isPresent()){
            modelAndView = new ModelAndView("/admin/category/edit");
            Category category = categoryOptional.get();
            modelAndView.addObject("category", category);
        } else {
            modelAndView = new ModelAndView("error-404");
        }
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView updateCategory(@ModelAttribute Category category){
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/admin/category/edit");
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (categoryOptional.isPresent()){
            categoryService.remove(id);
            return "redirect:/admin/categories/list";
        } else {
            return "error-404";
        }
    }
}
