package com.haru.marketspring.service;

import com.haru.marketspring.dto.category.CategoryRequest;
import com.haru.marketspring.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest requestDto);
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse updateCategory(Long id, CategoryRequest requestDto);
    void deleteCategory(Long id);
}