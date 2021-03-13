package ru.geekbrains.spring.springdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.geekbrains.spring.springdata.exceptions.BadRequestException;
import ru.geekbrains.spring.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.springdata.model.Product;
import ru.geekbrains.spring.springdata.model.SortDirection;
import ru.geekbrains.spring.springdata.model.dtos.ProductDto;
import ru.geekbrains.spring.springdata.services.ProductService;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")

public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    // http://localhost:8189/geek/api/v1/products/add
    @PostMapping("/add")
    public ProductDto add(@RequestBody ProductDto product) {
        return productService.add(product).orElseThrow(() -> new BadRequestException("Can't add product"));
    }

    @DeleteMapping("/del/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    // http://localhost:8189/geek/api/v1/1
    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return productService.getByIdDto(id).orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product with id: %d doesn't exist", id)));
    }

    // http://localhost:8189/geek/api/v1/products/name/Iphone X
    @GetMapping("/name/{name}")
    public ProductDto getByName(@PathVariable String name) {
        return productService.getByNameDto(name).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Product with name: '%s' doesn't exist", name)));
    }

    // http://localhost:8189/geek/api/v1/products/filter?min=300&max=500
    @GetMapping("/filter")
    public ProductDto getAll(@RequestParam Integer min, @RequestParam Integer max) {
        return productService.getFilteredByPrice(min, max).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Products with price %d to %d are not available", min, max)));
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

    // http://localhost:8189/geek/product/sorted?name=ASC&price=DESC
    @GetMapping("/sorted")
    public List<Product> getAllSorted(@RequestParam Map<String, String> params) {
        List<Product> productList;
        try {
            productList = productService.getAllSorted(params);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return productList;
    }
}
