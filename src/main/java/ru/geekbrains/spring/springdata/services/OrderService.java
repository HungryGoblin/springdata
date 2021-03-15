package ru.geekbrains.spring.springdata.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.springdata.model.Product;
import ru.geekbrains.spring.springdata.model.OrderItem;
import ru.geekbrains.spring.springdata.repository.OrderRepository;

import java.util.List;

@Service
@Data
public class OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    public OrderItem createOrderItem(long productId, int number) {
        Product product = productService
                .getById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product with id: d% does not exist", productId)));
        return new OrderItem(product.getId(), product.getName(), product.getPrice(), number);
    }

    public List<OrderItem> addOrderItem(long id, int number) {
        return orderRepository.addOrderItem(id, number);
    }

    public List<OrderItem> removeOrderItem(long id) {
        return orderRepository.removeOrderItem(id);
    }

    public List<OrderItem> emptyOrder() {
        return orderRepository.emptyOrder();
    }

    public List<OrderItem> getOrder() {
        return orderRepository.getOrder();
    }

}
