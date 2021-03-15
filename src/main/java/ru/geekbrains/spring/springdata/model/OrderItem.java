package ru.geekbrains.spring.springdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.geekbrains.spring.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.springdata.model.mappers.DtoMapper;
import ru.geekbrains.spring.springdata.repository.ProductRepository;
import ru.geekbrains.spring.springdata.services.ProductService;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    private long id;
    private String name;
    private int number;
    private int price;

    public int getTotal () {
        return this.number * this.price;
    }
}
