package ru.geekbrains.spring.springdata.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.springdata.exceptions.BadRequestException;
import ru.geekbrains.spring.springdata.model.Product;
import ru.geekbrains.spring.springdata.model.SortDirection;
import ru.geekbrains.spring.springdata.model.dtos.ProductDto;
import ru.geekbrains.spring.springdata.model.mappers.DtoMapper;
import ru.geekbrains.spring.springdata.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DtoMapper dtoMapper;

    @Transactional
    public List<Product> getAll() {
        return productRepository.findAll(Sort.by("name").and(Sort.by("price")));
    }

    @Transactional
    public List<Product> getAllByPrice(SortDirection direction) {
        return direction == SortDirection.ASC?
                productRepository.findAll(Sort.by("price").ascending()):
                productRepository.findAll(Sort.by("price").descending());
    }

    @Transactional
    public List<Product> getAllPaged(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size)).toList();
    }

    @Transactional
    public List<Product> getAllSorted(Map<String, String> sortParams) {
        List<Product> productList;
        try {
            List<Sort.Order> sortOrder = new ArrayList<>();
            sortParams.forEach((key, value) -> sortOrder.add(new Sort.Order(Sort.Direction.valueOf(value), key)));
            productList = productRepository.findAll(Sort.by(sortOrder));
        } catch (RuntimeException e) {
            throw new BadRequestException(String.format("Sorted request error: %s", e.getMessage()));
        }
        return productList;
    }

    public Product getById(Long id) {
        return productRepository.findById(id).get();
    }

    public Optional<ProductDto> getByIdDto(Long id) {
        return productRepository.findById(id).map(ProductDto::new);
    }

    public Product getByName(String name) {
        return productRepository.findProductByName(name).get();
    }

    public Optional<ProductDto> getByNameDto(String name) {
        return productRepository.findProductByName(name).map(ProductDto::new);
    }

    public Optional<ProductDto> getFilteredByPrice(int min, int max) {
        return productRepository.findAllByPriceBetween(min, max).map(ProductDto::new);
    }

    public Optional<ProductDto> add(ProductDto product) {
        return Optional.of(dtoMapper.toDto(productRepository.save(dtoMapper.toEntity(product))));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}
