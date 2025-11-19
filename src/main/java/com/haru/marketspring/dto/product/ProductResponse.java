package com.haru.marketspring.dto.product;

import com.haru.marketspring.dto.category.CategoryResponse;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        Integer stock,
        CategoryResponse category
) {}
