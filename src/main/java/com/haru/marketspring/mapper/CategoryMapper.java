package com.haru.marketspring.mapper;

import com.haru.marketspring.dto.category.CategoryRequest;
import com.haru.marketspring.dto.category.CategoryResponse;
import com.haru.marketspring.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CategoryMapper {

    // Map request DTO -> entity (for create)
    Category toEntity(CategoryRequest request);

    // Map entity -> response DTO
    CategoryResponse toResponse(Category category);

    // Update existing entity from request DTO (for partial/put updates)
    void updateEntityFromDto(CategoryRequest request, @MappingTarget Category entity);
}