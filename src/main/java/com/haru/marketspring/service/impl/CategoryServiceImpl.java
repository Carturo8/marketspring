package com.haru.marketspring.service.impl;

import com.haru.marketspring.dto.category.CategoryRequest;
import com.haru.marketspring.dto.category.CategoryResponse;
import com.haru.marketspring.entity.Category;
import com.haru.marketspring.exception.BadRequestException;
import com.haru.marketspring.exception.ConflictException;
import com.haru.marketspring.exception.ResourceNotFoundException;
import com.haru.marketspring.mapper.CategoryMapper;
import com.haru.marketspring.repository.CategoryRepository;
import com.haru.marketspring.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest requestDto) {
        // Unique name validation
        if (categoryRepository.existsByNameIgnoreCase(requestDto.name())) {
            throw new ConflictException("Category name already exists");
        }

        Category category = categoryMapper.toEntity(requestDto);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest requestDto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + id));

        // Unique name validation (excluding current id)
        if (requestDto.name() != null &&
                categoryRepository.existsByNameIgnoreCaseAndIdNot(requestDto.name(), id)) {
            throw new ConflictException("Category name already exists");
        }

        categoryMapper.updateEntityFromDto(requestDto, existing);
        Category updated = categoryRepository.save(existing);
        return categoryMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + id));

        // TODO: When Product entity/repository is available, check if products are associated
        // if (productRepository.existsByCategoryId(id)) {
        //     throw new BadRequestException("Cannot delete category with associated products");
        // }

        categoryRepository.delete(existing);
    }
}