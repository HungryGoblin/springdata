package ru.geekbrains.spring.springdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.springdata.model.Product;
import ru.geekbrains.spring.springdata.model.SortDirection;
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

    // http://localhost:8189/geek/product/filter?min=300&max=500
    @GetMapping("/filter")
    public List<Product> getAll(@RequestParam Integer min, @RequestParam Integer max) {
        return productService.getFiltered(min, max);
    }

    // http://localhost:8189/geek/product/pricege?from=300
    @GetMapping("/pricege")
    public List<Product> getPriceFrom(@RequestParam Integer from) {
        return productService.getPriceFrom(from);
    }

    // http://localhost:8189/geek/product/pricele?to=300
    @GetMapping("/pricele")
    public List<Product> getPriceTo(@RequestParam Integer to) {
        return productService.getPriceTo(to);
    }

    @GetMapping("/find")
    public List<Product> getBYNamSortDirection(@RequestParam Integer to) {
        return productService.getPriceTo(to);
    }

    private static final int DEFAULT_PAGE_SIZE = 10;
    // http://localhost:8189/geek/product/paged?page=2&size=3
    @GetMapping("paged")
    public List<Product> getAllPaged(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        if (page == null || page <= 0) page = 1;
        if (size == null || size <= 0) size = DEFAULT_PAGE_SIZE;
        return productService.getAllPaged(page, size);
    }

    // http://localhost:8189/geek/product/byprice
    @GetMapping("/byprice")
    public List<Product> getAllByPrice() {
        return productService.getAllByPrice(SortDirection.ASC);
    }

    // http://localhost:8189/geek/product/bypricedesc
    @GetMapping("/bypricedesc")
    public List<Product> getAllByPriceDesc() {
        return productService.getAllByPrice(SortDirection.DESC);
    }

    // http://localhost:8189/geek/product/sorted?byPrice=true&byName=false
    @GetMapping("sorted")
    public List<Product> getSorted(
            @RequestParam Boolean byPrice,
            @RequestParam Boolean byName) {
        SortDirection sortByPrice;
        SortDirection sortByName;
        if (byPrice == null || byPrice) sortByPrice = SortDirection.ASC;
        else sortByPrice = SortDirection.DESC;
        if (byName == null || byName) sortByName = SortDirection.ASC;
        else sortByName = SortDirection.DESC;
        return productService.getSorted(sortByPrice, sortByName);
    }

}
