package ru.geekbrains.spring.springdata.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.spring.springdata.model.Product;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private int price;

    public ProductDto(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.price = p.getPrice();
    }
}
