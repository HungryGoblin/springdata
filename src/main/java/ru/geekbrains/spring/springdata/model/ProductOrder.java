package ru.geekbrains.spring.springdata.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.springdata.model.mappers.DtoMapper;
import ru.geekbrains.spring.springdata.repository.ProductRepository;
import ru.geekbrains.spring.springdata.services.ProductService;

@Component
@Data
@NoArgsConstructor
public class ProductOrder {

    @Autowired
    private ProductService productService;

    @Autowired
    private DtoMapper dtoMapper;

    @Autowired
    private ProductRepository productRepository;

    private long id;
    private String name;
    private int number;
    private int price;


    // productService == null !!!!
    public ProductOrder(long productId, int number) {
        Product product = productService
                .getById(productId)
                .orElseThrow(()-> new ResourceNotFoundException(
                        String.format("Product with id: d% does not exist", productId)));
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.number = number;
    }

    public int getTotal () {
        return this.number * this.price;
    }
}
