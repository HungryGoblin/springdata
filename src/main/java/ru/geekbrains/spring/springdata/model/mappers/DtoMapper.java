package ru.geekbrains.spring.springdata.model.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.springdata.model.Product;
import ru.geekbrains.spring.springdata.model.dtos.ProductDto;

import java.util.Objects;

@Component
public class DtoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Product toEntity(ProductDto dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, Product.class);
    }

    public ProductDto toDto(Product entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, ProductDto.class);
    }

}
