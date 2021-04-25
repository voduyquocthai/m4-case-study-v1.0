package com.blueteam.official.controller.admin;

import com.blueteam.official.model.Category;
import com.blueteam.official.model.Product;
import com.blueteam.official.model.ProductForm;
import com.blueteam.official.model.User;
import com.blueteam.official.service.ICategoryService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;


@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IUserService userService;

    @Value(value = "${upload.path.product}")
    private String fileUpload;

    @ModelAttribute("categories")
    public Iterable<Category> getAllCategory(){
        Iterable<Category> categories = categoryService.findAll();
        return categories;
    }

    @ModelAttribute("admin")
    private User getUser(Principal principal){
        return userService.findUserByUserName(principal.getName());
    }

    @GetMapping("/list")
    public ModelAndView showAll(@RequestParam("name")Optional<String> name, @PageableDefault(size = 5) Pageable pageable){
        Page<Product> products;
        if(name.isPresent()){
            String query = "%" + name.get() + "%";
            products = productService.findAllProductByNameUsingQuery(query, pageable);
        } else {
            products =  productService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/admin/product/list");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showFormCreate() {
        ModelAndView modelAndView = new ModelAndView("/admin/product/create");
        Iterable<Category> categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("product", new ProductForm());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createNewProduct(
            @Valid @ModelAttribute("product") ProductForm productForm,
            BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            return new ModelAndView("product/create");
        }
        Product product = new Product();
        MultipartFile multipartFile = productForm.getImgUrl();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productForm.getImgUrl().getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setSize(productForm.getSize());
        product.setColor(productForm.getColor());
        product.setDescription(productForm.getDescription());
        product.setImgUrl(fileName);
        product.setCategory(productForm.getCategory());
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/admin/product/create");
        modelAndView.addObject("product", new ProductForm());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id){
        Optional<Product> productOptional = productService.findById(id);
        ModelAndView modelAndView;
        if(productOptional.isPresent()){
            modelAndView = new ModelAndView("/admin/product/edit");
            Product product = productOptional.get();
            ProductForm productForm = new ProductForm(product.getId(), product.getName(), product.getPrice(),
                    product.getColor(), product.getDescription(), product.getSize(), null, product.getCategory());
            modelAndView.addObject("product", productForm);
        } else {
            modelAndView = new ModelAndView("/error-404");
        }
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editProduct(@ModelAttribute ProductForm productForm){
        Product product = new Product();
        MultipartFile multipartFile = productForm.getImgUrl();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productForm.getImgUrl().getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setId(productForm.getId());
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setSize(productForm.getSize());
        product.setColor(productForm.getColor());
        product.setDescription(productForm.getDescription());
        product.setImgUrl(fileName);
        product.setCategory(productForm.getCategory());
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/admin/product/edit");
        modelAndView.addObject("product", productForm);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()){
            productService.remove(id);
            return "redirect:/admin/products/list";
        } else {
            return "error-404";
        }
    }
}
