package ru.geekbrains.spring.springdata.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.springdata.model.Product;
import ru.geekbrains.spring.springdata.model.SortDirection;
import ru.geekbrains.spring.springdata.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

//    public List<Product> getAll() {
//        return productRepository.findAll();
//    }

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
    public List<Product> getSorted (SortDirection byPrice, SortDirection byName) {
        /*
        как-то нужно сделать динамический запрос
        CriteriaBuilder & CriteriaQuery ?
        */
        return productRepository.findAll(Sort.by("price").ascending());
    }

    public Product getById(Long id) {
        return productRepository.findById(id).get();
    }

    public Product getByName(String name) {
        return productRepository.findProductByName(name);
    }

    public Product add(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getFiltered(int min, int max) {
        return productRepository.findAllByPriceBetween(min, max);
    }

    public List<Product> getPriceTo(Integer to) {
        return productRepository.findAllByPriceBefore(to);
    }

    public List<Product> getPriceFrom(Integer from) {
        return productRepository.findAllByPriceAfter(from);
    }
}
