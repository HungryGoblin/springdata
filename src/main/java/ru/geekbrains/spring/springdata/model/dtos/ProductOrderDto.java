package ru.geekbrains.spring.springdata.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.spring.springdata.model.OrderItem;

@Data
@NoArgsConstructor
public class ProductOrderDto {

    private long id;
    private String name;
    private int number;
    private int price;
    private int total;

    public ProductOrderDto(OrderItem o) {
        this.id = o.getId();
        this.name = o.getName();
        this.price = o.getPrice();
        this.total = o.getTotal();
    }
}
