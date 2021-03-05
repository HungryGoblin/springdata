package ru.geekbrains.spring.springdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.springdata.model.Product;
import ru.geekbrains.spring.springdata.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

//    @GetMapping("/price")
//    public List<Product> getAll(@RequestParam Integer first, @RequestParam Integer second) {
//        return productService.getAllByScore(first, second);
//    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/name")
    public Product getByName(@RequestParam String name) {
        return productService.getByName(name);
    }

    @PostMapping
        public Product add(@RequestBody Product product) {
        return productService.add(product);
    }

    @DeleteMapping("/del/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

}
