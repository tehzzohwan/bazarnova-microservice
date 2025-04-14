package com.tehzzcode.productservice.service.impl;

import com.tehzzcode.productservice.dto.DefaultResponse;
import com.tehzzcode.productservice.dto.ProductRequest;
import com.tehzzcode.productservice.dto.ProductResponse;
import com.tehzzcode.productservice.model.Product;
import com.tehzzcode.productservice.repository.ProductRepository;
import com.tehzzcode.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ResponseEntity<DefaultResponse<ProductResponse>> addProduct(ProductRequest productDto) {
        DefaultResponse<ProductResponse> response = new DefaultResponse<>();
        try {
            Product product = Product.builder()
                    .name(productDto.getName())
                    .description(productDto.getDescription())
                    .price(productDto.getPrice())
                    .build();

            productRepository.save(product);
            log.info("Added product: {}", product.getId());

            ProductResponse productResponse = mapToProductResponse(product);
            response.setStatus("00");
            response.setMessage("Product added successfully");
            response.setData(productResponse);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error adding product: ", e.getMessage());
            response.setStatus("99");
            response.setMessage("Error adding product");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DefaultResponse<List<ProductResponse>>> getAllProducts() {
        DefaultResponse<List<ProductResponse>> response = new DefaultResponse<>();
        try {
            List<Product> products = productRepository.findAll();
            List<ProductResponse> productResponses = products.stream()
                    .map(this::mapToProductResponse)
                    .toList();
            response.setStatus("00");
            response.setMessage("Products fetched successfully");
            response.setData(productResponses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching products: ", e);
            response.setStatus("99");
            response.setMessage("Error fetching products");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
