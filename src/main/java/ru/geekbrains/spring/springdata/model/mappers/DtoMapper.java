package ru.geekbrains.spring.springdata.model.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.springdata.model.Product;
import ru.geekbrains.spring.springdata.model.ProductOrder;
import ru.geekbrains.spring.springdata.model.dtos.ProductDto;
import ru.geekbrains.spring.springdata.model.dtos.ProductOrderDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Product toProduct(ProductDto product) {
        return Objects.isNull(product) ? null : modelMapper.map(product, Product.class);
    }

    public ProductDto toProductDto(Product entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, ProductDto.class);
    }

    public List<Product> toProductList(List<ProductDto> dtos) {
        return Objects.isNull(dtos) ? null : dtos
                .stream()
                .map(productDto -> modelMapper.map(productDto, Product.class))
                .collect(Collectors.toList());
    }

    public List<ProductDto> toProductDtoList(List<Product> products) {
        return Objects.isNull(products) ? null : products
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public ProductOrder toProductOrder(ProductOrderDto productOrder) {
        return Objects.isNull(productOrder) ? null : modelMapper.map(productOrder, ProductOrder.class);
    }

    public List<ProductOrderDto> toProductOrderDtoList(List<ProductOrder> orders) {
        return Objects.isNull(orders) ? null : orders
                .stream()
                .map(product -> modelMapper.map(product, ProductOrderDto.class))
                .collect(Collectors.toList());
    }


}
