package com.haru.marketspring.controller;

import com.haru.marketspring.dto.category.CategoryRequest;
import com.haru.marketspring.dto.category.CategoryResponse;
import com.haru.marketspring.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity<CategoryResponse> createCategory(CategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Override
    public ResponseEntity<CategoryResponse> getCategoryById(Long id) {
        CategoryResponse response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CategoryResponse> updateCategory(Long id, CategoryRequest request) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}