package com.haru.marketspring.mapper;

import com.haru.marketspring.dto.product.ProductRequestDTO;
import com.haru.marketspring.dto.product.ProductResponseDTO;
import com.haru.marketspring.entity.Category;
import com.haru.marketspring.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    Product toEntity(ProductRequestDTO dto, Category category);

    ProductResponseDTO toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true) // Category is updated separately
    void updateEntityFromDto(ProductRequestDTO dto, @MappingTarget Product product);
}
