package ru.geekbrains.spring.springdata.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.springdata.model.Product;
import ru.geekbrains.spring.springdata.model.ProductOrder;
import ru.geekbrains.spring.springdata.model.mappers.DtoMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Data
public class ProductOrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private DtoMapper dtoMapper;

    private Map<Long, Integer> products = new HashMap<>();

    public List<ProductOrder> addProduct(long id, int number) {
        Product product = productService
                .getById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("Product with id: %d doesn't exist", id)));
        if (number <= 0) number = 1;
        if (products.containsKey(product.getId())) {
            number += products.remove(product.getId());
        }
        products.put(product.getId(), number);
        return getOrders();
    }

    public List<ProductOrder> getOrders() {
        List<ProductOrder> orders = new ArrayList<>();
        try {
            products.forEach((id, number) -> orders.add(new ProductOrder(id, number)));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<ProductOrder> removeProduct(long id) {
        products.remove(id);
        return getOrders();
    }

    public List<ProductOrder> emptyCart() {
        products.clear();
        return getOrders();
    }

    public int getTotal() {
        AtomicInteger total = new AtomicInteger();
        products.forEach((id, number) -> total.addAndGet(productService
                .getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unexpected error: user shopping cart has spoiled"))
                .getPrice() * number));
        return total.get();
    }

}
