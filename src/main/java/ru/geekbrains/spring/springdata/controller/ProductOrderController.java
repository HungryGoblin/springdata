package ru.geekbrains.spring.springdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.springdata.model.dtos.ProductOrderDto;
import ru.geekbrains.spring.springdata.model.mappers.DtoMapper;
import ru.geekbrains.spring.springdata.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")

public class ProductOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DtoMapper dtoMapper;


    // http://localhost:8189/geek/api/v1/orders
    @GetMapping
    @ResponseBody
    public List<ProductOrderDto> getOrders() {
        return dtoMapper.toProductOrderDtoList(orderService.getOrder());
    }

    // http://localhost:8189/geek/api/v1/orders/add?id=1&number=3
    @GetMapping("add")
    @ResponseBody
    public List<ProductOrderDto> addOrder(
            @RequestParam Long id,
            @RequestParam int number) {
        return dtoMapper.toProductOrderDtoList(orderService.addOrderItem(id, number));
    }

    // http://localhost:8189/geek/api/v1/orders/rem?id=1
    @GetMapping("rem")
    @ResponseBody
    public List<ProductOrderDto> removeOrder(
            @RequestParam Long id) {
        return dtoMapper.toProductOrderDtoList(orderService.removeOrderItem(id));
    }

}
