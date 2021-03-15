package ru.geekbrains.spring.springdata.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.springdata.model.OrderItem;
import ru.geekbrains.spring.springdata.model.Product;
import ru.geekbrains.spring.springdata.services.OrderService;
import ru.geekbrains.spring.springdata.services.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Repository
public class OrderRepository {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    private Map<Long, Integer> orderLines = new HashMap<>();

    public List<OrderItem> addOrderItem(long id, int number) {
        Product product = productService
                .getById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("Product with id: %d doesn't exist", id)));
        if (number <= 0) number = 1;
        if (orderLines.containsKey(product.getId())) {
            number += orderLines.remove(product.getId());
        }
        orderLines.put(product.getId(), number);
        return getOrder();
    }

    public List<OrderItem> removeOrderItem(long id) {
        orderLines.remove(id);
        return getOrder();
    }

    public List<OrderItem> emptyOrder() {
        orderLines.clear();
        return getOrder();
    }

    public int getTotal() {
        AtomicInteger total = new AtomicInteger();
        orderLines.forEach((id, number) -> total.addAndGet(productService
                .getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unexpected error: user shopping cart has spoiled"))
                .getPrice() * number));
        return total.get();
    }

    public List<OrderItem> getOrder() {
        List<OrderItem> orders = new ArrayList<>();
        try {
            orderLines.forEach((id, number) -> orders.add(orderService.createOrderItem(id, number)));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return orders;
    }



}
