package com.blueteam.official.controller.admin;

import com.blueteam.official.model.Category;
import com.blueteam.official.model.Product;
import com.blueteam.official.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

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
        modelAndView.addObject("products", categories);
        return modelAndView;
    }
}
