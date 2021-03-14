package ru.geekbrains.spring.springdata.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.spring.springdata.model.Product;
import ru.geekbrains.spring.springdata.model.ProductOrder;

@Data
@NoArgsConstructor
public class ProductOrderDto {

    private long id;
    private String name;
    private int number;
    private int price;
    private int total;

    public ProductOrderDto(ProductOrder o) {
        this.id = o.getId();
        this.name = o.getName();
        this.price = o.getPrice();
        this.total = o.getTotal();
    }
}
