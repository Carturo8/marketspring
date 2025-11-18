package com.haru.marketspring.controller;

import com.haru.marketspring.dto.ApiErrorResponse;
import com.haru.marketspring.dto.category.CategoryRequest;
import com.haru.marketspring.dto.category.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Category management (CRUD)")
public interface CategoryApi {

    @Operation(summary = "Create a new category", description = "Creates a new category with a unique name")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Category name already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request);

    @Operation(summary = "Get all categories", description = "Returns the list of all categories")
    @ApiResponse(responseCode = "200", description = "List of categories",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))))
    @GetMapping
    ResponseEntity<List<CategoryResponse>> getAllCategories();

    @Operation(summary = "Get category by id", description = "Returns category details for the given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<CategoryResponse> getCategoryById(
            @Parameter(description = "Category identifier", example = "1")
            @PathVariable Long id
    );

    @Operation(summary = "Update an existing category", description = "Updates an existing category with the provided data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated successfully",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Category name already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}")
    ResponseEntity<CategoryResponse> updateCategory(
            @Parameter(description = "Category identifier", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    );

    @Operation(summary = "Delete a category", description = "Deletes a category by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Cannot delete category due to business rule",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCategory(
            @Parameter(description = "Category identifier", example = "1")
            @PathVariable Long id
    );
}