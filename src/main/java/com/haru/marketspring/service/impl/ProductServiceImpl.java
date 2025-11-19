package com.haru.marketspring.service.impl;

import com.haru.marketspring.dto.product.ProductRequest;
import com.haru.marketspring.dto.product.ProductResponse;
import com.haru.marketspring.entity.Category;
import com.haru.marketspring.entity.Product;
import com.haru.marketspring.exception.ResourceNotFoundException;
import com.haru.marketspring.mapper.ProductMapper;
import com.haru.marketspring.repository.CategoryRepository;
import com.haru.marketspring.repository.ProductRepository;
import com.haru.marketspring.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        //Find the category
        Category category = categoryRepository.findById(productRequest.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productRequest.categoryId()));

        //Map DTO to Entity
        Product product = productMapper.toEntity(productRequest);
        product.setCategory(category);

        //Save and return
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        //Find the existing product
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        //Update from DTO
        productMapper.updateEntityFromDto(productRequest, existingProduct);

        //If categoryId is present, update the category
        if (productRequest.categoryId() != null) {
            Category category = categoryRepository.findById(productRequest.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productRequest.categoryId()));
            existingProduct.setCategory(category);
        }

        //Save and return
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
