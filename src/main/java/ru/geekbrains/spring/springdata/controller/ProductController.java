package ru.geekbrains.spring.springdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.geekbrains.spring.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.springdata.model.SortDirection;
import ru.geekbrains.spring.springdata.model.dtos.ProductDto;
import ru.geekbrains.spring.springdata.model.mappers.DtoMapper;
import ru.geekbrains.spring.springdata.services.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")

public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private DtoMapper dtoMapper;

    @GetMapping
    @ResponseBody
    public List<ProductDto> getAll() {
        return dtoMapper.toProductDtoList(productService.getAll());
    }

    // http://localhost:8189/geek/api/v1/products/add
    @PostMapping("/add")
    @ResponseBody
    public ProductDto add(@RequestBody ProductDto productDto) {
        return dtoMapper.toProductDto(productService.add(dtoMapper.toProduct(productDto)));
    }

    @DeleteMapping("/del/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    // http://localhost:8189/geek/api/v1/1
    @GetMapping("/{id}")
    @ResponseBody
    public ProductDto findProductById(@PathVariable Long id) {
        return productService
                .getById(id)
                .map(ProductDto::new)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product with id: %d doesn't exist", id)));
    }

    // http://localhost:8189/geek/api/v1/products/name/Iphone X
    @GetMapping("/name/{name}")
    @ResponseBody
    public ProductDto getByName(@PathVariable String name) {
        return productService
                .getByName(name)
                .map(ProductDto::new)
                .orElseThrow(() -> new ResourceNotFoundException(
                String.format("Product with name: '%s' doesn't exist", name)));
    }

    // http://localhost:8189/geek/api/v1/products/filter?min=300&max=500
    @GetMapping("/filter")
    @ResponseBody
    public ProductDto getAll(@RequestParam Integer min, @RequestParam Integer max) {
        return productService
                .getFilteredByPrice(min, max)
                .map(ProductDto::new)
                .orElseThrow(() -> new ResourceNotFoundException(
                String.format("Products with price %d to %d are not available", min, max)));
    }

    private static final int DEFAULT_PAGE_SIZE = 10;
    // http://localhost:8189/geek/product/paged?page=2&size=3
    @GetMapping("paged")
    @ResponseBody
    public List<ProductDto> getAllPaged(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        if (page == null || page <= 0) page = 1;
        if (size == null || size <= 0) size = DEFAULT_PAGE_SIZE;
        return dtoMapper.toProductDtoList(productService.getAllPaged(page, size));
    }

    // http://localhost:8189/geek/product/byprice
    @GetMapping("/byprice")
    @ResponseBody
    public List<ProductDto> getAllByPrice() {
        return dtoMapper.toProductDtoList(productService.getAllByPrice(SortDirection.ASC));
    }

    // http://localhost:8189/geek/product/bypricedesc
    @GetMapping("/bypricedesc")
    @ResponseBody
    public List<ProductDto> getAllByPriceDesc() {
        return dtoMapper.toProductDtoList(productService.getAllByPrice(SortDirection.DESC));
    }

    // http://localhost:8189/geek/product/sorted?name=ASC&price=DESC
    @GetMapping("/sorted")
    @ResponseBody
    public List<ProductDto> getAllSorted(@RequestParam Map<String, String> params) {
        List<ProductDto> productList;
        try {
            productList = dtoMapper.toProductDtoList(productService.getAllSorted(params));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return productList;
    }
}
